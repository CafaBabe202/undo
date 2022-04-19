package top.cafebabe.undo.file.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.cafebabe.fileManager.exception.NotFileException;
import top.cafebabe.fileManager.manger.TempFileManager;
import top.cafebabe.undo.common.bean.LoginUser;
import top.cafebabe.undo.common.bean.ResponseMessage;
import top.cafebabe.undo.common.util.MessageUtil;
import top.cafebabe.undo.file.bean.AppConfig;
import top.cafebabe.undo.file.bean.form.FileUploadInitForm;
import top.cafebabe.undo.file.util.RandomUtils;
import top.cafebabe.undo.file.bean.FileUploadStatus;
import top.cafebabe.undo.file.service.UserFileSer;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 上传文件的逻辑。
 *
 * @author cafababe
 */
@RestController
@RequestMapping("/upload")
public class UploadCtrl {

    final TempFileManager tempFileManager;

    final UserFileSer userFileSer;

    public UploadCtrl(TempFileManager tempFileManager, UserFileSer userFileSer) {
        this.tempFileManager = tempFileManager;
        this.userFileSer = userFileSer;
    }

    /**
     * 初始化一个文件上传。
     * 用户提交文件名，md5，大小等信息，如果文件已经存在，直接生成一个文件对象放在 MySQL 中，否则返回下载上传的位置片段。
     */
    @PostMapping("/initUpload.token")
    public ResponseMessage initUpload(@RequestBody FileUploadInitForm form, HttpSession session) {
        LoginUser loginUser = (LoginUser) session.getAttribute(AppConfig.LOGIN_USER_KEY_IN_SESSION);
        if (loginUser == null)
            return MessageUtil.error("数据异常");

        // 验证文件是否存在
        if (tempFileManager.exist(form.getMd5())) { // 存在直接秒传
            try {
                return this.userFileSer.add(loginUser.getId(), form.getFileName(), form.getMd5(), form.getAllSize())
                        && this.tempFileManager.add(form.getMd5())
                        ? MessageUtil.done() : MessageUtil.fail("数据异常");
            } catch (NotFileException e) {
                e.printStackTrace();
                return MessageUtil.error("上传失败");
            }
        } else { // 不存在请求上传。
            FileUploadStatus status = new FileUploadStatus(form.getFileName(), form.getMd5(), tempFileManager.createTempFile(), form.getAllSize(), 0);
            String uuid = RandomUtils.getUUID(); // 生成一个该文件上传对应的随机 ID，之所有不使用 MD5 是防止用户同时上传两个同 MD5 的文件造成文件的上传错误。
            session.setAttribute(uuid, status);
            Map<String, Object> res = nexUpload(status); // 计算下次上传的片段位置。
            res.put("uuid", uuid); // 将上传状态等和文件的唯一上传 ID 放在 session 中。
            return MessageUtil.goOn(res);
        }
    }

    // 真正的上传逻辑
    @PostMapping("/upload.token")
    public ResponseMessage upload(@RequestParam("file") MultipartFile file, String uuid, HttpSession session) throws Exception {
        LoginUser loginUser = (LoginUser) session.getAttribute(AppConfig.LOGIN_USER_KEY_IN_SESSION);
        if (loginUser == null)
            return MessageUtil.error("数据异常");

        FileUploadStatus status = (FileUploadStatus) session.getAttribute(uuid); // 获取用户的上传状态
        byte[] data = file.getInputStream().readAllBytes(); // 上传的片段
        if (tempFileManager.temp(status.getTempFileId(), data)) // 放在临时数据库中
            status.setNowSize(status.getNowSize() + data.length);

        if (status.getAllSize() == status.getNowSize())// 上传完成进行校验
            return tempFileManager.store(status.getTempFileId(), status.getMd5())
                    && this.userFileSer.add(loginUser.getId(), status.getFileName(), status.getMd5(), status.getAllSize())
                    ? MessageUtil.done() : MessageUtil.fail("上传失败");

        Thread.sleep(500); // 为了方便看效果，上传限速
        return MessageUtil.goOn(nexUpload(status));
    }

    public Map<String, Object> nexUpload(FileUploadStatus status) {
        Map<String, Object> res = new HashMap<>();
        res.put("from", status.getNowSize());
        long to = status.getNowSize() + 1024 * 1024;
        res.put("to", Math.min(to, status.getAllSize()));
        return res;
    }
}


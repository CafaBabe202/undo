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

    @PostMapping("/initUpload.token")
    public ResponseMessage initUpload(@RequestBody FileUploadInitForm form, HttpSession session) {
        LoginUser loginUser = (LoginUser) session.getAttribute(AppConfig.LOGIN_USER_KEY_IN_SESSION);
        if (loginUser == null)
            return MessageUtil.error("数据异常");

        if (tempFileManager.exist(form.getMd5())) {
            try {
                return this.userFileSer.add(loginUser.getId(), form.getFileName(), form.getMd5(), form.getAllSize())
                        && this.tempFileManager.add(form.getMd5())
                        ? MessageUtil.done() : MessageUtil.fail("数据异常");
            } catch (NotFileException e) {
                e.printStackTrace();
                return MessageUtil.error("上传失败");
            }
        } else {
            FileUploadStatus status = new FileUploadStatus(form.getFileName(), form.getMd5(), tempFileManager.createTempFile(), form.getAllSize(), 0);
            String uuid = RandomUtils.getUUID();
            session.setAttribute(uuid, status);
            Map<String, Object> res = nexUpload(status);
            res.put("uuid", uuid);
            return MessageUtil.goOn(res);
        }
    }

    @PostMapping("/upload.token")
    public ResponseMessage upload(@RequestParam("file") MultipartFile file, String uuid, HttpSession session) throws Exception {
        LoginUser loginUser = (LoginUser) session.getAttribute(AppConfig.LOGIN_USER_KEY_IN_SESSION);
        if (loginUser == null)
            return MessageUtil.error("数据异常");

        FileUploadStatus status = (FileUploadStatus) session.getAttribute(uuid);
        byte[] data = file.getInputStream().readAllBytes();
        if (tempFileManager.temp(status.getTempFileId(), data))
            status.setNowSize(status.getNowSize() + data.length);

        if (status.getAllSize() == status.getNowSize())
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


package top.cafebabe.undo.file.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.cafebabe.fileManager.manger.TempFileManager;
import top.cafebabe.fileManager.manger.integrator.Integrator;
import top.cafebabe.undo.common.bean.LoginUser;
import top.cafebabe.undo.common.bean.ResponseMessage;
import top.cafebabe.undo.common.util.MessageUtil;
import top.cafebabe.undo.file.bean.AppConfig;
import top.cafebabe.undo.file.bean.UserFile;
import top.cafebabe.undo.file.service.UserFileSer;
import top.cafebabe.undo.file.util.DownIdUtil;
import top.cafebabe.undo.file.util.FileIdUtil;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 主要处理文件获取相关的逻辑。
 *
 * @author cafababe
 */
@RestController
public class GetCtrl {

    final TempFileManager tempFileManager;
    final UserFileSer userFileSer;

    public GetCtrl(TempFileManager tempFileManager, UserFileSer userFileSer) {
        this.tempFileManager = tempFileManager;
        this.userFileSer = userFileSer;
    }

    /**
     * 获取一个文件用来展示
     *
     * @param id 文件 ID
     */
    @GetMapping("/get.cors/{id}")
    public ResponseMessage get(@PathVariable String id, HttpServletResponse response, HttpSession session) {
        try {
            return this.doGet(id, response, session, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MessageUtil.error("获取文件失败");
    }

    /**
     * 请求下载一个文件，这并不会直接下载文件，而是生成一个临时的下载凭证，下载的逻辑通过下边的方法完成。
     */
    @GetMapping("/down.tokCors/{fileId}")
    public ResponseMessage down(@PathVariable String fileId, HttpSession session) {
        LoginUser loginUser = (LoginUser) session.getAttribute(AppConfig.LOGIN_USER_KEY_IN_SESSION);
        UserFile file = userFileSer.getFile(fileId);
        if (file == null)
            return MessageUtil.fail("文件不存在");

        if (file.isPrivate() && (loginUser == null || loginUser.getId() != file.getUserId()))
            return MessageUtil.permissionDenied();

        return MessageUtil.ok(DownIdUtil.createId(fileId));
    }

    /**
     * 下载一个文件，只验证凭证，只要凭证通过就可以下载。
     */
    @GetMapping("/download/*")
    public ResponseMessage download(@RequestParam String downId, HttpServletResponse response, HttpSession session) throws Exception {
        if (!DownIdUtil.check(downId))
            return MessageUtil.permissionDenied();

        return doGet(DownIdUtil.getFileId(downId), response, session, true);
    }

    /**
     * 这里其实很没有必要这么写，因为目前前端的做法，预览的用户信息跟不能被校验，所以预览的时候即使是自己私有文件还是不能被展示。
     * 目前并不打算改这里，以后想想怎么改前端吧，想办法能够验证是自己访问自己的文章。
     */
    private ResponseMessage doGet(String fileId, HttpServletResponse response, HttpSession session, boolean isDown) throws IOException, InterruptedException {
        UserFile file = userFileSer.getFile(fileId);
        if (file == null)
            return MessageUtil.fail("文件不存在");

        // 判断是不是下载
        if (isDown) { // 下载不需要验证信息
            response.addHeader("Content-Disposition", "attachment");
        } else { // 预览就要验证信息
            LoginUser loginUser = (LoginUser) session.getAttribute(AppConfig.LOGIN_USER_KEY_IN_SESSION);
            if (file.isPrivate() && (loginUser == null || loginUser.getId() != file.getUserId()))
                return MessageUtil.permissionDenied();
        }

        response.reset();
        Integrator integrator = tempFileManager.get(file.getMd5());
        ServletOutputStream outputStream = response.getOutputStream();
        while (integrator.hasNext()) {
            Thread.sleep(500); // 延时，为了看前端的下载效果
            outputStream.write(integrator.next());
        }
        outputStream.close();

        return MessageUtil.ok("");
    }
}

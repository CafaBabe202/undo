package top.cafebabe.undo.file.controller;

import org.springframework.web.bind.annotation.*;
import top.cafebabe.undo.common.bean.LoginUser;
import top.cafebabe.undo.common.bean.ResponseMessage;
import top.cafebabe.undo.common.util.MessageUtil;
import top.cafebabe.undo.file.bean.AppConfig;
import top.cafebabe.undo.file.bean.form.RenameForm;
import top.cafebabe.undo.file.service.UserFileSer;

import javax.servlet.http.HttpSession;

/**
 * @author cafababe
 */
@RestController
@RequestMapping("/file")
public class FileCtl {
    final UserFileSer userFileSer;

    public FileCtl(UserFileSer userFileSer) {
        this.userFileSer = userFileSer;
    }

    @GetMapping("/delete.token/{id}")
    public ResponseMessage delete(@PathVariable String id, HttpSession session) {
        LoginUser loginUser = (LoginUser) session.getAttribute(AppConfig.LOGIN_USER_KEY_IN_SESSION);
        if (loginUser == null)
            return MessageUtil.error("数据异常");

        try {
            return this.userFileSer.delete(loginUser.getId(), Integer.parseInt(id))
                    ? MessageUtil.ok("删除成功") : MessageUtil.fail("删除失败");
        } catch (Exception e) {
            e.printStackTrace();
            return MessageUtil.fail("数据异常");
        }
    }

    @PostMapping("/changePrivate.token/{id}")
    public ResponseMessage changePrivate(@PathVariable String id, HttpSession session) {
        LoginUser loginUser = (LoginUser) session.getAttribute(AppConfig.LOGIN_USER_KEY_IN_SESSION);
        if (loginUser == null)
            return MessageUtil.error("数据异常");

        try {
            return this.userFileSer.changePrivate(loginUser.getId(), Integer.parseInt(id))
                    ? MessageUtil.ok("设置成功") : MessageUtil.fail("设置失败");
        } catch (Exception e) {
            e.printStackTrace();
            return MessageUtil.fail("数据异常");
        }
    }


    @PostMapping("/rename.token")
    public ResponseMessage rename(@RequestBody RenameForm form, HttpSession session) {
        LoginUser loginUser = (LoginUser) session.getAttribute(AppConfig.LOGIN_USER_KEY_IN_SESSION);
        if (loginUser == null)
            return MessageUtil.error("数据异常");

        return this.userFileSer.rename(loginUser.getId(), form.getId(), form.getNewVal()) ?
                MessageUtil.ok("重命名成功") : MessageUtil.fail("重命名失败");
    }

    @GetMapping("/getAllFile.token")
    public ResponseMessage getAllFile(HttpSession session) {
        LoginUser loginUser = (LoginUser) session.getAttribute(AppConfig.LOGIN_USER_KEY_IN_SESSION);
        if (loginUser == null)
            return MessageUtil.error("数据异常");

        return MessageUtil.ok(this.userFileSer.getAllFile(loginUser.getId()));
    }

}

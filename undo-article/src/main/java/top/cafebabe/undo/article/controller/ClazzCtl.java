package top.cafebabe.undo.article.controller;

import org.springframework.web.bind.annotation.*;
import top.cafebabe.undo.article.bean.AppConfig;
import top.cafebabe.undo.article.bean.Clazz;
import top.cafebabe.undo.article.form.AddClazzForm;
import top.cafebabe.undo.article.form.RenameClazzForm;
import top.cafebabe.undo.article.service.ClazzService;
import top.cafebabe.undo.article.service.TokenService;
import top.cafebabe.undo.article.util.Checker;
import top.cafebabe.undo.article.util.ClassConverter;
import top.cafebabe.undo.article.util.SessionUtil;
import top.cafebabe.undo.common.bean.LoginUser;
import top.cafebabe.undo.common.bean.ResponseMessage;
import top.cafebabe.undo.common.util.MessageUtil;

import javax.servlet.http.HttpSession;

/**
 * @author cafababe
 */
@RestController
@RequestMapping("/clazz")
public class ClazzCtl {

    final TokenService tokenService;
    final ClazzService clazzService;

    public ClazzCtl(TokenService tokenService, ClazzService clazzService) {
        this.tokenService = tokenService;
        this.clazzService = clazzService;
    }

    @PostMapping("/add.token")
    public ResponseMessage add(@RequestBody AddClazzForm form, HttpSession session) {
        if (!Checker.check(form))
            return MessageUtil.fail("参数异常");

        LoginUser loginUser = (LoginUser) session.getAttribute(AppConfig.LOGIN_USER_KEY_IN_SESSION);
        Clazz clazz = ClassConverter.toClazz(form);
        clazz.setUserId(loginUser.getId());

        return this.clazzService.addClazz(clazz) ?
                MessageUtil.ok("添加成功") : MessageUtil.fail("添加失败");
    }

    @PostMapping("/delete.token/{id}")
    public ResponseMessage delete(@PathVariable String id, HttpSession session) {
        LoginUser loginUser = SessionUtil.getLoginUser(session);
        if (loginUser == null)
            return MessageUtil.error("变量错误");
        try {
            return clazzService.deleteClazz(loginUser.getId(), Integer.parseInt(id)) ?
                    MessageUtil.ok("删除成功") : MessageUtil.fail("删除失败");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return MessageUtil.fail("变量错误");
        }
    }

    @PostMapping("/rename.token")
    public ResponseMessage rename(@RequestBody RenameClazzForm form, HttpSession session) {
        if (!Checker.check(form))
            return MessageUtil.fail("变量错误");

        LoginUser loginUser = SessionUtil.getLoginUser(session);
        if (loginUser == null)
            return MessageUtil.error("变量错误");

        return clazzService.rename(loginUser.getId(), form.getId(), form.getNewVal()) ?
                MessageUtil.ok("设置成功") : MessageUtil.fail("设置失败");
    }

    @GetMapping("/getAllClazz/{userId}")
    public ResponseMessage getAllClazz(@PathVariable String userId) {
        try {
            int id = Integer.parseInt(userId);
            return MessageUtil.ok(this.clazzService.getAllClazz(id));
        } catch (Exception e) {
            return MessageUtil.fail("ID异常");
        }
    }
}

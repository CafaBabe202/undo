package top.cafebabe.undo.article.controller;

import org.springframework.web.bind.annotation.*;
import top.cafebabe.undo.article.bean.AppConfig;
import top.cafebabe.undo.article.bean.Article;
import top.cafebabe.undo.article.bean.Clazz;
import top.cafebabe.undo.article.bean.form.AddClazzForm;
import top.cafebabe.undo.article.bean.form.RenameClazzForm;
import top.cafebabe.undo.article.service.ArticleService;
import top.cafebabe.undo.article.service.ClazzService;
import top.cafebabe.undo.article.service.TokenService;
import top.cafebabe.undo.article.util.Checker;
import top.cafebabe.undo.article.util.ClassConverter;
import top.cafebabe.undo.article.util.SessionUtil;
import top.cafebabe.undo.common.bean.LoginUser;
import top.cafebabe.undo.common.bean.ResponseMessage;
import top.cafebabe.undo.common.util.MessageUtil;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @author cafababe
 */
@RestController
@RequestMapping("/clazz")
public class ClazzCtl {

    final TokenService tokenService;
    final ClazzService clazzService;
    final ArticleService articleService;

    public ClazzCtl(TokenService tokenService, ClazzService clazzService, ArticleService articleService) {
        this.tokenService = tokenService;
        this.clazzService = clazzService;
        this.articleService = articleService;
    }

    // 添加分类
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

    // 删除分类
    @PostMapping("/delete.token/{id}")
    public ResponseMessage delete(@PathVariable String id, HttpSession session) {
        LoginUser loginUser = SessionUtil.getLoginUser(session);

        if (loginUser == null)
            return MessageUtil.error("变量错误");

        try {
            int i = Integer.parseInt(id);
            if (articleService.getStatistics(loginUser.getId(), i).get("number") > Integer.parseInt("0"))
                return MessageUtil.fail("分类不为空");

            return clazzService.deleteClazz(loginUser.getId(), i) ?
                    MessageUtil.ok("删除成功") : MessageUtil.fail("删除失败");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return MessageUtil.fail("变量错误");
        }
    }

    // 重命名分类
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

    // 获取某人的所有分类
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

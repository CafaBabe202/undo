package top.cafebabe.undo.article.controller;

import org.springframework.web.bind.annotation.*;
import top.cafebabe.undo.article.bean.AppConfig;
import top.cafebabe.undo.article.bean.form.AdminLoginForm;
import top.cafebabe.undo.article.dao.ContentDao;
import top.cafebabe.undo.article.util.Checker;
import top.cafebabe.undo.article.util.ClassConverter;
import top.cafebabe.undo.common.bean.ResponseMessage;
import top.cafebabe.undo.common.util.MessageUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author cafababe
 */
@RestController
@RequestMapping("/review")
public class ReviewCtl {
    final ContentDao contentDao;

    public ReviewCtl(ContentDao contentDao) {
        this.contentDao = contentDao;
    }

    // 获取一篇未审核的文章
    @GetMapping("/get")
    public ResponseMessage get(HttpSession session, HttpServletRequest request) {
        System.out.println(session.getId());
        System.out.println(request.getSession().getId());
        Map<String, Object> res = new HashMap<>();
        long notReviewNumber = contentDao.getNotReviewNumber();
        res.put("wait", notReviewNumber);
        if (notReviewNumber != 0)
            res.put("content", ClassConverter.showContent(contentDao.getOneNotReview()));
        return MessageUtil.ok(res);
    }

    // 通过篇文章
    @GetMapping("/pass/{id}")
    public ResponseMessage pass(@PathVariable String id) {
        return contentDao.pass(id) ? MessageUtil.ok("通过成功") : MessageUtil.fail("通过失败");
    }

    // 拒绝通过一篇文章
    @GetMapping("/denied/{id}")
    public ResponseMessage denied(@PathVariable String id) {
        return contentDao.denied(id) ? MessageUtil.ok("拒绝成功") : MessageUtil.fail("拒绝失败");
    }

    // 管理员登录
    @PostMapping("/login")
    public ResponseMessage login(@RequestBody AdminLoginForm form, HttpServletRequest request, HttpSession session) {
        if (!Checker.check(form))
            return MessageUtil.fail("登录失败");
        session.setAttribute(AppConfig.ADMIN_LOGIN_TOKEN_KEY_IN_SESSION, request.getRemoteAddr());
        return MessageUtil.ok(form);
    }
}

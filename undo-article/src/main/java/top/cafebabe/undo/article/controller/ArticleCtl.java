package top.cafebabe.undo.article.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.cafebabe.undo.article.bean.AppConfig;
import top.cafebabe.undo.article.bean.Article;
import top.cafebabe.undo.article.bean.Content;
import top.cafebabe.undo.article.dao.ContentDao;
import top.cafebabe.undo.article.dao.RecordsDao;
import top.cafebabe.undo.article.service.ArticleService;
import top.cafebabe.undo.common.bean.LoginUser;
import top.cafebabe.undo.common.bean.ResponseMessage;
import top.cafebabe.undo.common.util.CurrentUtil;
import top.cafebabe.undo.common.util.MessageUtil;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;

/**
 * @author cafababe
 */
@RestController()
@RequestMapping("/article")
public class ArticleCtl {

    final ArticleService articleService;

    public ArticleCtl(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/getArticles.token/{clazzId}")
    public ResponseMessage getArticles(@PathVariable String clazzId, HttpSession session) {
        try {
            int id = Integer.parseInt(clazzId);
            LoginUser loginUser;
            Object attribute = session.getAttribute(AppConfig.LOGIN_USER_KEY_IN_SESSION);
            if (!(attribute instanceof LoginUser)) {
                return MessageUtil.fail("未知错误");
            }
            loginUser = (LoginUser) attribute;

            return MessageUtil.ok(articleService.getArticlesByClazz(loginUser.getId(), id));
        } catch (Exception e) {
            return MessageUtil.ok("变量错误");
        }

    }

}

package top.cafebabe.undo.article.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.cafebabe.undo.article.bean.Article;
import top.cafebabe.undo.article.bean.Content;
import top.cafebabe.undo.article.dao.ContentDao;
import top.cafebabe.undo.article.dao.RecordsDao;
import top.cafebabe.undo.article.service.ArticleService;
import top.cafebabe.undo.common.bean.ResponseMessage;
import top.cafebabe.undo.common.util.CurrentUtil;
import top.cafebabe.undo.common.util.MessageUtil;

import java.sql.Timestamp;

/**
 * @author cafababe
 */
@RestController()
@RequestMapping("/article")
public class ArticleCtl {

    @Autowired
    ArticleService articleService;
    @Autowired
    RecordsDao recordsDao;
    @Autowired
    ContentDao contentDao;

    @PostMapping("/add")
    public ResponseMessage addArticle() {
        Article article = new Article();
        article.setTitle("Demo");
        article.setSummary("demodemo");
        article.setCreateTime(new Timestamp(CurrentUtil.now()));
        article.setUpdateTime(new Timestamp(CurrentUtil.now()));
        article.setUserId(1);
        article.setClazzId(1);
        article.setPrivate(false);
        articleService.addArticle(article, "demoContent");
        return MessageUtil.ok(article.getId());
    }

    @PostMapping("/update")
    public ResponseMessage test() {
        Content content = new Content("UPDate");
        return articleService.updateContent(16, "DEmo", content) ? MessageUtil.ok("OK") : MessageUtil.fail("NO");
    }

    @PostMapping("/delete")
    public ResponseMessage delete() {
        return articleService.deleteArticle(16) ? MessageUtil.ok("OK") : MessageUtil.fail("NO");
    }

    @GetMapping("/d")
    public ResponseMessage d(String id) {
        contentDao.deleteContent(id);
        return MessageUtil.ok("OK");
    }

}

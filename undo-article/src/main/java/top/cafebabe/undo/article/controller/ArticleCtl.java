package top.cafebabe.undo.article.controller;

import org.springframework.web.bind.annotation.*;
import top.cafebabe.undo.article.bean.Article;
import top.cafebabe.undo.article.bean.Content;
import top.cafebabe.undo.article.bean.form.EditArticleForm;
import top.cafebabe.undo.article.dao.GoodMapper;
import top.cafebabe.undo.article.service.ArticleService;
import top.cafebabe.undo.article.service.ClazzService;
import top.cafebabe.undo.article.util.Checker;
import top.cafebabe.undo.article.util.ClassConverter;
import top.cafebabe.undo.article.util.SessionUtil;
import top.cafebabe.undo.common.bean.LoginUser;
import top.cafebabe.undo.common.bean.ResponseMessage;
import top.cafebabe.undo.common.util.MessageUtil;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author cafababe
 */
@RestController()
@RequestMapping("/article")
public class ArticleCtl {

    final ArticleService articleService;
    final ClazzService clazzService;
    final GoodMapper mapper;

    public ArticleCtl(ArticleService articleService, ClazzService clazzService, GoodMapper mapper) {
        this.articleService = articleService;
        this.clazzService = clazzService;
        this.mapper = mapper;
    }

    // 编辑、新建文章
    @PostMapping("/editArticle.token")
    public ResponseMessage addArticle(@RequestBody EditArticleForm form, HttpSession session) {
        if (!Checker.check(form) || !clazzService.existClazz(form.getClazzId()))
            return MessageUtil.fail("数据异常");

        LoginUser loginUser = SessionUtil.getLoginUser(session);
        if (loginUser == null)
            return MessageUtil.error("变量错误");

        Article article = ClassConverter.toArticle(form);
        article.setUserId(loginUser.getId());

        // 编辑的文章 ID ，如果是 <0 就是新建
        if (form.getId() < 0)
            return articleService.addArticle(article, form.getContent()) ?
                    MessageUtil.ok("添加成功") : MessageUtil.error("添加失败");
        else
            return articleService.updateArticle(article, form.getUpdateSummary(), form.getContent()) ?
                    MessageUtil.ok("更新成功") : MessageUtil.error("更新失败");
    }

    // 删除文章
    @PostMapping("/deleteArticle.token/{articleId}")
    public ResponseMessage deleteArticle(@PathVariable String articleId, HttpSession session) {
        LoginUser loginUser = SessionUtil.getLoginUser(session);
        if (loginUser == null)
            return MessageUtil.error("变量错误");

        try {
            return articleService.deleteArticle(loginUser.getId(), Integer.parseInt(articleId)) ?
                    MessageUtil.ok("删除成功") : MessageUtil.fail("删除失败");
        } catch (Exception e) {
            return MessageUtil.fail("数据异常");
        }
    }

    // 改变文章的访问级别
    @PostMapping("/changePrivate.token/{clazzId}")
    public ResponseMessage changePrivate(@PathVariable String clazzId, HttpSession session) {
        LoginUser loginUser = SessionUtil.getLoginUser(session);
        if (loginUser == null)
            return MessageUtil.error("变量错误");
        try {
            return articleService.changePrivate(loginUser.getId(), Integer.parseInt(clazzId)) ?
                    MessageUtil.ok("设置成功") : MessageUtil.error("设置失败");
        } catch (NumberFormatException e) {
            return MessageUtil.fail("设置失败");
        }
    }

    // 统计用户的文章信息, 如果没有指定统计那一分类则统计本人所有文章信息
    @GetMapping("/getStatistics.token")
    public ResponseMessage getStatistics(@RequestParam(value = "id", defaultValue = "-1") String clazzId, HttpSession session) {
        LoginUser loginUser = SessionUtil.getLoginUser(session);
        if (loginUser == null)
            return MessageUtil.error("变量错误");

        try {
            Map<String, Long> statistics = articleService.getStatistics(loginUser.getId(), Integer.parseInt(clazzId));
            statistics.put("clazzNum", (long) clazzService.getAllClazz(loginUser.getId()).size());
            return MessageUtil.ok(statistics);
        } catch (Exception e) {
            e.printStackTrace();
            return MessageUtil.fail("变量错误");
        }
    }

    // 获取某人自己某一分类下的所有文章
    @GetMapping("/getArticles.token/{clazzId}")
    public ResponseMessage getArticles(@PathVariable String clazzId, HttpSession session) {
        LoginUser loginUser = SessionUtil.getLoginUser(session);
        if (loginUser == null)
            return MessageUtil.error("变量错误");

        try {
            return MessageUtil.ok(articleService.getMyArticlesByClazz(loginUser.getId(), Integer.parseInt(clazzId)));
        } catch (Exception e) {
            e.printStackTrace();
            return MessageUtil.ok("变量错误");
        }
    }

    // 获取自己的文章
    @GetMapping("/getMyArticle.token/{articleId}")
    public ResponseMessage getMyArticle(@PathVariable String articleId, HttpSession session) {
        LoginUser loginUser = SessionUtil.getLoginUser(session);
        if (loginUser == null)
            return MessageUtil.error("变量错误");
        try {
            Article article = articleService.getMyArticle(loginUser.getId(), Integer.parseInt(articleId));
            return article == null ?
                    MessageUtil.fail("变量错误") : MessageUtil.ok(ClassConverter.showArticle(article, articleService.getLastContent(article.getRecordsId(), true)));
        } catch (NumberFormatException e) {
            return MessageUtil.fail("变量错误");
        }
    }

    // 搜索接口
    @GetMapping("/search")
    public ResponseMessage search(
            @RequestParam(value = "title", defaultValue = "") String title,
            @RequestParam(value = "p", defaultValue = "1") String page) {
        try {
            return MessageUtil.ok(articleService.search(title, Integer.parseInt(page)));
        } catch (Exception e) {
            return MessageUtil.fail("数据异常");
        }
    }

    // 登录或者没登录获取文章
    @GetMapping("/getArticle.tok/{articleId}")
    public ResponseMessage getArticle(
            @PathVariable String articleId,
            @RequestParam(value = "v", defaultValue = "init") String version,
            HttpSession session
    ) {
        LoginUser loginUser = SessionUtil.getLoginUser(session);
        Article article;
        try {
            if ((article = articleService.getArticle(Integer.parseInt(articleId))) == null)
                return MessageUtil.fail("数据异常");
        } catch (Exception e) {
            return MessageUtil.fail("数据异常");
        }

        boolean isPrivate = loginUser != null && article.getUserId() == loginUser.getId();
        Content content = "init".equals(version) ? articleService.getLastContent(article.getRecordsId(), isPrivate) : articleService.getContent(version, isPrivate);
        if (content == null)
            return MessageUtil.fail("数据异常");

        if (!article.isPrivate() || isPrivate) {
            articleService.visit(article.getId());
            return MessageUtil.ok(ClassConverter.showArticle(article, content));
        } else {
            return MessageUtil.permissionDenied();
        }
    }

    //  获取文章更新记录
    @GetMapping("/getRecords.tok/{articleId}")
    public ResponseMessage getArticleRecords(@PathVariable String articleId, HttpSession session) {
        LoginUser loginUser = SessionUtil.getLoginUser(session);
        Article article;
        try {
            if ((article = articleService.getArticle(Integer.parseInt(articleId))) == null)
                return MessageUtil.fail("数据异常");
        } catch (Exception e) {
            return MessageUtil.fail("数据异常");
        }

        boolean isPrivate = loginUser != null && article.getUserId() == loginUser.getId();
        return !article.isPrivate() || isPrivate ?
                MessageUtil.ok(ClassConverter.showRecords(articleService.getRecords(article.getRecordsId(), isPrivate))) : MessageUtil.permissionDenied();
    }

    // 获取浏览量的榜单
    @GetMapping("/getVisitRank")
    public ResponseMessage getVisitRank() {
        return MessageUtil.ok(articleService.getVisitRank());
    }

    // 获取用户的排名
    @GetMapping("/getUserRank")
    public ResponseMessage getUserRank() {
        return MessageUtil.ok(articleService.getUserRank());
    }

    // 点赞文章
    @GetMapping("/like.token")
    public ResponseMessage like(@RequestParam(value = "id", defaultValue = "-1") String id, HttpSession session) {
        LoginUser loginUser = SessionUtil.getLoginUser(session);
        if (loginUser == null)
            return MessageUtil.error("变量错误");

        try {
            boolean res = articleService.like(Integer.parseInt(id))
                    && mapper.good(loginUser.getId(), Integer.parseInt(id)) == 1;
            return MessageUtil.ok(res ? "点赞成功！" : "成功");
        } catch (Exception e) {
            e.printStackTrace();
            return MessageUtil.fail("数据异常");
        }
    }

    // 是否点赞
    @GetMapping("/isLike.token/{articleId}")
    public ResponseMessage isLike(@PathVariable String articleId, HttpSession session) {
        LoginUser loginUser = SessionUtil.getLoginUser(session);
        if (loginUser == null)
            return MessageUtil.error("变量错误");
        try {
            return MessageUtil.ok(mapper.isGood(loginUser.getId(), Integer.parseInt(articleId)) > 0 ? 1 : 0);
        } catch (Exception e) {
            e.printStackTrace();
            return MessageUtil.ok(0);
        }
    }
}
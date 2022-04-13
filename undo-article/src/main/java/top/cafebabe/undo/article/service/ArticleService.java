package top.cafebabe.undo.article.service;

import org.springframework.stereotype.Service;
import top.cafebabe.undo.article.bean.*;
import top.cafebabe.undo.article.dao.ArticleMapper;
import top.cafebabe.undo.article.dao.ContentDao;
import top.cafebabe.undo.article.dao.RecordsDao;
import top.cafebabe.undo.common.util.CurrentUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cafababe
 */
@Service
public class ArticleService {

    private final ArticleMapper articleMapper;
    private final ContentDao contentDao;
    final RecordsDao recordsDao;

    public ArticleService(ArticleMapper articleMapper, ContentDao contentDao, RecordsDao recordsDao) {
        this.articleMapper = articleMapper;
        this.contentDao = contentDao;
        this.recordsDao = recordsDao;
    }

    /**
     * 添加一篇文章。
     *
     * @param article 文章的元数据。
     * @param con     文章的正文。
     * @return 是否添加成功。
     */
    public boolean addArticle(Article article, String con) {
        article.setCreateTime(new Timestamp(CurrentUtil.now()));
        article.setUpdateTime(new Timestamp(CurrentUtil.now()));

        Records records = new Records();
        article.setRecordsId(records.getId().toString());

        Content content = new Content(con);
        recordsDao.createRecords(records); // 添加记录集合。

        contentDao.addContent(content); // 向内容集合添加数据
        if (articleMapper.add(article) != 1
                || !recordsDao.putRecord(article.getRecordsId(), new Record("init", content.getId()))) { // 出现问题回滚
            articleMapper.deleteById(article.getId());
            deleteRecords(records.getId().toString());
            return false;
        }
        return true;
    }

    /**
     * 删除文章
     *
     * @param articleId 文章的 ID。
     * @return 是否成功删除。
     */
    public boolean deleteArticle(int userId, int articleId) {
        Article article = articleMapper.getArticleById(articleId, true);
        if (article == null || article.getUserId() != userId)
            return false;
        // 如果没有这篇文章或者说删除的不只有一个，就是有问题，就失败
        return articleMapper.deleteById(articleId) == 1 && deleteRecords(article.getRecordsId());
    }

    /**
     * 更新文章
     *
     * @param newArticle 新的文章
     * @param summary    更新说明
     * @param content    新的正文
     * @return 是否更新成功
     */
    public boolean updateArticle(Article newArticle, String summary, Content content) {
        Article article = articleMapper.getArticleById(newArticle.getId(), true);
        if (article == null) return false; // 如果没有该文章肯定是失败
        contentDao.addContent(content); // 向内容集合添加数据
        if (!recordsDao.putRecord(article.getRecordsId(), new Record(summary, content.getId())))
            return false; // 向更新记录添加数据
        newArticle.setUpdateTime(new Timestamp(CurrentUtil.now()));
        return articleMapper.update(newArticle) == 1; // 更新 MySQL 中的数据
    }

    /**
     * 设置访问级别。
     *
     * @param userId  用户 ID。
     * @param clazzId 文章 ID。
     * @return 是否更新成功。
     */
    public boolean changePrivate(int userId, int clazzId) {
        return articleMapper.changePrivate(userId, clazzId) == 1;
    }

    /**
     * 获取某人自己的文章
     *
     * @param userId    用户 ID。
     * @param articleId 文章 ID。
     * @return 找到的文章，如果不存在，返回 null。
     */
    public Article getMyArticle(int userId, int articleId) {
        Article article = articleMapper.getArticleById(articleId, true);
        return (article != null && article.getUserId() == userId) ? article : null;
    }

    /**
     * 获取文章
     *
     * @param articleId 文章 ID。
     * @return 找到的文章，如果不存在，返回 null
     */
    public Article getArticle(int articleId) {
        return articleMapper.getArticleById(articleId, true);
    }

    /**
     * 获取某一记录 ID 的最新文章。
     *
     * @param recordsId 记录 ID。
     * @return 最新的内容。
     */
    public Content getLastContent(String recordsId) {
        Records records = recordsDao.getRecords(recordsId);
        if (records == null) return null;
        List<Record> rs = records.getRecords();
        Record record = rs.get(rs.size() - 1);
        return contentDao.getContent(record.getContentId().toString());
    }

    public Records getRecords(String recordsId) {
        return recordsDao.getRecords(recordsId);
    }

    /**
     * 获取某一 Id 的正文。
     *
     * @param contentId id。
     * @return 正文。
     */
    public Content getContent(String contentId) {
        return contentDao.getContent(contentId);
    }

    /**
     * 获取某人某分类下的所有文章。
     *
     * @param userId  用户 ID。
     * @param clazzId 分类 ID。
     * @return 该分类下的所有文章。
     */
    public List<Article> getMyArticlesByClazz(int userId, int clazzId) {
        return articleMapper.getArticleByClazzId(userId, clazzId, true);
    }

    /**
     * 获取获赞数量前 10 的用户信息。
     *
     * @return 排名榜。
     */
    public List<UserRank> getUserRank() {
        List<UserRank> articleLikeTop = articleMapper.getUserLikeTop();
        return articleLikeTop == null ? new ArrayList<>() : articleLikeTop;
    }

    /**
     * 获取访问量排行榜
     *
     * @return 排行榜
     */
    public List<Article> getVisitRank() {
        List<Article> articleVisitTop = articleMapper.getArticleVisitTop();
        return articleVisitTop == null ? new ArrayList<>() : articleVisitTop;
    }

    /**
     * 获取用户某一分类的统计信息。 如果 ID <= 0 ，则统计本人所有分类的信息。
     *
     * @param userId  用户 ID。
     * @param clazzId 分类 ID。
     * @return 返回统计信息。
     */
    public Map<String, Long> getStatistics(int userId, int clazzId) {
        HashMap<String, Long> statistics = articleMapper.getStatistics(userId, clazzId);
        if (statistics == null) {
            statistics = new HashMap<>();
            statistics.put("number", 0L);
            statistics.put("like", 0L);
            statistics.put("visit", 0L);
        }
        return statistics;
    }

    public boolean visit(int articleId) {
        return this.articleMapper.visit(articleId) == 1;
    }

    public boolean like(int articleId) {
        return this.articleMapper.like(articleId) == 1;
    }

    private boolean deleteRecords(String id) {
        Records rs = recordsDao.getRecords(id);
        if (rs == null) return false;
        List<Record> records = rs.getRecords();
        for (Record record : records) // 逐条删除实体内容
            contentDao.deleteContent(record.getContentId().toString());
        recordsDao.deleteRecords(id); // 删除记录集合
        return true;
    }
}

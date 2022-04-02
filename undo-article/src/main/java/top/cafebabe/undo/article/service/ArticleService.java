package top.cafebabe.undo.article.service;

import org.springframework.stereotype.Service;
import top.cafebabe.undo.article.bean.Article;
import top.cafebabe.undo.article.bean.Content;
import top.cafebabe.undo.article.bean.Record;
import top.cafebabe.undo.article.bean.Records;
import top.cafebabe.undo.article.dao.ArticleMapper;
import top.cafebabe.undo.article.dao.ContentDao;
import top.cafebabe.undo.article.dao.RecordsDao;
import top.cafebabe.undo.common.util.CurrentUtil;

import java.sql.Timestamp;
import java.util.List;

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
        Records records = new Records();
        article.setRecordsId(records.getId().toString());
        Content content = new Content(con);
        recordsDao.createRecords(records); // 添加记录集合。
        boolean res = (articleMapper.add(article) != 1) && updateContent(article.getId(), "init", content); // 添加文章元数据、文章内容实体。
        if (!res) { // 出现问题回滚
            articleMapper.deleteById(article.getId());
            contentDao.deleteContent(content.getId().toString());
        }
        return true;
    }

    /**
     * 删除文章
     *
     * @param articleId 文章的 ID。
     * @return 是否成功删除。
     */
    public boolean deleteArticle(int articleId) {
        Article article = articleMapper.getArticleById(articleId, true);
        if (article == null || articleMapper.deleteById(articleId) != 1) return false; // 如果没有这篇文章或者说删除的不只有一个，就是有问题，就失败

        List<Records> recordsList = recordsDao.getRecords(article.getRecordsId());
        if (recordsList.size() != 1) return false;
        List<Record> records = recordsList.get(0).getRecords();
        for (Record record : records) // 逐条删除实体内容
            contentDao.deleteContent(record.getContentId().toString());
        recordsDao.deleteRecords(article.getRecordsId()); // 删除记录集合
        return true;
    }

    /**
     * 更新内容。
     *
     * @param articleId 更新的文章 ID。
     * @param summary   更新的摘要。
     * @param content   新的内容。
     * @return 是否更新成功。
     */
    public boolean updateContent(int articleId, String summary, Content content) {
        Article article = articleMapper.getArticleById(articleId, true);
        if (article == null) return false; // 如果没有该文章肯定是失败
        contentDao.addContent(content);
        if (!recordsDao.putRecord(article.getRecordsId(), new Record(summary, content.getId()))) return false; // 添加更新记录
        return articleMapper.setUpdateTime(articleId, new Timestamp(CurrentUtil.now())) == 1; // 更新更新时间
    }
}

package top.cafebabe.undo.article.dao;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import top.cafebabe.undo.article.bean.Content;

import java.util.List;

/**
 * @author cafababe
 * 操作文章的实际的实体内容。
 */
@Repository
public class ContentDao {

    final MongoTemplate mongoTemplate;

    private static final String CONTENT_COLLECTION_NAME = "content";

    public ContentDao(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * 向 mongo 中添加一个文章的实体内容。
     *
     * @param content 文章内容。
     */
    public void addContent(Content content) {
        mongoTemplate.insert(content, CONTENT_COLLECTION_NAME);
    }

    /**
     * 删除一篇文章的实体内容。
     *
     * @param contentId 文章的 ID。
     */
    public void deleteContent(String contentId) {
        Criteria criteria = Criteria.where("_id").is(contentId);
        mongoTemplate.remove(new Query(criteria), CONTENT_COLLECTION_NAME);
    }

    public Content getContent(String contentId) {
        Criteria criteria = Criteria.where("_id").is(contentId);
        List<Content> contents = mongoTemplate.find(new Query(criteria), Content.class);
        return contents.size() == 1 ? contents.get(0) : null;
    }
}

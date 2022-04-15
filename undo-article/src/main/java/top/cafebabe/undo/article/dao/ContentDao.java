package top.cafebabe.undo.article.dao;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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

    /**
     * 获取文章。
     *
     * @param contentId 文章 ID。
     * @return 文章内容
     */
    public Content getContent(String contentId) {
        Criteria criteria = Criteria.where("_id").is(contentId);
        List<Content> contents = mongoTemplate.find(new Query(criteria), Content.class);
        return contents.size() == 1 ? contents.get(0) : null;
    }

    /**
     * 判断一个正文是不是被审核过。
     *
     * @param contentId 正文 ID。
     * @return 审核过返回 true，不存在或未审核返回 false。
     */
    public boolean isReview(String contentId) {
        Criteria criteria = Criteria.where("_id").is(contentId).and("isReview").is(Content.REVIEW_PASS);
        List<Content> contents = mongoTemplate.find(new Query(criteria), Content.class);
        return !contents.isEmpty();
    }

    public Content getOneNotReview() {
        Criteria criteria = Criteria.where("isReview").is(Content.NOT_REVIEW);
        Query query = new Query(criteria);
        query.with(PageRequest.of(0, 1));
        List<Content> contents = mongoTemplate.find(query, Content.class);
        return contents.size() == 0 ? null : contents.get(0);
    }

    public boolean pass(String id) {
        return setReview(id, Content.REVIEW_PASS);
    }

    public boolean denied(String id) {
        return setReview(id, Content.REVIEW_DENIED);
    }

    public long getNotReviewNumber() {
        Criteria criteria = Criteria.where("isReview").is(Content.NOT_REVIEW);
        return mongoTemplate.count(new Query(criteria), CONTENT_COLLECTION_NAME);
    }

    private boolean setReview(String id, int newVal) {
        Criteria criteria = Criteria.where("_id").is(id);
        Update update = new Update();
        update.set("isReview", newVal);
        return mongoTemplate.updateFirst(new Query(criteria), update, Content.class).getModifiedCount() == 1;
    }
}

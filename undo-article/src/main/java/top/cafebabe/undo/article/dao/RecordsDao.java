package top.cafebabe.undo.article.dao;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import top.cafebabe.undo.article.bean.AppConfig;
import top.cafebabe.undo.article.bean.Record;
import top.cafebabe.undo.article.bean.Records;

import java.util.List;


/**
 * @author cafababe
 * 用来管理文章的修改记录。
 */
@Repository
public class RecordsDao {

    final MongoTemplate mongoTemplate;

    public RecordsDao(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * 创建保存文章记录的集合。
     *
     * @param records 需要添加的集合。
     */
    public void createRecords(Records records) {
        mongoTemplate.insert(records, AppConfig.RECORDS_COLLECTION_NAME);
    }

    /**
     * 删除一个文章修改记录的集合。
     *
     * @param recordId ID。
     */
    public void deleteRecords(String recordId) {
        Criteria criteria = Criteria.where("_id").is(recordId);
        mongoTemplate.remove(new Query(criteria), AppConfig.RECORDS_COLLECTION_NAME);
    }

    /**
     * 向集合中添加一条记录。
     *
     * @param recordsId 集合的 ID。
     * @param record    记录的内容。
     * @return 是否添加成功。
     */
    public boolean putRecord(String recordsId, Record record) {
        Criteria criteria = Criteria.where("_id").is(recordsId);
        Query query = new Query(criteria);
        if (mongoTemplate.find(query, Records.class, AppConfig.RECORDS_COLLECTION_NAME).size() != 1) return false;
        Update update = new Update();
        update.push("records").atPosition(1).value(record);
        return mongoTemplate.updateFirst(query, update, AppConfig.RECORDS_COLLECTION_NAME).getModifiedCount() == 1;
    }

    /**
     * 获取记录。
     *
     * @param recordsId 记录的 ID。
     * @return 存放记录的列表。
     */
    public Records getRecords(String recordsId) {
        Criteria criteria = Criteria.where("_id").is(recordsId);
        List<Records> records = mongoTemplate.find(new Query(criteria), Records.class, AppConfig.RECORDS_COLLECTION_NAME);
        if (records.size() != 1)
            return null;
        Records rs = records.get(0);
        rs.sort();
        return rs;
    }
}

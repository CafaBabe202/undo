package top.cafebabe.undo.article.bean;

import lombok.Data;
import org.bson.types.ObjectId;

import java.sql.Time;

/**
 * @author cafababe
 */
@Data
public class Record {
    private String summary;
    private ObjectId contentId;

    public Record(String summary, ObjectId contentId) {
        this.summary = summary;
        this.contentId = contentId;
    }
}

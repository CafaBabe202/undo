package top.cafebabe.undo.article.bean;

import lombok.Data;
import org.bson.types.ObjectId;

/**
 * @author cafababe
 */
@Data
public class Content {
    private ObjectId id;
    private boolean isReview;
    private String content;

    public Content(String content) {
        this.id = new ObjectId();
        this.isReview = false;
        this.content = content;
    }
}

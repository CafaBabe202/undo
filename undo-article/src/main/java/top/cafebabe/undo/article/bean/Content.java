package top.cafebabe.undo.article.bean;

import lombok.Data;
import org.bson.types.ObjectId;

/**
 * @author cafababe
 */
@Data
public class Content {
    private ObjectId id;
    private int isReview;
    private String content;

    public static final int NOT_REVIEW = 0;
    public static final int REVIEW_PASS = 1;
    public static final int REVIEW_DENIED = -1;

    public Content(String content) {
        this.id = new ObjectId();
        this.isReview = NOT_REVIEW;
        this.content = content;
    }

    public boolean isReview() {
        return this.isReview == REVIEW_PASS;
    }
}

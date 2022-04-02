package top.cafebabe.undo.article.bean;

import lombok.Data;
import org.bson.types.ObjectId;

import java.util.LinkedList;
import java.util.List;

/**
 * @author cafababe
 */
@Data
public class Records {
    private ObjectId id;
    private List<Record> records;

    public Records() {
        this.id = new ObjectId();
        this.records = new LinkedList<>();
    }
}

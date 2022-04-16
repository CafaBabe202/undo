package top.cafebabe.fileManager.bean;

import lombok.Data;
import org.bson.types.ObjectId;
import top.cafebabe.fileManager.utils.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cafababe
 */
@Data
public class TempBlockFile {
    private ObjectId id;
    private long size;
    private long createTime;
    private long lastUpdateTime;
    private List<String> content;

    public TempBlockFile() {
        this.id = new ObjectId();
        this.size = 0;
        this.createTime = Util.now();
        this.lastUpdateTime = Util.now();
        this.content = new ArrayList<>();
    }
}

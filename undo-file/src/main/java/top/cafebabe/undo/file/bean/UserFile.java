package top.cafebabe.undo.file.bean;

import lombok.Data;
import top.cafebabe.undo.file.util.NowUtil;

/**
 * @author cafababe
 */
@Data
public class UserFile {
    private int id;
    private int userId;
    private String name;
    private long size;
    private String md5;
    private String createTime;
    private boolean isPrivate;
    private boolean isReview;

    public UserFile() {
    }

    public UserFile(int userId, String name, long size, String md5) {
        this.userId = userId;
        this.name = name;
        this.size = size;
        this.md5 = md5;
        this.createTime = NowUtil.nowTime();
    }
}

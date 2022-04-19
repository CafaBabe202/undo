package top.cafebabe.undo.file.bean;

import lombok.Data;
import top.cafebabe.undo.file.util.NowUtil;

/**
 * @author cafababe
 */
@Data
public class UserFile {
    private String id;
    private int userId;
    private String name;
    private long size;
    private String md5;
    private String createTime;
    private boolean isPrivate;
    private boolean isReview;

    public UserFile(String id, int userId, String name, long size, String md5) {
        System.out.println(id);
        System.out.println(id.length());
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.size = size;
        this.md5 = md5;
        this.createTime = NowUtil.nowTime();
    }
}

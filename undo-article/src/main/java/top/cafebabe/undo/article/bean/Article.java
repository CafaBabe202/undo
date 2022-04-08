package top.cafebabe.undo.article.bean;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author cafababe
 */
@Data
public class Article {
    private int id;
    private int userId;
    private String title;
    private String summary;
    private String recordsId;
    private int like;
    private int visit;
    private int clazzId;
    private String clazzName;
    private Timestamp updateTime;
    private Timestamp createTime;
    private boolean isPrivate;
}

package top.cafebabe.undo.article.bean;

import lombok.Data;

/**
 * @author cafababe
 */
@Data
public class Say {
    private int id;
    private int userId;
    private String username;
    private int articleId;
    private String content;
    private String time;
}

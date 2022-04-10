package top.cafebabe.undo.article.form;

import lombok.Data;

/**
 * @author cafababe
 */
@Data
public class EditArticleForm {
    private String id;
    private String title;
    private String summary;
    private String content;
    private int clazzId;
    private boolean isPrivate;
}

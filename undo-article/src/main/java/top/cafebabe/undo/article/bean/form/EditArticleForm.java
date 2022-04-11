package top.cafebabe.undo.article.bean.form;

import lombok.Data;

/**
 * @author cafababe
 */
@Data
public class EditArticleForm {
    private int id;
    private String title;
    private String summary;
    private String updateSummary;
    private String content;
    private int clazzId;
    private boolean isPrivate;
}

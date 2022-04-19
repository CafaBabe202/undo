package top.cafebabe.undo.user.bean.form;

import lombok.Data;

/**
 * @author cafababe
 */
@Data
public class ChangePassForm {
    private String oldPass;
    private String newPass;
}

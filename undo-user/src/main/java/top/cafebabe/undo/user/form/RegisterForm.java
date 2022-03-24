package top.cafebabe.undo.user.form;

import lombok.Data;

/**
 * @author cafababe
 */
@Data
public class RegisterForm {
    private String username;
    private String email;
    private String password;
    private String code;
}

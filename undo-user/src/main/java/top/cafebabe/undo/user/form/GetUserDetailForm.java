package top.cafebabe.undo.user.form;

import lombok.Data;

/**
 * @author cafababe
 * 通过其他组件或第三方应用获取用户的信息。
 */
@Data
public class GetUserDetailForm {
    private String userToken;
    private String detail;
}

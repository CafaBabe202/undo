package top.cafebabe.undo.common.bean;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author cafababe
 */
@Data
public class LoginUser {
    private int id;
    private String username;
    private String email;
    private String avatar;
    private String sign;
    private Timestamp createTime;
    private long loginTime;
    private String ip;
    private String userAgent;
}

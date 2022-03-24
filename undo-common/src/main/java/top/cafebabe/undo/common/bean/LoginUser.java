package top.cafebabe.undo.common.bean;

import lombok.Data;

/**
 * @author cafababe
 */
@Data
public class LoginUser {
    private int id;
    private long loginTime;
    private String ip;
    private String userAgent;
}

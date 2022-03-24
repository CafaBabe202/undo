package top.cafebabe.undo.common.bean;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author cafababe
 */
@Data
public class SysUser {
    private int id;
    private String username;
    private String password;
    private String email;
    private String avatar;
    private String sign;
    private Timestamp createTime;
    private boolean isDelete;
}

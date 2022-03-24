package top.cafebabe.undo.user.util;

import top.cafebabe.undo.common.bean.SysUser;
import top.cafebabe.undo.user.form.RegisterForm;

/**
 * @author cafababe
 */
public class ClassConverter {
    public static SysUser toSysUser(RegisterForm form) {
        SysUser user = new SysUser();
        user.setUsername(form.getUsername());
        user.setPassword(form.getPassword());
        user.setEmail(form.getEmail());
        return user;
    }
}

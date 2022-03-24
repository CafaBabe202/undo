package top.cafebabe.undo.user.util;

import top.cafebabe.undo.common.bean.SysUser;
import top.cafebabe.undo.user.form.RegisterForm;

/**
 * @author cafababe
 */
public class Checker {
    public static boolean RegisterForm(RegisterForm form) {
        return checkStringLen(form.getEmail(), 32, false)
                && checkStringLen(form.getUsername(), 16, false)
                && checkStringLen(form.getPassword(), 128, false)
                && form.getCode() != null;
    }

    public static boolean checkStringLen(String str, int len, boolean nullAble) {
        return str == null ? nullAble : str.length() <= len;
    }
}

package top.cafebabe.undo.user.util;

import top.cafebabe.undo.common.util.StringUtil;
import top.cafebabe.undo.user.bean.AppConfig;
import top.cafebabe.undo.user.form.LoginForm;
import top.cafebabe.undo.user.form.RegisterForm;
import top.cafebabe.undo.user.form.SetForm;

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

    public static boolean LoginForm(LoginForm form) {
        return form.getId() > 0 && form.getId() < Integer.MAX_VALUE && checkStringLen(form.getPassword(), 128, false);
    }

    public static boolean setForm(SetForm form) {
        switch (form.getField()) {
            case "username":
                return Checker.checkUsername(form.getNewVal());
            case "password":
                return Checker.checkPassword(form.getNewVal());
            case "email":
                return Checker.checkEmail(form.getNewVal());
            case "sign":
                return Checker.checkSign(form.getNewVal());
        }
        return false;
    }

    public static boolean checkStringLen(String str, int len, boolean nullAble) {
        return str == null ? nullAble : str.length() <= len;
    }

    public static boolean checkUsername(String username) {
        return !StringUtil.hasBlank(username) && username.length() <= AppConfig.USERNAME_LEN;
    }

    public static boolean checkPassword(String password) {
        return !StringUtil.hasBlank(password) && password.length() <= AppConfig.PASSWORD_LEN;
    }

    public static boolean checkEmail(String email) {
        return StringUtil.isMatch(AppConfig.EMAIL_PATTEN, email);
    }

    public static boolean checkSign(String sign) {
        return !StringUtil.hasBlank(sign) && sign.length() <= AppConfig.SIGN_LEN;
    }
}

package top.cafebabe.undo.user.util;

import top.cafebabe.undo.common.util.StringUtil;
import top.cafebabe.undo.user.bean.AppConfig;
import top.cafebabe.undo.user.bean.form.*;

/**
 * @author cafababe
 */
public class Checker {
    public static boolean check(RegisterForm form) {
        return checkStringLen(form.getEmail(), 32)
                && checkStringLen(form.getUsername(), 16)
                && checkStringLen(form.getPassword(), 128)
                && form.getCode() != null;
    }

    public static boolean check(LoginForm form) {
        return checkEmail(form.getEmail()) && checkStringLen(form.getPassword(), 128);
    }

    public static boolean check(ChangePassForm form) {
        return form.getOldPass().length() > 0 && checkPassword(form.getNewPass());
    }

    public static boolean check(SetForm form) {
        if (form == null || form.getField() == null || form.getNewVal() == null)
            return false;
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

    public static boolean check(GetPublicDetailForm form) {
        if (form == null || form.getIds() == null || form.getIds().size() == 0)
            return false;
        for (Integer integer : form.getIds())
            if (integer == null)
                return false;
        return true;
    }

    private static boolean checkStringLen(String str, int len) {
        return str != null && str.length() <= len;
    }

    private static boolean checkUsername(String username) {
        return !StringUtil.hasBlank(username) && username.length() <= AppConfig.USERNAME_LEN;
    }

    private static boolean checkPassword(String password) {
        return !StringUtil.hasBlank(password) && password.length() <= AppConfig.PASSWORD_LEN;
    }

    private static boolean checkEmail(String email) {
        return StringUtil.isMatch(AppConfig.EMAIL_PATTEN, email);
    }

    private static boolean checkSign(String sign) {
        return !StringUtil.hasBlank(sign) && sign.length() <= AppConfig.SIGN_LEN;
    }
}

package top.cafebabe.undo.article.util;

import top.cafebabe.undo.article.bean.AppConfig;
import top.cafebabe.undo.common.bean.LoginUser;

import javax.servlet.http.HttpSession;

/**
 * 通过 Session 获取登录的用户
 *
 * @author cafababe
 */
public class SessionUtil {

    public static LoginUser getLoginUser(HttpSession session) {
        Object attribute = session.getAttribute(AppConfig.LOGIN_USER_KEY_IN_SESSION);
        return (attribute instanceof LoginUser) ?
                (LoginUser) attribute : null;
    }
}

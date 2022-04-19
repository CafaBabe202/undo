package top.cafebabe.undo.article.service;

import org.springframework.stereotype.Service;
import top.cafebabe.undo.article.bean.AppConfig;
import top.cafebabe.undo.article.util.HttpUtils;
import top.cafebabe.undo.common.bean.LoginUser;
import top.cafebabe.undo.common.bean.ResponseMessage;
import top.cafebabe.undo.common.util.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cafababe
 */
@Service
public class TokenService {

    /**
     * 这个方法向 user 模块认证用户的 token 是否有效。
     *
     * @param userToken 用户 token。
     * @return 返回该 token 代表的用户，如果 token 无效，将返回 null。
     */
    public LoginUser getLoginUser(String userToken) {
        String get = HttpUtils.GET(AppConfig.USER_GET_LOGIN_USER_API + userToken, getAppHeader(), null, AppConfig.HTTP_TIMEOUT);
        if (get == null) return null;

        ResponseMessage message = StringUtil.pareJson(get, ResponseMessage.class);
        if (message == null || message.getStatus() != ResponseMessage.STATUS_OK) return null;

        return StringUtil.pareJson(StringUtil.toJson(message.getData()), LoginUser.class);
    }

    private Map<String, List<String>> getAppHeader() {
        Map<String, List<String>> res = new HashMap<>();
        res.put(AppConfig.INNER_APP_PASSWORD_NAME_IN_HEADER, List.of(AppConfig.INNER_APP_PASSWORD));
        return res;
    }
}

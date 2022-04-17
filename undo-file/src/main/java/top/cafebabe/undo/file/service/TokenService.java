package top.cafebabe.undo.file.service;

import org.springframework.stereotype.Service;
import top.cafebabe.undo.common.bean.LoginUser;
import top.cafebabe.undo.common.bean.ResponseMessage;
import top.cafebabe.undo.common.util.StringUtil;
import top.cafebabe.undo.file.bean.AppConfig;
import top.cafebabe.undo.file.util.HttpUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cafababe
 */
@Service
public class TokenService {

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

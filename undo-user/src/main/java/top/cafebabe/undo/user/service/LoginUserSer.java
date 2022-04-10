package top.cafebabe.undo.user.service;

import org.springframework.stereotype.Service;
import top.cafebabe.undo.common.bean.LoginUser;
import top.cafebabe.undo.common.bean.SysUser;
import top.cafebabe.undo.common.util.CurrentUtil;
import top.cafebabe.undo.common.util.TokenUtil;
import top.cafebabe.undo.user.bean.AppConfig;
import top.cafebabe.undo.user.dao.TokenRedis;
import top.cafebabe.undo.user.util.ClassConverter;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * @author cafababe
 * 操作登录用户的缓存信息。主要是操作 Redis。
 */
@Service
public class LoginUserSer {

    final TokenRedis tokenRedis;

    public LoginUserSer(TokenRedis tokenRedis) {
        this.tokenRedis = tokenRedis;
    }

    /**
     * 通过 Token 获取用户的登录信息。
     *
     * @param token 用户 Token。
     * @return 用户的登录信息，如果 Token 不存在，返回 null。
     */
    public LoginUser getUseByToken(String token) {
        return tokenRedis.getUser(TokenUtil.getLoginTokenId(token, AppConfig.TOKEN_KEY));
    }

    /**
     * 向 redis 中添加一个 Token。
     *
     * @param user 用户对象。
     * @param ip   用户的 ip。
     * @return 创建并保存的 Token，如果保存失败将返回 null。
     */
    public String saveToken(SysUser user, String ip) {
        LoginUser loginUser = ClassConverter.toLoginUser(user);
        loginUser.setLoginTime(CurrentUtil.now());
        loginUser.setIp(ip);
        String loginToken = TokenUtil.createLoginToken(user.getId(), ip, AppConfig.TOKEN_KEY);
        return tokenRedis.putToken(loginToken, loginUser) ? loginToken : null;
    }

    /**
     * 通过反射更新 Redis 缓存中的用户数据。
     *
     * @param token  用户的 Token。
     * @param field  更新的字段。
     * @param newVal 新的值。
     * @return 是否更新成功。
     */
    public boolean updateRedisUser(String token, String field, String newVal) {
        int id = TokenUtil.getLoginTokenId(token, AppConfig.TOKEN_KEY);
        LoginUser user = tokenRedis.getUser(id);
        try {
            Field declaredField = user.getClass().getDeclaredField(field);
            declaredField.setAccessible(true);
            declaredField.set(user, newVal);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return tokenRedis.putToken(token, user);
    }

    /**
     * 获取用户信息。
     *
     * @param token token。
     * @return Map 形式的用户信息。
     */
    public Map<String, String> userGetDetail(String token) {
        LoginUser user = getUseByToken(token);
        if (user == null) return null;
        Map<String, String> userMap = ClassConverter.objectToMap(user);
        userMap.remove("loginTime");
        userMap.remove("ip");
        userMap.remove("userAgent");
        return userMap;
    }

}

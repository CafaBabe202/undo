package top.cafebabe.undo.user.service;

import org.springframework.stereotype.Service;
import top.cafebabe.undo.common.bean.LoginUser;
import top.cafebabe.undo.common.util.TokenUtil;
import top.cafebabe.undo.user.bean.AppConfig;
import top.cafebabe.undo.user.dao.TokenRedis;

/**
 * @author cafababe
 * 操作登录用户的缓存信息。主要是操作 Redis。
 * 以后将 SysUserSer 中操作 Redis 的操作转移到本类中。
 */
@Service
public class LoginUserSer {
    final TokenRedis tokenRedis;

    public LoginUserSer(TokenRedis tokenRedis) {
        this.tokenRedis = tokenRedis;
    }

    public LoginUser getUseByToken(String token) {
        return tokenRedis.getUser(TokenUtil.getLoginTokenId(token, AppConfig.TOKEN_KEY));
    }
}

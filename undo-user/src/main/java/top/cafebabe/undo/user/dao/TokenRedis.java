package top.cafebabe.undo.user.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import top.cafebabe.undo.common.bean.LoginUser;
import top.cafebabe.undo.common.util.StringUtil;
import top.cafebabe.undo.user.bean.AppConfig;

import java.util.concurrent.TimeUnit;

/**
 * @author cafababe
 * 对接 redis 负责 Token 的全局服务。
 */
@Repository
public class TokenRedis {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 向 redis 中添加一个用户的 token。
     *
     * @param token 用户的 token，将被用来当作 redis 的值。
     * @param user  用户信息。
     * @return 返回是否添加成功。
     */
    public boolean putToken(String token, LoginUser user) {
        BoundHashOperations<String, Object, Object> key = redisTemplate.boundHashOps(AppConfig.REDIS_TOKEN_PREFIX + user.getId());
        key.put(AppConfig.REDIS_TOKEN_VALUE_TOKEN_KEY, token);
        key.put(AppConfig.REDIS_TOKEN_VALUE_USER_KEY, StringUtil.toJson(user));
        key.expire(AppConfig.TOKEN_TIME_OUT, TimeUnit.SECONDS);
        return true;
    }

    public String getToken(int id) {
        BoundHashOperations<String, Object, Object> key = redisTemplate.boundHashOps(AppConfig.REDIS_TOKEN_PREFIX + id);
        Object o = key.get(AppConfig.REDIS_TOKEN_VALUE_TOKEN_KEY);
        return o == null ? null : o.toString();
    }

    public LoginUser getUser(int id) {
        BoundHashOperations<String, Object, Object> key = redisTemplate.boundHashOps(AppConfig.REDIS_TOKEN_PREFIX + id);
        Object o = key.get(AppConfig.REDIS_TOKEN_VALUE_USER_KEY);
        return o == null ? null : StringUtil.pareJson(o.toString(), LoginUser.class);
    }
}

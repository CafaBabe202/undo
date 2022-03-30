package top.cafebabe.undo.user.dao;

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
    private final RedisTemplate<String, String> redisTemplate;

    public TokenRedis(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 向 redis 中添加一个用户的 token。
     *
     * @param token 用户的 token。
     * @param user  用户信息。
     * @return 返回是否添加成功。
     */
    public boolean putToken(String token, LoginUser user) {
        BoundHashOperations<String, Object, Object> key = redisTemplate.boundHashOps(AppConfig.REDIS_TOKEN_PREFIX + user.getId());
        key.put(AppConfig.REDIS_TOKEN_VALUE_TOKEN_KEY, token);
        key.put(AppConfig.REDIS_TOKEN_VALUE_USER_KEY, StringUtil.toJson(user));
        key.expire(AppConfig.TOKEN_TIME_OUT, TimeUnit.MINUTES);
        return true;
    }

    /**
     * 根据用户的 id 获取该用户的 token。
     *
     * @param id 用户 ID。
     * @return 如果 redis 中存在该用户的 Token 就返回，没有就返回 null。
     */
    public String getToken(int id) {
        BoundHashOperations<String, String, String> key = redisTemplate.boundHashOps(AppConfig.REDIS_TOKEN_PREFIX + id);
        return key.get(AppConfig.REDIS_TOKEN_VALUE_TOKEN_KEY);
    }

    /**
     * 根据用户的 id 获取登录用户对象。
     *
     * @param id 用户 ID。
     * @return 如果 Redis 中存在该用户的 id 就返回，如果没有就返回 null。
     */
    public LoginUser getUser(int id) {
        BoundHashOperations<String, String, String> key = redisTemplate.boundHashOps(AppConfig.REDIS_TOKEN_PREFIX + id);
        String s = key.get(AppConfig.REDIS_TOKEN_VALUE_USER_KEY);
        return s == null ? null : StringUtil.pareJson(s, LoginUser.class);
    }
}

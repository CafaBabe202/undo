package top.cafebabe.undo.user.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
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

    private static final String TOKEN_PREFIX = "Token:";

    /**
     * 向 redis 中添加一个用户的 token。
     *
     * @param id    用户的 id，将被用来当作 redis 的值。
     * @param token 用户的 token，将被用来当作 redis 的键。
     * @return 返回是否添加成功。
     */
    public boolean putToken(int id, String token) {
        BoundValueOperations<String, String> key = redisTemplate.boundValueOps(TOKEN_PREFIX + String.valueOf(id));
        key.set(token);
        return Boolean.TRUE.equals(key.expire(AppConfig.TOKEN_TIME_OUT, TimeUnit.SECONDS));
    }

    /**
     * 判断给定的 Token 是否存在 redis 中。
     *
     * @param token 需要检验的 token。
     * @return 返回是否存在 redis 中。
     */
    public boolean checkToken(String token) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(TOKEN_PREFIX + token));
    }
}

package top.cafebabe.undo.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.cafebabe.undo.common.bean.LoginUser;
import top.cafebabe.undo.common.bean.SysUser;
import top.cafebabe.undo.common.util.CurrentUtil;
import top.cafebabe.undo.common.util.TokenUtil;
import top.cafebabe.undo.user.bean.AppConfig;
import top.cafebabe.undo.user.dao.SysUserMapper;
import top.cafebabe.undo.user.dao.TokenRedis;
import top.cafebabe.undo.user.util.ClassConverter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author cafababe
 */
@Service
public class SysUserSer {
    @Autowired
    SysUserMapper userMapper;

    @Autowired
    TokenRedis tokenRedis;

    /**
     * 注册用户的服务。主要用来向数据库中插入一个合法的用户数据。但是在这个用户对象中原来的 createTime,avatar,sing,属性均不生效，因为在添加之前这些属性将被重置为 AppConfig 中的默认值。
     * <b>注意：</b> 当前版本中，这里并不会对数据的合法行进行检查，这需要在 controller 中进行数据的合法性检查。
     *
     * @param user 一个用户对象。
     * @return 是否创建成功。
     */
    public boolean register(SysUser user) {
        if (userMapper.getByUsername(user.getUsername()) != null)
            return false;
        user.setCreateTime(new Timestamp(CurrentUtil.now()));
        user.setAvatar(AppConfig.DEFAULT_USER_AVATAR);
        user.setSign(AppConfig.DEFAULT_USER_SIGN);
        return userMapper.addUser(user) == 1;
    }

    /**
     * 检查用户的密码是否正确。
     *
     * @param id       用户 id。
     * @param password 密码。
     * @return true：正确；false：不正确。
     */
    public SysUser checkPassword(int id, String password) {
        SysUser byUserId = userMapper.getByUserId(id);
        return (byUserId != null && byUserId.getPassword().equals(password)) ? byUserId : null;
    }

    public boolean setUsername(int id, String username) {
        return userMapper.setUsername(id, username) == 1;
    }

    public boolean setPassword(int id, String password) {
        return userMapper.setPassword(id, password) == 1;
    }

    public boolean setEmail(int id, String email) {
        return userMapper.setEmail(id, email) == 1;
    }

    public boolean setAvatar(int id, String avatar) {
        return userMapper.setAvatar(id, avatar) == 1;
    }

    public boolean setSign(int id, String sign) {
        return userMapper.setSign(id, sign) == 1;
    }

    /**
     * 向 redis 中添加一个 Token，你只需要的提供用户的 id 和 登录 ip，生成 Token 的动作已经集成好了。
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
     * 获取用户信息。
     *
     * @param id 用户 id。
     * @return Map 形式的用户信息。
     */
    public Map<String, String> userDetail(int id) {
        SysUser byUserId = userMapper.getByUserId(id);
        if (byUserId == null)
            return null;
        Map<String, String> res = ClassConverter.objectToMap(byUserId);
        res.remove("password");
        res.remove("createTime");
        res.remove("isDelete");
        return res;
    }

    /**
     * 获取一个用户的公开信息，包括 id、头像、签名等信息。
     *
     * @param id 用户的 id。
     * @return 以 Map 的形式保存的用户公开信息。
     */
    public Map<String, String> getSysUserPublicDetail(int id) {
        Map<String, String> user = userDetail(id);
        if (user != null) user.remove("email");
        return user;
    }

    /**
     * 同时获取多个用户的公开信息。
     *
     * @param ids 想要查询的 id 的集合。
     * @return 用户的公开信息，这是一个包含 N 个 Map 的 List。
     */
    public List<Map<String, String>> getSysUserPublicDetail(List<Integer> ids) {
        List<Map<String, String>> res = new ArrayList<>();
        for (int id : ids) {
            Map<String, String> now = getSysUserPublicDetail(id);
            if (now == null) return new ArrayList<>();
            res.add(now);
        }
        return res;
    }
}

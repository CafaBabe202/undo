package top.cafebabe.undo.user.service;

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
    final SysUserMapper userMapper;

    final TokenRedis tokenRedis;

    public SysUserSer(SysUserMapper userMapper, TokenRedis tokenRedis) {
        this.userMapper = userMapper;
        this.tokenRedis = tokenRedis;
    }

    /**
     * 注册用户的服务。主要用来向数据库中插入一个合法的用户数据。但是在这个用户对象中原来的 createTime,avatar,sing 属性将被重置为 AppConfig 中的默认值。
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
     * @param email    用户邮箱。
     * @param password 密码。
     * @return 返回登录的用户对象，但是已经抹除了密码，防止后边的编写错误造成密码泄漏。
     */
    public SysUser checkPassword(String email, String password) {
        SysUser byUserId = userMapper.getByUserEmail(email);
        if (byUserId != null && byUserId.getPassword().equals(password)) {
            byUserId.setPassword("");
            return byUserId;
        }
        return null;
    }

    public boolean existEmail(String email) {
        return userMapper.checkEmail(email) == 1;
    }

    public boolean existUsername(String username) {
        return userMapper.checkUsername(username) == 1;
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
     * 获取用户信息。
     *
     * @param token token。
     * @return Map 形式的用户信息。
     */
    public Map<String, String> userDetail(String token) {
        LoginUser user = tokenRedis.getUser(TokenUtil.getLoginTokenId(token, AppConfig.TOKEN_KEY));
        if (user == null) return null;
        Map<String, String> userMap = ClassConverter.objectToMap(user);
        userMap.remove("loginTime");
        userMap.remove("ip");
        userMap.remove("userAgent");
        return userMap;
    }

    /**
     * 获取一个用户的公开信息，包括 id、头像、签名等信息。
     *
     * @param id 用户的 id。
     * @return 以 Map 的形式保存的用户公开信息。
     */
    public Map<String, String> getSysUserPublicDetail(int id) {
        SysUser user = userMapper.getByUserId(id);
        if (user == null)
            return null;

        Map<String, String> userMap = ClassConverter.objectToMap(user);
        userMap.remove("password");
        userMap.remove("email");
        userMap.remove("createTime");
        userMap.remove("isDelete");
        return userMap;
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

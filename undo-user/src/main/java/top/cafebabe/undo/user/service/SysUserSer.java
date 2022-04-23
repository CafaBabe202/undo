package top.cafebabe.undo.user.service;

import org.springframework.stereotype.Service;
import top.cafebabe.undo.common.bean.SysUser;
import top.cafebabe.undo.common.util.CurrentUtil;
import top.cafebabe.undo.common.util.TokenUtil;
import top.cafebabe.undo.user.bean.AppConfig;
import top.cafebabe.undo.user.dao.SysUserMapper;
import top.cafebabe.undo.user.util.ClassConverter;
import top.cafebabe.undo.common.util.Md5Util;

import java.io.FileOutputStream;
import java.io.OutputStream;
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

    public SysUserSer(SysUserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * 注册用户的服务。主要用来向数据库中插入一个合法的用户数据。但是在这个用户对象中原来的 createTime,avatar,sing 属性将被重置为 AppConfig 中的默认值。
     * <b>注意：</b> 当前版本中，这里并不会对数据的合法行进行检查，这需要在 controller 中进行数据的合法性检查。
     *
     * @param user 一个用户对象。
     * @return 是否创建成功。
     */
    public boolean register(SysUser user) {
        user.setCreateTime(new Timestamp(CurrentUtil.now()));
        user.setAvatar(AppConfig.AVATAR_BASE_URL + AppConfig.DEFAULT_USER_AVATAR);
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
        SysUser user = userMapper.getByUserEmail(email);

        if (user == null || !user.getPassword().equals(password))
            return null;

        user.setPassword("");
        return user;
    }

    /**
     * 判断给定的邮箱是否存在。
     *
     * @param email 邮箱。
     * @return 存在：true；不存在：false。
     */
    public boolean existEmail(String email) {
        return userMapper.checkEmail(email) == 1;
    }

    /**
     * 判断制定的用户名是否存在。
     *
     * @param username 用户名。
     * @return 存在：true；不存在：false。
     */
    public boolean existUsername(String username) {
        return userMapper.checkUsername(username) == 1;
    }

    // 设置用户名
    public boolean setUsername(String token, String username) {
        return userMapper.setUsername(TokenUtil.getLoginTokenId(token, AppConfig.TOKEN_KEY), username) == 1;
    }

    // 设置邮箱
    public boolean setEmail(String token, String email) {
        return userMapper.setEmail(TokenUtil.getLoginTokenId(token, AppConfig.TOKEN_KEY), email) == 1;
    }

    // 设置签名
    public boolean setSign(String token, String sign) {
        return userMapper.setSign(TokenUtil.getLoginTokenId(token, AppConfig.TOKEN_KEY), sign) == 1;
    }

    // 设置头像
    public String setAvatar(String token, byte[] avatar) {
        int id = TokenUtil.getLoginTokenId(token, AppConfig.TOKEN_KEY);
        Md5Util md5Util = new Md5Util();
        md5Util.update(avatar, 0, avatar.length);
        String md5 = md5Util.design();

        try {
            OutputStream os = new FileOutputStream(AppConfig.LOCAL_AVATAR_DIR + md5);
            os.write(avatar);
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        String url = AppConfig.AVATAR_BASE_URL + md5;
        if (userMapper.setAvatar(id, url) == 1)
            return url;

        return null;
    }

    // 设置密码
    public boolean setPassword(int id, String password) {
        return userMapper.setPassword(id, password) == 1;
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

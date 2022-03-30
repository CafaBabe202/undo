package top.cafebabe.undo.user.dao;

import org.apache.ibatis.annotations.Mapper;
import top.cafebabe.undo.common.bean.SysUser;

/**
 * @author cafababe
 */
@Mapper
public interface SysUserMapper {
    int addUser(SysUser user);

    int deleteUser(int id);

    int setUsername(int id, String name);

    int setPassword(int id, String password);

    int setEmail(int id, String email);

    int setAvatar(int id, String md5);

    int setSign(int id, String sing);

    SysUser getByUserId(int id);

    SysUser getByUserEmail(String email);

    SysUser getByUsername(String name);

    int checkUsername(String username);

    int checkEmail(String email);
}

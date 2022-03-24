package top.cafebabe.undo.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.cafebabe.undo.common.bean.SysUser;
import top.cafebabe.undo.common.util.CurrentUtil;
import top.cafebabe.undo.user.dao.SysUserMapper;

import java.sql.Timestamp;

/**
 * @author cafababe
 */
@Service
public class SysUserSer {
    @Autowired
    SysUserMapper userMapper;

    public boolean register(SysUser user) {
        if (userMapper.getByUsername(user.getUsername()) != null)
            return false;
        user.setCreateTime(new Timestamp(CurrentUtil.now()));
        user.setAvatar("default");
        return userMapper.addUser(user) == 1;
    }
}

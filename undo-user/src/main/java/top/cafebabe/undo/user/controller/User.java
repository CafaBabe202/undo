package top.cafebabe.undo.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.cafebabe.undo.common.bean.ResponseMessage;
import top.cafebabe.undo.common.bean.SysUser;
import top.cafebabe.undo.common.util.MessageUtil;
import top.cafebabe.undo.user.form.RegisterForm;
import top.cafebabe.undo.user.service.SysUserSer;
import top.cafebabe.undo.user.util.Checker;
import top.cafebabe.undo.user.util.ClassConverter;

/**
 * @author cafababe
 */
@RestController
@RequestMapping("/user")
public class User {

    @Autowired
    SysUserSer sysUserSer;

    @PostMapping("/register")
    public ResponseMessage register(@RequestBody RegisterForm form) {
        if (!Checker.RegisterForm(form))
            return MessageUtil.fail("参数异常");
        return sysUserSer.register(ClassConverter.toSysUser(form)) ?
                MessageUtil.ok("注册成功！") : MessageUtil.fail("注册失败！");
    }
}
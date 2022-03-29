package top.cafebabe.undo.user.controller;

import cn.hutool.http.server.HttpServerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.cafebabe.undo.common.bean.ResponseMessage;
import top.cafebabe.undo.common.util.MessageUtil;
import top.cafebabe.undo.common.util.TokenUtil;
import top.cafebabe.undo.user.bean.AppConfig;
import top.cafebabe.undo.user.dao.TokenRedis;
import top.cafebabe.undo.user.form.GetDetailForm;
import top.cafebabe.undo.user.form.LoginForm;
import top.cafebabe.undo.user.form.RegisterForm;
import top.cafebabe.undo.user.form.SetForm;
import top.cafebabe.undo.user.service.SysUserSer;
import top.cafebabe.undo.user.util.Checker;
import top.cafebabe.undo.user.util.ClassConverter;

import java.util.Map;

/**
 * @author cafababe
 * 对用户开放的所有的 controller。
 * .token 结尾的代表需要 Token 认证。
 */
@RestController
@RequestMapping("/user")
public class UserCtl {

    @Autowired
    SysUserSer sysUserSer;

    @Autowired
    TokenRedis tokenRedis;

    // 用户注册
    @PostMapping("/register")
    public ResponseMessage register(@RequestBody RegisterForm form) {
        if (!Checker.RegisterForm(form)) return MessageUtil.fail("参数异常");
        return sysUserSer.register(ClassConverter.toSysUser(form)) ?
                MessageUtil.ok("注册成功！") : MessageUtil.fail("注册失败！");
    }

    // 用户登录
    @PostMapping("/login")
    public ResponseMessage login(@RequestBody LoginForm form, HttpServerRequest request) {
        if (!Checker.LoginForm(form) || !sysUserSer.checkPassword(form.getId(), form.getPassword()))
            return MessageUtil.fail("登录失败");
        String token = sysUserSer.saveToken(form.getId(), request.getClientIP());
        return token == null ? MessageUtil.error("服务器异常") : MessageUtil.ok(token);
    }

    //
    @PostMapping("/set.token")
    public ResponseMessage set(@RequestBody SetForm form, HttpServerRequest request) {
        String token = request.getHeader("token");
        int id = TokenUtil.getLoginTokenId(token, AppConfig.TOKEN_KEY);
        return MessageUtil.fail("????");
    }

    // 获取用户的公开资料
    @GetMapping("/getDetail/{id}")
    public ResponseMessage getGetDetail(@PathVariable int id) {
        Map<String, String> user = sysUserSer.getSysUserPublicDetail(id);
        return user == null ? MessageUtil.fail("None") : MessageUtil.ok(user);
    }

    // 获取多个用户的公开资料
    @PostMapping("/getDetail")
    public ResponseMessage postGetDetail(@RequestBody GetDetailForm form) {
        return MessageUtil.fail(sysUserSer.getSysUserPublicDetail(form.getIds()));
    }
}
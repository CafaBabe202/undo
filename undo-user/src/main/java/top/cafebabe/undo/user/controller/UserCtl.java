package top.cafebabe.undo.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.cafebabe.undo.common.bean.ResponseMessage;
import top.cafebabe.undo.common.bean.SysUser;
import top.cafebabe.undo.common.util.MessageUtil;
import top.cafebabe.undo.common.util.RequestUtil;
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

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author cafababe
 * 所有个人用户可用 controller，主要包含了对用户的创建和个人信息的修改功能（目前不包含头像部分）。
 * <b>.token</b> 结尾的代表需要 Token 认证。
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
                MessageUtil.ok("注册成功！") : MessageUtil.error("注册失败！");
    }

    // 用户登录
    @PostMapping("/login")
    public ResponseMessage login(@RequestBody LoginForm form, HttpServletRequest request) {
        if (!Checker.LoginForm(form))
            return MessageUtil.fail("登录失败");
        SysUser sysUser = sysUserSer.checkPassword(form.getId(), form.getPassword());
        if (sysUser == null)
            return MessageUtil.fail("用户名或密码错误");
        String token = sysUserSer.saveToken(sysUser, RequestUtil.getIp(request));
        return token == null ? MessageUtil.error("服务器异常") : MessageUtil.ok(token);
    }

    @GetMapping("/getMyDetail.token")
    public ResponseMessage getMyDetail(HttpServletRequest request) {
        int id = TokenUtil.getLoginTokenId(request.getHeader(AppConfig.TOKEN_NAME_IN_HEADER), AppConfig.TOKEN_KEY);
        Map<String, String> stringStringMap = sysUserSer.userDetail(id);
        return stringStringMap == null ? MessageUtil.fail("没有此用户") : MessageUtil.ok(stringStringMap);
    }

    // 设置有用户信息。
    @PostMapping("/set.token")
    public ResponseMessage set(@RequestBody SetForm form, HttpServletRequest request) {
        if (!Checker.setForm(form)) return MessageUtil.fail("参数异常");
        int id = TokenUtil.getLoginTokenId(request.getHeader(AppConfig.TOKEN_NAME_IN_HEADER), AppConfig.TOKEN_KEY);
        boolean res = false;
        switch (form.getField()) {
            case "username":
                res = sysUserSer.setUsername(id, form.getNewVal());
                break;
            case "password":
                res = sysUserSer.setPassword(id, form.getNewVal());
                break;
            case "email":
                res = sysUserSer.setEmail(id, form.getNewVal());
                break;
            case "sign":
                res = sysUserSer.setSign(id, form.getNewVal());
                break;
        }
        return res ? MessageUtil.ok("设置成功！") : MessageUtil.error("设置失败！");
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
        return form.getIds().size() > 20 ?
                MessageUtil.fail("数据过大") : MessageUtil.ok(sysUserSer.getSysUserPublicDetail(form.getIds()));
    }
}
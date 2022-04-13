package top.cafebabe.undo.user.controller;

import org.springframework.web.bind.annotation.*;
import top.cafebabe.undo.common.bean.ResponseMessage;
import top.cafebabe.undo.common.bean.SysUser;
import top.cafebabe.undo.common.util.MessageUtil;
import top.cafebabe.undo.common.util.RequestUtil;
import top.cafebabe.undo.user.bean.AppConfig;
import top.cafebabe.undo.user.bean.form.GetPublicDetailForm;
import top.cafebabe.undo.user.bean.form.LoginForm;
import top.cafebabe.undo.user.bean.form.RegisterForm;
import top.cafebabe.undo.user.bean.form.SetForm;
import top.cafebabe.undo.user.service.LoginUserSer;
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

    final SysUserSer sysUserSer;
    final LoginUserSer loginUserSer;

    public UserCtl(SysUserSer sysUserSer, LoginUserSer loginUserSer) {
        this.sysUserSer = sysUserSer;
        this.loginUserSer = loginUserSer;
    }

    // 用户注册
    @PostMapping("/register")
    public ResponseMessage register(@RequestBody RegisterForm form) {
        if (!Checker.RegisterForm(form)) return MessageUtil.fail("参数异常");

        if (sysUserSer.existUsername(form.getUsername())) return MessageUtil.fail("用户名已经存在");
        if (sysUserSer.existEmail(form.getEmail())) return MessageUtil.fail("邮箱已被占用");

        return sysUserSer.register(ClassConverter.toSysUser(form)) ?
                MessageUtil.ok("注册成功！") : MessageUtil.error("注册失败！");
    }

    // 用户登录
    @PostMapping("/login")
    public ResponseMessage login(@RequestBody LoginForm form, HttpServletRequest request) {
        if (!Checker.LoginForm(form))
            return MessageUtil.fail("参数异常");

        SysUser sysUser = sysUserSer.checkPassword(form.getEmail(), form.getPassword());
        if (sysUser == null)
            return MessageUtil.fail("邮箱或密码错误");

        String token = loginUserSer.saveToken(sysUser, RequestUtil.getIp(request));
        return token == null ? MessageUtil.error("服务器异常") : MessageUtil.ok(token);
    }

    // 设置有用户信息。
    @PostMapping("/set.token")
    public ResponseMessage set(@RequestBody SetForm form, HttpServletRequest request) {
        if (!Checker.setForm(form)) return MessageUtil.fail("参数异常");
        String token = request.getHeader(AppConfig.TOKEN_NAME_IN_HEADER);
        boolean res = loginUserSer.updateRedisUser(token, form.getField(), form.getNewVal());
        switch (form.getField()) {
            case "username":
                res &= sysUserSer.setUsername(token, form.getNewVal());
                break;
            case "email":
                res &= sysUserSer.setEmail(token, form.getNewVal());
                break;
            case "sign":
                res &= sysUserSer.setSign(token, form.getNewVal());
                break;
        }
        return res ? MessageUtil.ok("设置成功！") : MessageUtil.error("设置失败！");
    }

    // 获取自己的用户信息
    @GetMapping("/getMyDetail.token")
    public ResponseMessage getMyDetail(HttpServletRequest request) {
        Map<String, String> user = loginUserSer.userGetDetail(request.getHeader(AppConfig.TOKEN_NAME_IN_HEADER));
        return user == null ? MessageUtil.tokenInvalid() : MessageUtil.ok(user);
    }

    // 获取用户的公开资料
    @GetMapping("/getDetail/{id}")
    public ResponseMessage getGetDetail(@PathVariable int id) {
        Map<String, String> user = sysUserSer.getSysUserPublicDetail(id);
        return user == null ? MessageUtil.fail("None") : MessageUtil.ok(user);
    }

    // 获取多个用户的公开资料
    @PostMapping("/getDetail")
    public ResponseMessage postGetDetail(@RequestBody GetPublicDetailForm form) {
        if (!Checker.GetPublicDetailForm(form))
            return MessageUtil.fail("数据异常");

        return form.getIds().size() > 20 ?
                MessageUtil.fail("数据过大") : MessageUtil.ok(sysUserSer.getSysUserPublicDetail(form.getIds()));
    }
}
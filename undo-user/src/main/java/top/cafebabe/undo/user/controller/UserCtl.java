package top.cafebabe.undo.user.controller;

import org.springframework.web.bind.annotation.*;
import top.cafebabe.undo.common.bean.LoginUser;
import top.cafebabe.undo.common.bean.ResponseMessage;
import top.cafebabe.undo.common.bean.SysUser;
import top.cafebabe.undo.common.util.MessageUtil;
import top.cafebabe.undo.common.util.NowUtil;
import top.cafebabe.undo.common.util.RequestUtil;
import top.cafebabe.undo.user.bean.AppConfig;
import top.cafebabe.undo.user.bean.form.*;
import top.cafebabe.undo.user.service.LoginUserSer;
import top.cafebabe.undo.user.service.SysUserSer;
import top.cafebabe.undo.user.util.Checker;
import top.cafebabe.undo.user.util.ClassConverter;
import top.cafebabe.undo.user.util.EmailSender;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

    @PostMapping("/sendEmail")
    public ResponseMessage demo(@RequestBody SendEmailForm form, HttpSession session) {
        long last = lastSendTime(session);
        if (NowUtil.now() - last < AppConfig.SEND_TIME)
            return MessageUtil.fail("技能冷却中");
        if (!Checker.check(form))
            return MessageUtil.fail("数据异常");

        String code = EmailSender.sender(form.getEmail());
        session.setAttribute(AppConfig.REGISTER_CODE_KEY_IN_SESSION, code);
        session.setAttribute(AppConfig.LAST_SEND_TIME, NowUtil.now());
        return MessageUtil.ok("验证码已发送");
    }

    // 用户注册
    @PostMapping("/register")
    public ResponseMessage register(@RequestBody RegisterForm form, HttpSession session) {
        if (!Checker.check(form))
            return MessageUtil.fail("参数异常");

        long last = lastSendTime(session);
        if (!form.getCode().equals(session.getAttribute(AppConfig.REGISTER_CODE_KEY_IN_SESSION))
                || NowUtil.now() - last > AppConfig.SEND_TIME)
            return MessageUtil.fail("验证码不正确");

        if (sysUserSer.existUsername(form.getUsername())) return MessageUtil.fail("用户名已经存在");
        if (sysUserSer.existEmail(form.getEmail())) return MessageUtil.fail("邮箱已被占用");

        return sysUserSer.register(ClassConverter.toSysUser(form)) ?
                MessageUtil.ok("注册成功！") : MessageUtil.error("注册失败！");
    }

    // 用户登录
    @PostMapping("/login")
    public ResponseMessage login(@RequestBody LoginForm form, HttpServletRequest request) {
        if (!Checker.check(form))
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
        if (!Checker.check(form))
            return MessageUtil.fail("参数异常");

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

    // 修改密码
    @PostMapping("/changePass.token")
    public ResponseMessage changePass(@RequestBody ChangePassForm form, HttpServletRequest request) {
        if (!Checker.check(form))
            return MessageUtil.fail("数据异常");

        LoginUser user = loginUserSer.getUseByToken(request.getHeader(AppConfig.TOKEN_NAME_IN_HEADER));
        if (user == null)
            return MessageUtil.tokenInvalid();

        if (sysUserSer.checkPassword(user.getEmail(), form.getOldPass()) == null)
            return MessageUtil.fail("原密码错误");

        return sysUserSer.setPassword(user.getId(), form.getNewPass())
                ? MessageUtil.ok("设置成功") : MessageUtil.error("设置失败");
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
        if (!Checker.check(form))
            return MessageUtil.fail("数据异常");

        return form.getIds().size() > 20 ?
                MessageUtil.fail("数据过大") : MessageUtil.ok(sysUserSer.getSysUserPublicDetail(form.getIds()));
    }

    private long lastSendTime(HttpSession session) {
        try {
            return Long.parseLong(session.getAttribute(AppConfig.LAST_SEND_TIME).toString());
        } catch (NumberFormatException | NullPointerException e) {
            return 0L;
        }
    }
}
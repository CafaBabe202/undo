package top.cafebabe.undo.user.controller;

import org.springframework.web.bind.annotation.*;
import top.cafebabe.undo.common.bean.LoginUser;
import top.cafebabe.undo.common.bean.ResponseMessage;
import top.cafebabe.undo.common.util.MessageUtil;
import top.cafebabe.undo.user.service.LoginUserSer;

/**
 * @author cafababe
 * AppCtl 是向其他 App 传递用户信息的控制器。以后实现其他平台使用 undo 帐号登录时，同样要通过本模块进行 Token 的签发，其他 App 将通过这里验证用户的 Token 并获取用户信息。
 */
@RestController
@RequestMapping("/innerApp")
public class InnerAppCtl {

    final LoginUserSer loginUserSer;

    public InnerAppCtl(LoginUserSer loginUserSer) {
        this.loginUserSer = loginUserSer;
    }

    @GetMapping("/getUserDetail/{token}")
    public ResponseMessage getUserDetail(@PathVariable String token) {
        LoginUser loginUser;
        return (loginUser = loginUserSer.getUseByToken(token)) == null ?
                MessageUtil.tokenInvalid() : MessageUtil.ok(loginUser);
    }
}
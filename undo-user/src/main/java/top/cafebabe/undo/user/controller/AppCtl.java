package top.cafebabe.undo.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.cafebabe.undo.common.bean.ResponseMessage;
import top.cafebabe.undo.common.util.MessageUtil;
import top.cafebabe.undo.user.dao.TokenRedis;
import top.cafebabe.undo.user.form.GetUserDetailForm;
import top.cafebabe.undo.user.service.LoginUserSer;

/**
 * @author cafababe
 * AppCtl 是向其他 App 传递用户信息的控制器。以后实现其他平台使用 undo 帐号登录时，同样要通过本模块进行 Token 的签发，其他 App 将通过这里验证用户的 Token 并获取用户信息。
 */
@RestController
@RequestMapping("/app")
public class AppCtl {

    final LoginUserSer loginUserSer;

    public AppCtl(LoginUserSer loginUserSer) {
        this.loginUserSer = loginUserSer;
    }

    @GetMapping("/getUserDetail.app/")
    public ResponseMessage getUserDetail(@RequestParam GetUserDetailForm form) {
        return MessageUtil.ok("");
    }
}
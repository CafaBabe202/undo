package top.cafebabe.monitorshow.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.cafebabe.monitorshow.bean.LoginForm;
import top.cafebabe.monitorshow.bean.ResponseMessage;
import top.cafebabe.monitorshow.util.MessageUtil;

/**
 * @author cafababe
 */
@RestController
@RequestMapping("/")
public class AdminCtl {
    @PostMapping("/login")
    public ResponseMessage login(@RequestBody LoginForm form) {
        if ("ZH".equals(form.getId()) && "Admin@123".equals(form.getPassword())) {
            return MessageUtil.ok("登录成功");
        } else {
            return MessageUtil.fail("登录失败");
        }
    }
}

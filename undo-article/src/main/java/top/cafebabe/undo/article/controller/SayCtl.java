package top.cafebabe.undo.article.controller;

import org.springframework.web.bind.annotation.*;
import top.cafebabe.undo.article.bean.form.SayForm;
import top.cafebabe.undo.article.service.SayService;
import top.cafebabe.undo.article.util.SessionUtil;
import top.cafebabe.undo.common.bean.LoginUser;
import top.cafebabe.undo.common.bean.ResponseMessage;
import top.cafebabe.undo.common.util.MessageUtil;

import javax.servlet.http.HttpSession;

/**
 * @author cafababe
 */
@RestController
@RequestMapping("/say")
public class SayCtl {

    final SayService service;

    public SayCtl(SayService service) {
        this.service = service;
    }

    @PostMapping("/say.token")
    public ResponseMessage say(@RequestBody SayForm form, HttpSession session) {
        LoginUser loginUser = SessionUtil.getLoginUser(session);
        if (loginUser == null)
            return MessageUtil.error("变量错误");

        return service.say(loginUser.getId(), form.getArticleId(), form.getContent()) ?
                MessageUtil.ok("评论成功！") : MessageUtil.fail("评论失败！");
    }

    @GetMapping("/getAllSay/{id}")
    public ResponseMessage getAllSay(@PathVariable String id) {
        try {
            return MessageUtil.ok(service.getAll(Integer.parseInt(id)));
        } catch (Exception e) {
            e.printStackTrace();
            return MessageUtil.fail(null);
        }
    }
}

package top.cafebabe.monitorshow.controller;

import org.springframework.web.bind.annotation.*;
import top.cafebabe.monitorshow.bean.AddServerForm;
import top.cafebabe.monitorshow.bean.ResponseMessage;
import top.cafebabe.monitorshow.bean.ServerStatus;
import top.cafebabe.monitorshow.util.MessageUtil;

/**
 * @author cafababe
 */
@RestController()
@RequestMapping("/status")
public class StatusCtl {

    @PostMapping("/add")
    public ResponseMessage add(@RequestBody AddServerForm form) {

        if (ServerStatus.exist(form.getServerName()))
            return MessageUtil.fail("服务器已经存在");
        if (!form.check())
            return MessageUtil.fail("数据异常");
        ServerStatus.add(
                form.getServerName(),
                form.getIp(),
                form.getPort(),
                form.getPass(),
                form.getTime(),
                form.getEmail()
        );
        return MessageUtil.ok(null);
    }

    @GetMapping("/stop/{serverName}")
    public ResponseMessage stop(@PathVariable String serverName) {
        if (!ServerStatus.exist(serverName))
            return MessageUtil.fail("数据异常");
        ServerStatus.stop(serverName);
        return MessageUtil.ok(null);
    }

    @GetMapping("/get/{serverName}")
    public ResponseMessage get(@PathVariable String serverName) {
        if (!ServerStatus.exist(serverName))
            return MessageUtil.fail("数据异常");
        return MessageUtil.ok(ServerStatus.get(serverName));
    }

    @GetMapping("/getAllServer")
    public ResponseMessage getAllServer() {
        return MessageUtil.ok(ServerStatus.getAllName());
    }
}
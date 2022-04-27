package top.cafebabe.monitor.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.cafebabe.monitor.StatusGetter.OshiStatusGetter;
import top.cafebabe.monitor.bean.Pass;
import top.cafebabe.monitor.bean.Status;

/**
 * @author cafababe
 */
@RestController()
@RequestMapping("/")
public class Getter {

    @GetMapping("/getter/{pass}")
    public Status getter(@PathVariable String pass) {
        if (Pass.PASS.equals(pass)) {
            return OshiStatusGetter.getStatus();
        } else {
            return new Status();
        }
    }
}

package top.cafebabe.undo.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.cafebabe.undo.common.bean.ResponseMessage;
import top.cafebabe.undo.common.util.MessageUtil;
import top.cafebabe.undo.user.bean.AppConfig;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author cafababe
 */
@RestController
@RequestMapping("/avatar")
public class AvatarCtl {

    @GetMapping("/{md5}")
    public ResponseMessage getAvatar(@PathVariable String md5, HttpServletResponse httpServletResponse) {
        try {
            InputStream is = new FileInputStream(AppConfig.LOCAL_AVATAR_DIR + md5);
            ServletOutputStream os = httpServletResponse.getOutputStream();
            os.write(is.readAllBytes());
            os.close();
            is.close();
            return MessageUtil.ok("OK");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MessageUtil.error("");
    }
}

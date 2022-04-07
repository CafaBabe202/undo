package top.cafebabe.undo.user.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.cafebabe.undo.common.bean.ResponseMessage;
import top.cafebabe.undo.common.util.MessageUtil;
import top.cafebabe.undo.user.bean.AppConfig;
import top.cafebabe.undo.user.service.LoginUserSer;
import top.cafebabe.undo.user.service.SysUserSer;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author cafababe
 */
@RestController
@RequestMapping("/avatar")
public class AvatarCtl {

    final SysUserSer sysUserSer;
    final LoginUserSer loginUserSer;

    public AvatarCtl(SysUserSer sysUserSer, LoginUserSer loginUserSer) {
        this.sysUserSer = sysUserSer;
        this.loginUserSer = loginUserSer;
    }

    //上传头像
    @PostMapping("/uploadAvatar.token")
    public ResponseMessage setAvatar(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request) {
        String token = request.getHeader(AppConfig.TOKEN_NAME_IN_HEADER), url;
        try {
            InputStream inputStream = multipartFile.getInputStream();
            byte[] bytes = inputStream.readAllBytes();
            if (bytes.length > AppConfig.AVATAR_SIZE)
                return MessageUtil.fail("文件过大");

            return (url = sysUserSer.setAvatar(token, bytes)) != null && loginUserSer.updateRedisUser(token, "avatar", url) ?
                    MessageUtil.ok("设置成功") : MessageUtil.fail("设置失败！");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MessageUtil.error("未知错误");
    }

    @GetMapping("/get/{md5}")
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

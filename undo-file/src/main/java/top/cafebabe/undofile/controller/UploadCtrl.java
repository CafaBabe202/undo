package top.cafebabe.undofile.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.cafebabe.undo.common.bean.ResponseMessage;
import top.cafebabe.undo.common.util.MessageUtil;
import top.cafebabe.undofile.bean.form.Md5CheckForm;

/**
 * @author cafababe
 */
@RestController
@RequestMapping("/upload")
public class UploadCtrl {

    @PostMapping("/upload")
    public ResponseMessage upload(MultipartFile file) {
        return MessageUtil.ok("");
    }

    @PostMapping("/md5Check")
    public ResponseMessage md5CheckAndAdd(@RequestBody Md5CheckForm form) {
        return MessageUtil.ok(form);
    }
}

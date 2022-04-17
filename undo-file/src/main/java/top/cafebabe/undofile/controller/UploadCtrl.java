package top.cafebabe.undofile.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.cafebabe.fileManager.manger.TempFileManager;
import top.cafebabe.undo.common.bean.ResponseMessage;
import top.cafebabe.undo.common.util.MessageUtil;
import top.cafebabe.undofile.bean.FileUploadStatus;
import top.cafebabe.undofile.bean.form.FileUploadInitForm;
import top.cafebabe.undofile.util.RandomUtils;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author cafababe
 */
@RestController
@RequestMapping("/upload")
public class UploadCtrl {

    final TempFileManager tempFileManager;

    public UploadCtrl(TempFileManager tempFileManager) {
        this.tempFileManager = tempFileManager;
    }

    @PostMapping("/initUpload")
    public ResponseMessage initUpload(@RequestBody FileUploadInitForm form, HttpSession session) {
        if (tempFileManager.exist(form.getMd5())) {
            return MessageUtil.done();
        } else {
            FileUploadStatus status = new FileUploadStatus(form.getFileName(), form.getMd5(), tempFileManager.createTempFile(), form.getAllSize(), 0);
            String uuid = RandomUtils.getUUID();
            session.setAttribute(uuid, status);
            Map<String, Object> res = nexUpload(status);
            res.put("uuid", uuid);
            return MessageUtil.goOn(res);
        }
    }

    @PostMapping("/upload")
    public ResponseMessage upload(@RequestParam("file") MultipartFile file, String uuid, HttpSession session) throws Exception {
        FileUploadStatus status = (FileUploadStatus) session.getAttribute(uuid);
        byte[] data = file.getInputStream().readAllBytes();
        if (tempFileManager.temp(status.getTempFileId(), data)) {
            status.setNowSize(status.getNowSize() + data.length);
        }
        if (status.getAllSize() == status.getNowSize()) {
            return tempFileManager.store(status.getTempFileId(), status.getMd5()) ? MessageUtil.done() : MessageUtil.fail("上传失败");
        }
        return MessageUtil.goOn(nexUpload(status));
    }

    public Map<String, Object> nexUpload(FileUploadStatus status) {
        Map<String, Object> res = new HashMap<>();
        res.put("from", status.getNowSize());
        long to = status.getNowSize() + 1024 * 1024;
        res.put("to", Math.min(to, status.getAllSize()));
        return res;
    }
}


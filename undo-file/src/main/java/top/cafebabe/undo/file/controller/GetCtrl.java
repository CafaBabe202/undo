package top.cafebabe.undo.file.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import top.cafebabe.fileManager.manger.TempFileManager;
import top.cafebabe.fileManager.manger.integrator.Integrator;
import top.cafebabe.undo.common.bean.ResponseMessage;
import top.cafebabe.undo.common.util.MessageUtil;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * @author cafababe
 */
@Controller
public class GetCtrl {

    final TempFileManager tempFileManager;

    public GetCtrl(TempFileManager tempFileManager) {
        this.tempFileManager = tempFileManager;
    }

    @GetMapping("/get.tok/{id}")
    public ResponseMessage get(@PathVariable String id, HttpServletResponse response) throws Exception {
        Integrator integrator = tempFileManager.get(id);
        ServletOutputStream outputStream = response.getOutputStream();
        while (integrator.hasNext()) {
            outputStream.write(integrator.next());
        }
        outputStream.close();
        return MessageUtil.ok("");
    }
}

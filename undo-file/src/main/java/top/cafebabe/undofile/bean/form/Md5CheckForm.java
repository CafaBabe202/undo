package top.cafebabe.undofile.bean.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author cafababe
 */
@Data
public class Md5CheckForm {
    private String fileName;
    private String md5;
}

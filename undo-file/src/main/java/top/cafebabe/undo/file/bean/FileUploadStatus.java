package top.cafebabe.undo.file.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author cafababe
 */
@Data
@AllArgsConstructor
public class FileUploadStatus {
    private String fileName;
    private String md5;
    private String tempFileId;
    private long allSize;
    private long nowSize;
}

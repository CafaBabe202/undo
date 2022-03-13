package top.cafebabe.fileManager.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author cafababe
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlockFile {
    private String md5;
    private List<String> content;
    private long count;
    private long size;
}

package top.cafebabe.fileManager.bean;

import lombok.*;
import top.cafebabe.fileManager.utils.Md5Util;

/**
 * @author cafababe
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Block {
    private String md5;
    private long count;
    private int size;
    private byte[] content;

    public void setContent(byte[] content){
        this.content = content;
        this.md5 = Md5Util.md5(content);
        this.size = this.content.length;
    }

}

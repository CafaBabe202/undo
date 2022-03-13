package top.cafebabe.fileManager.bean;

import lombok.*;

/**
 * @author cafababe
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Block {
    private String md5;
    private long count;
    private int size;
    private byte[] content;

    @Override
    public String toString() {
        return "Block{" +
                "md5='" + md5 + '\'' +
                ", count=" + count +
                ", size=" + size +
                '}';
    }
}

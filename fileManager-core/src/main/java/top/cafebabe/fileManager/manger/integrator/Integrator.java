package top.cafebabe.fileManager.manger.integrator;

import java.util.Iterator;

/**
 * 整合器将一个 BlockFile 类型的文件整合成一个文件。
 * 返回读取的字节数组，但是并不保证每次读取的数据长度相同，实际上将每次读取时将返回其在数据库总的一个块，这与当初存文件时使用的分割器和其配置有关。如果返回 null ，表示没有任何内容了。
 *
 * @author cafababe
 */
public interface Integrator extends Iterator<byte[]> {
    void skip(int length);
}

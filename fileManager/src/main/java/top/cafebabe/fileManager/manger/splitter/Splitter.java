package top.cafebabe.fileManager.manger.splitter;

import java.io.Closeable;
import java.util.Iterator;

/**
 * Splitter 用来分割一个表示普通文件的流。它将文件分割成多个块，并通过字节数组的方式返回每一个块的内容。
 * 通过不同的 Splitter 实现，可以实现许多不同的功能，比如动态分块。
 * @author cafababe
 */
public interface Splitter extends Iterator<byte[]>, Closeable {
    /**
     * 获取已经读取的所有字节数。
     * @return
     */
    long getSize();
}

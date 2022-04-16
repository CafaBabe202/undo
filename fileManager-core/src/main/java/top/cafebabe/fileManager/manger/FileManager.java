package top.cafebabe.fileManager.manger;

import top.cafebabe.fileManager.exception.NotFileException;
import top.cafebabe.fileManager.manger.integrator.Integrator;

import java.io.InputStream;

/**
 * FileManager 是分块文件管理的核心接口之一，FileManager 将组织 memory、integrator 和 splitter 进行工作。
 * FileManager 负责对文件进行分块、存储和记录文件分块列表，它将对文件的完整性进行约束，因为 Memory 对每个块的操作都听从它的指挥。
 * FileManager 并不对块的完整性负责，如果在存储过成中块被损坏或丢失请追究 memory 的责任。
 * FileManager 通过 splitter 将文件分割成小块储存在 memory 中，并记录文件的分块列表。
 * 获取文件时，FileManager 将返回一个 integrator，它将通过类似迭代器的方式将文件从内存中读取出来返回给调用者。
 * 受限与存储方式，FileManager 并不支持对文件的部分修改。
 *
 * @author cafababe
 */
public interface FileManager {
    /**
     * 添加一个文件。
     *
     * @param inputStream 指向文件的流。
     * @return 是否添加成功。
     */
    boolean add(InputStream inputStream);

    /**
     * 通过 md5 添加一个文件，前提是你要确保该 md5 已经存在，不然将会抛出u异常。
     *
     * @param md5 文件 md5。
     * @return 是否添加成功。
     * @throws NotFileException 如果该文件没有事先存在将抛出次异常。
     */
    boolean add(String md5) throws NotFileException;

    /**
     * 删除一个文件。
     *
     * @param md5 文件的 md5。
     * @return 是否删除成功。
     */
    boolean remove(String md5);

    /**
     * 获取一个文件。
     *
     * @param md5 文件的 md5。
     * @return 一个包含文件流的 integrator，通过迭代器的方式以此获取文件的没一个部分。
     */
    Integrator get(String md5);

    boolean exist(String md5);

    /**
     * 清理掉计数为 0 的文件或块，这个操作主要由 memory 完成，并不保证所有的 memory 都支持。
     */
    void gc();
}

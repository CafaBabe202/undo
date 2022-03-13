package top.cafebabe.fileManager.manger.memory;

import top.cafebabe.fileManager.bean.Block;
import top.cafebabe.fileManager.bean.BlockFile;
import top.cafebabe.fileManager.exception.ExistException;
import top.cafebabe.fileManager.exception.NotBlockException;
import top.cafebabe.fileManager.exception.NotFileException;

/**
 * Memory 是分块存储的存储器，支持对每一个块文件的访问和文件的访问。
 * Memory 只对块的完整性负责，并不对文件的完整性负责，FileManager 有权对每一个块进行添加和删除操作，即使破坏文件完整性也是允许的。
 * 在进行删除块的操作时，Memory 并不进行文件依赖的检查，也就说由于 FileManager 的逻辑问题，可能导致文件的完整性被破坏。
 *
 * @author cafababe
 */
public interface Memory {
    /**
     * 添加一个块文件，但是并不承诺是真的添加一个块文件，也可能是已存在的块计数加一。
     * 标记不同的块的唯一方法是 Block 的 md5.
     *
     * @param block 块文件对象
     * @return 是否添加成功。
     */
    boolean addBlock(Block block) throws ExistException;

    /**
     * 删除一个块，同添加一样，这可能只是计数减一，如果块不存在，直接返回 true。
     *
     * @param md5 删除块的 md5。
     * @return 返回是否删除成功，即使是计数减一，也会返回 true。
     */
    boolean deleteBlock(String md5);

    /**
     * 这只文件的引用个数，返回 boolean 类型不表示是否设置成功。
     *
     * @param md5   md5 标识。
     * @param count 设置的引用个数。
     * @return 成功返回 true，否则返回 false。
     * @throws NotBlockException 如果没有找到这样的块将抛出异常。
     */
    boolean setBlockCount(String md5, long count) throws NotBlockException;

    /**
     * 获取块的应用个数。
     *
     * @param md5 md5 标识
     * @return 该块目前的引用个数。
     * @throws NotBlockException 如果没有找到这样的块将抛出异常。
     */
    long getBlockCount(String md5) throws NotBlockException;

    /**
     * 通过 md5 获取一个块。
     *
     * @param md5 md5。
     * @return 返回块对象，如果不存在这样的块将返回 null。
     */
    Block getBlock(String md5);

    /**
     * 判断一个块是否存在。
     * 注意：这里的存是检查文件是否真正存在，即使它的引用数为 0 如果存在同样会返回 true。
     *
     * @param md5 md5。
     * @return 如果存在返回 true，否则返回 false。
     */
    boolean existBlock(String md5);

    /**
     * 添加一个文件，注意，这里的文件的内容是块 ID，而并不是文件内容本身。
     * 与块一样，添加一个文件时，同样可能只是计数加一。
     *
     * @param blockFile 分块文件对象。
     * @return 返回是否保存成功。
     */
    boolean addFile(BlockFile blockFile) throws ExistException;

    /**
     * 删除一个文件，同理，这可能只是计数减一，如果文件本来就不存在，在接返回 true。
     *
     * @param md5 要删除的 md5。
     * @return 返回是否删除成功，即使是计数减一，同样会返回 true。
     */
    boolean deleteFile(String md5);

    /**
     * @param md5   文件的 md5。
     * @param count 引用数量。
     * @return true 表示成功，false 表示失败。
     * @throws NotFileException 如果没有找到这样的文件将抛出异常。
     */
    boolean setFileCount(String md5, long count) throws NotFileException;

    /**
     * @param md5 文件的 md5。
     * @return 文件的引用数量
     * @throws NotFileException 如果没有找到这样的文件，将抛出此异常。
     */
    long getFileCount(String md5) throws NotFileException;

    /**
     * 通过 md5 获取一个文件。
     * 他返回的文件的内容只是块文件的 md5 列表，并不进行包含文件内容的实体。
     *
     * @param md5 md5。
     * @return 块文件，如果不存在，将返回 null。
     */
    BlockFile getFile(String md5);

    /**
     * 检查文件是否存在，即使 文件的计数为 0，依然会返回 true。
     *
     * @param md5 文件的 md5。
     * @return 存在返回 true，否则返回 false。
     */
    boolean existFile(String md5);

    /**
     * 删除所有引用数为 0 的 块。
     *
     * @return 删除的个数。
     */
    long blockGc();

    /**
     * 删除所有引用数为 0 的文件。
     *
     * @return 删除的个数。
     */
    long fileGc();
}

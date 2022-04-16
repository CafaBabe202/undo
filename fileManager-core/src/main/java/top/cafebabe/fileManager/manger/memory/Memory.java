package top.cafebabe.fileManager.manger.memory;

import top.cafebabe.fileManager.bean.Block;
import top.cafebabe.fileManager.bean.BlockFile;
import top.cafebabe.fileManager.exception.ExistException;
import top.cafebabe.fileManager.exception.NotFileException;

/**
 * Memory 是分块存储的存储器，支持对每一个块和文件的访问。
 * Memory 只对块的完整性负责，并不对文件的完整性负责，fileManager 有权对每一个块进行添加和删除操作，即使破坏文件完整性也是允许的，由于 fileManger 的错误操作将很容易造成文件的损坏。
 * 在进行删除的操作时，Memory 并不进行文件依赖的检查，不论该文件或者块有没有被其他文件或块引用，都将删除成功。
 * 请不要手动管理 memory，因为这很容易造成文件的永久性破坏。正确的操作方式是将 memory 对象直接交给 fileManager 而不再其他地方保存对 memory 的引用。
 *
 * @author cafababe
 */
public interface Memory {
    /**
     * 添加一个块文件，但这可能并不是真的添加一个块文件，也可能是已存在的块计数加一。
     * 标记不同的块的唯一方法是 Block 的 md5，对于相同的块，将只保存一份来节省空间。
     *
     * @param block 块对象。
     * @return 是否添加成功。
     */
    boolean addBlock(Block block);

    /**
     * 删除一个块，和添加一样，这可能只是计数减一，如果块不存在，直接返回 true。
     *
     * @param md5 删除块的 md5。
     * @return 返回是否删除成功，即使是计数减一，也会返回 true。
     */
    boolean removeBlock(String md5);

    /**
     * 通过 md5 获取一个块。
     *
     * @param md5 md5。
     * @return 返回块对象，如果不存在这样的块将返回 null。
     */
    Block getBlock(String md5);

    /**
     * 添加一个文件。
     * 添加文件是一个并不简单的事情，对于同一个文件，根据不同的分割方式会产生不同的块，其中的记录信息当然也会不同。
     * 对于不同的 memory，可能作出不同的处理方式。
     * 在添加一个文件时首先检查文件是否存在将是一个好习惯。
     *
     * @param blockFile 分块文件。
     * @return 是否添加成功。
     * @throws ExistException 对于不知道如果应该上述问题时，可以抛出该异常把它交给 fileManager 进行处理。
     */
    boolean addFile(BlockFile blockFile) throws ExistException;

    /**
     * 添加一个文件，当你检查一个文件已经存在，可以直接使用该方法对该文件的计数 +1。
     * <b>注意：<b/> 所有的 memory 实现时都必须承诺在调用此方法时对文件包涵的每个块进行 +1 操作。
     *
     * @param md5 文件的 md5。
     * @return 是否添加成功。
     * @throws NotFileException 如果文件不存在，将会抛出这个异常。
     */
    boolean incrFile(String md5) throws NotFileException;

    /**
     * 删除一个文件。
     *
     * @param md5 要删除的 md5。
     * @return 返回是否删除成功。
     */
    boolean removeFile(String md5);

    /**
     * 通过 md5 获取一个文件。
     * 他返回的文件的内容只是块文件的 md5 列表，并不进行包含文件内容的实体。
     *
     * @param md5 md5。
     * @return 如果不存在，将返回 null。
     */
    BlockFile getFile(String md5);

    /**
     * 检查文件是否存在。
     * 对于某些不删除 count 为 0 的 memory，将自行决定当 count 为 0 是的返回结果。
     *
     * @param md5 文件的 md5。
     * @return 存在返回 true，否则返回 false。
     */
    boolean existFile(String md5);

    /**
     * 对于那些可以软删除块或文件的 memory 的存储器，通过该方法会删除掉没有任何引用的块或文件。
     */
    void gc();
}

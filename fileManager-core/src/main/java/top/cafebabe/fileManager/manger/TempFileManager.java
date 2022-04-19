package top.cafebabe.fileManager.manger;

import top.cafebabe.fileManager.bean.Block;
import top.cafebabe.fileManager.bean.BlockFile;
import top.cafebabe.fileManager.bean.TempBlockFile;
import top.cafebabe.fileManager.manger.integrator.IntegratorFactory;
import top.cafebabe.fileManager.manger.memory.TempMongoMemory;
import top.cafebabe.fileManager.manger.splitter.SplitterFactory;
import top.cafebabe.fileManager.utils.Md5Util;
import top.cafebabe.fileManager.utils.Util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 为了方便实现文件的切片上传。
 *
 * @author cafababe
 */
public class TempFileManager extends SimpleFileManager {

    /**
     * md5 计算工具的 Map。
     * Key 是生成的 ID，Value 是工具。
     * 之所以要这里要计算文件的 md5 而不是直接用户提供是为了方式文件上传过程中的错误，也可以剩下最后合并文件时的统一读取操作。
     * 如果不校验 md5，因为同一 md5 的文件只上传一份，如果一分损坏可能造成以后所有该 md5 文件全部损坏。
     */
    private final Map<String, Md5Util> md5Utils;

    TempMongoMemory tempMongoMemory;

    /**
     * 由于存放临时文件需要一个新的集合，或以需要 memory 对象的支持。这里没有创建新的接口，以后可能会创建创建。看以后的功能需求。
     */
    public TempFileManager(TempMongoMemory memory, SplitterFactory splitterFactory, IntegratorFactory integratorFactory) {
        super(memory, splitterFactory, integratorFactory);
        this.tempMongoMemory = memory;
        this.md5Utils = new ConcurrentHashMap<>();
    }

    /**
     * 创建一个临时的文件并返回该临时文件的 ID，以后每次进行 temp 操作时，都必须提供该 ID，之所以不同 MD5 做 ID 是方式两个线程同时上传同一个文件。
     *
     * @return 返回临时文件的 ID。
     */
    public String createTempFile() {
        TempBlockFile file = new TempBlockFile();
        this.md5Utils.put(file.getId().toString(), new Md5Util());
        this.tempMongoMemory.addTempFile(file);
        return file.getId().toString();
    }


    /**
     * 添加一个临时块
     *
     * @param id   临时文件的 ID
     * @param data 数据
     * @return 是否添加成功
     */
    public boolean temp(String id, byte[] data) {
        Md5Util md5Util = this.md5Utils.get(id);
        if (md5Util == null)
            return false;
        Block block = new Block();
        block.setContent(data);
        TempBlockFile tempFile;
        if (!this.tempMongoMemory.addBlock(block))
            return false;
        if ((tempFile = this.tempMongoMemory.getTempFile(id)) == null) {
            this.tempMongoMemory.removeBlock(block.getMd5());
            return false;
        }
        tempFile.setSize(tempFile.getSize() + data.length);
        tempFile.getContent().add(block.getMd5());
        tempFile.setLastUpdateTime(Util.now());
        if (!this.tempMongoMemory.updateTempFile(tempFile)) {
            this.tempMongoMemory.removeBlock(block.getMd5());
            return false;
        }
        md5Util.update(data, 0, data.length);
        return true;
    }

    /**
     * 将一个临时文件存储。
     * @param id 文件 ID。
     * @param md5 预期的 MD5 值。
     * @return 返回是否存储成功。
     */
    public boolean store(String id, String md5) {
        TempBlockFile tempFile = this.tempMongoMemory.getTempFile(id);
        String expectMd5 = this.md5Utils.get(tempFile.getId().toString()).design();
        return expectMd5.equals(md5) ? this.store(tempFile, md5) : this.remove(tempFile);
    }

    private boolean store(TempBlockFile file, String md5) {
        try {
            if (this.tempMongoMemory.existFile(md5) && this.tempMongoMemory.incrFile(md5)) {
                return this.remove(file);
            } else if (!this.tempMongoMemory.existFile(file.getId().toString())) {
                BlockFile blockFile = new BlockFile();
                blockFile.setMd5(md5);
                blockFile.setSize(file.getSize());
                blockFile.setContent(file.getContent());
                this.tempMongoMemory.addFile(blockFile);
                this.tempMongoMemory.removeTempFile(file.getId().toString());
                this.md5Utils.remove(file.getId().toString());
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean remove(TempBlockFile file) {
        for (String md5 : file.getContent()) {
            this.tempMongoMemory.removeBlock(md5);
        }
        return this.tempMongoMemory.removeTempFile(file.getId().toString());
    }
}

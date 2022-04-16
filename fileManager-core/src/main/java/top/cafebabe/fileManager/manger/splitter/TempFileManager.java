package top.cafebabe.fileManager.manger.splitter;

import top.cafebabe.fileManager.bean.Block;
import top.cafebabe.fileManager.bean.BlockFile;
import top.cafebabe.fileManager.bean.TempBlockFile;
import top.cafebabe.fileManager.manger.SimpleFileManager;
import top.cafebabe.fileManager.manger.integrator.IntegratorFactory;
import top.cafebabe.fileManager.manger.memory.TempMongoMemory;
import top.cafebabe.fileManager.utils.Md5Util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author cafababe
 */
public class TempFileManager extends SimpleFileManager {

    private final Map<String, Md5Util> md5Utils;

    TempMongoMemory tempMongoMemory;

    public TempFileManager(TempMongoMemory memory, SplitterFactory splitterFactory, IntegratorFactory integratorFactory) {
        super(memory, splitterFactory, integratorFactory);
        this.tempMongoMemory = memory;
        this.md5Utils = new ConcurrentHashMap<>();
    }

    public String createTempFile() {
        TempBlockFile file = new TempBlockFile();
        this.md5Utils.put(file.getId().toString(), new Md5Util());
        this.tempMongoMemory.addTempFile(file);
        return file.getId().toString();
    }

    public boolean temp(String id, byte[] data) {
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
        if (!this.tempMongoMemory.updateTempFile(tempFile)) {
            this.tempMongoMemory.removeBlock(block.getMd5());
            return false;
        }
        this.md5Utils.get(tempFile.getId().toString()).update(data, 0, data.length);
        return true;
    }

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

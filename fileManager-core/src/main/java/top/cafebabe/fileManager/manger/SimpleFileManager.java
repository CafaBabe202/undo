package top.cafebabe.fileManager.manger;

import top.cafebabe.fileManager.bean.Block;
import top.cafebabe.fileManager.bean.BlockFile;
import top.cafebabe.fileManager.exception.ExistException;
import top.cafebabe.fileManager.exception.NotBlockException;
import top.cafebabe.fileManager.exception.NotFileException;
import top.cafebabe.fileManager.exception.ObjectInitException;
import top.cafebabe.fileManager.manger.integrator.Integrator;
import top.cafebabe.fileManager.manger.integrator.IntegratorFactory;
import top.cafebabe.fileManager.manger.splitter.Splitter;
import top.cafebabe.fileManager.manger.splitter.SplitterFactory;
import top.cafebabe.fileManager.manger.memory.Memory;
import top.cafebabe.fileManager.utils.Md5Util;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

/**
 * @author cafababe
 */
public class SimpleFileManager {

    private final Memory memory;
    private final SplitterFactory splitterFactory;
    private final IntegratorFactory integratorFactory;

    private final boolean remove;

    public SimpleFileManager(Memory memory, SplitterFactory splitterFactory, IntegratorFactory integratorFactory, boolean remove) {
        this.memory = memory;
        this.splitterFactory = splitterFactory;
        this.integratorFactory = integratorFactory;
        this.remove = remove;
    }

    public boolean addFile(InputStream file) throws ObjectInitException {
        try {
            List<String> content = new LinkedList<>();
            Md5Util md5Util = new Md5Util();
            Splitter splitter = this.splitterFactory.getInstance(file);
            // 循环添加没一个块文件到 memory。
            while (splitter.hasNext()) {
                byte[] buffer = splitter.next();
                Block block = new Block(Md5Util.md5(buffer), 1L, buffer.length, buffer);
                md5Util.update(buffer, 0, buffer.length);
                // 如果添加失败则回滚。
                if (!this.addBlock(block)) {
                    this.backFile(content);
                    return false;
                }
                content.add(block.getMd5());
            }
            // 添加完每一个块后，添加整体文件到 memory。
            BlockFile blockFile = new BlockFile(md5Util.design(), content, 1L, splitter.getSize());
            if (!this.addFile(blockFile)) {
                this.backFile(content);
                return false;
            }
            return true;
        } catch (ExistException | NotFileException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Integrator getFile(String md5) throws NotFileException {
        try {
            return this.integratorFactory.getInstance(memory, md5);
        } catch (ObjectInitException e) {
            throw new NotFileException(md5);
        }
    }

    public boolean removeFile(String md5) {
        try {
            BlockFile file = this.memory.getFile(md5);
            if (file == null)
                return true;
            boolean res = true;
            for (String s : file.getContent())
                res &= this.deleteBlock(s);
            return this.deleteFile(md5) && res;
        } catch (NotBlockException | NotFileException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean exist(String md5) {
        return this.memory.existFile(md5);
    }

    private void backFile(List<String> blocks) {
        for (String s : blocks) {
            this.memory.deleteBlock(s);
        }
    }

    private boolean addBlock(Block block) throws ExistException {
        try {
            return this.memory.existBlock(block.getMd5()) ? this.inrBlockCount(block.getMd5()) : this.memory.addBlock(block);
        } catch (NotBlockException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean deleteBlock(String md5) throws NotBlockException {
        if (this.memory.getBlockCount(md5) > 1)
            return this.reduceBlockCount(md5);
        return this.remove ? this.memory.deleteBlock(md5) : this.memory.setBlockCount(md5, 0);
    }

    private boolean reduceBlockCount(String md5) throws NotBlockException {
        return this.memory.setBlockCount(md5, this.memory.getBlockCount(md5) - 1);
    }

    private boolean inrBlockCount(String md5) throws NotBlockException {
        return this.memory.setBlockCount(md5, this.memory.getBlockCount(md5) + 1);
    }

    private boolean addFile(BlockFile file) throws ExistException, NotFileException {
        return this.memory.existFile(file.getMd5()) ? this.inrFileCount(file.getMd5()) : this.memory.addFile(file);
    }

    private boolean deleteFile(String md5) throws NotFileException {
        if (this.memory.getFileCount(md5) > 1)
            return this.reduceFileCount(md5);
        return this.remove ? this.memory.deleteFile(md5) : this.memory.setFileCount(md5, 0);
    }

    private boolean reduceFileCount(String md5) throws NotFileException {
        return this.memory.setFileCount(md5, this.memory.getFileCount(md5));
    }

    private boolean inrFileCount(String md5) throws NotFileException {
        return this.memory.setFileCount(md5, this.memory.getFileCount(md5) + 1);
    }
}

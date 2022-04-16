package top.cafebabe.fileManager.manger;

import top.cafebabe.fileManager.bean.Block;
import top.cafebabe.fileManager.bean.BlockFile;
import top.cafebabe.fileManager.exception.ExistException;
import top.cafebabe.fileManager.exception.NotFileException;
import top.cafebabe.fileManager.exception.ObjectInitException;
import top.cafebabe.fileManager.manger.integrator.Integrator;
import top.cafebabe.fileManager.manger.integrator.IntegratorFactory;
import top.cafebabe.fileManager.manger.splitter.Splitter;
import top.cafebabe.fileManager.manger.splitter.SplitterFactory;
import top.cafebabe.fileManager.manger.memory.Memory;
import top.cafebabe.fileManager.utils.Md5Util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author cafababe
 */
public class SimpleFileManager implements FileManager {

    private final Memory memory;
    private final SplitterFactory splitterFactory;
    private final IntegratorFactory integratorFactory;

    public SimpleFileManager(Memory memory, SplitterFactory splitterFactory, IntegratorFactory integratorFactory) {
        this.memory = memory;
        this.splitterFactory = splitterFactory;
        this.integratorFactory = integratorFactory;
    }

    @Override
    public boolean add(InputStream file) {
        List<String> content = new LinkedList<>();
        Md5Util md5Util = new Md5Util();
        Splitter splitter;
        try {
            splitter = this.splitterFactory.getInstance(file);
        } catch (ObjectInitException e) {
            e.printStackTrace();
            return false;
        }

        // 循环添加每一个块文件到 memory。如果添加失败将回滚。
        while (splitter.hasNext()) {
            byte[] buffer = splitter.next();
            Block block = new Block(Md5Util.md5(buffer), 1L, buffer.length, buffer);
            md5Util.update(buffer, 0, buffer.length);
            if (!this.memory.addBlock(block)) {
                this.removeBlock(content);
                return false;
            }
            content.add(block.getMd5());
        }

        // 添加文件，如果添加失败将回滚。
        BlockFile blockFile = new BlockFile(md5Util.design(), content, 1L, splitter.getSize());
        try {
            this.memory.addFile(blockFile);
        } catch (ExistException e) {
            this.removeBlock(content);
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean add(String md5) throws NotFileException {
        return this.memory.incrFile(md5);
    }

    @Override
    public boolean remove(String md5) {
        BlockFile file = this.memory.getFile(md5);
        if (file == null)
            return true;
        Iterator<String> iterator = file.getContent().iterator();
        List<String> temp = new ArrayList<>();
        while (iterator.hasNext()) {
            String m = iterator.next();
            if (!this.memory.removeBlock(m)) {
                this.incrBlock(temp);
                return false;
            }
            temp.add(m);
        }
        if (!this.memory.removeFile(md5)) {
            this.incrBlock(temp);
            return false;
        }
        return true;
    }

    @Override
    public Integrator get(String md5) {
        try {
            return this.integratorFactory.getInstance(this.memory, md5);
        } catch (ObjectInitException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean exist(String md5) {
        return this.memory.existFile(md5);
    }

    @Override
    public void gc() {
        memory.gc();
    }

    private void removeBlock(List<String> blocks) {
        blocks.forEach(this.memory::removeBlock);
    }

    private void incrBlock(List<String> blocks) {
        blocks.forEach(block -> this.memory.addBlock(this.memory.getBlock(block)));
    }
}

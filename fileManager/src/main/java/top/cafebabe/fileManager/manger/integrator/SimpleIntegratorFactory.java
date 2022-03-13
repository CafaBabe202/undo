package top.cafebabe.fileManager.manger.integrator;

import top.cafebabe.fileManager.bean.BlockFile;
import top.cafebabe.fileManager.exception.ObjectInitException;
import top.cafebabe.fileManager.manger.memory.Memory;

import java.util.Iterator;

/**
 * @author cafababe
 */
public class SimpleIntegratorFactory implements IntegratorFactory {

    @Override
    public Integrator getInstance(Memory memory, String md5)throws ObjectInitException {
        return new SimpleIntegrator(memory,md5);
    }

    class SimpleIntegrator implements Integrator {
        private final String fileMd5;
        private final Memory memory;
        private Iterator<String> now;

        public SimpleIntegrator(Memory memory, String md5) throws ObjectInitException {
            this.memory = memory;
            this.fileMd5 = md5;
            this.init();
        }

        private void init() throws ObjectInitException {
            BlockFile blockFile = this.memory.getFile(this.fileMd5);
            if (blockFile == null)
                throw new ObjectInitException("没有找到对应 MD5 的文件 ：" + this.fileMd5);
            this.now = blockFile.getContent().listIterator();
        }

        @Override
        public boolean hasNext() {
            return this.now == null || this.now.hasNext();
        }

        @Override
        public byte[] next() {
            if (this.now.hasNext())
                return this.memory.getBlock(this.now.next()).getContent();
            return null;
        }
    }

}

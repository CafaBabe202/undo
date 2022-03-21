package top.cafebabe.fileManager.manger.splitter;

import top.cafebabe.fileManager.exception.ObjectInitException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * @author cafababe
 */
public class SimpleSplitterFactory implements SplitterFactory{

    private int blockSize;

    public SimpleSplitterFactory(int blockSize){
        this.blockSize = blockSize;
    }

    @Override
    public Splitter getInstance(InputStream is) throws ObjectInitException {
        return new SimpleSplitter(is);
    }

    /**
     * 这是一个简单的分割器，将文件分割成相同大小的块。
     *
     * @author cafababe
     */
    class SimpleSplitter implements Splitter {

        private final InputStream is;

        private boolean isClosed;
        private long size;
        private int lastSize;
        private byte[] buffer;

        public SimpleSplitter(InputStream is) throws ObjectInitException {
            this.isClosed = false;
            this.size = this.lastSize = 0;
            if (blockSize <= 0)
                throw new ObjectInitException("初始化 SimpleSplitter 时，必须 blockSize > 0");
            this.is = is;
            this.read();
        }

        private void read() {
            if (this.isClosed) {
                this.buffer = null;
                return;
            }
            try {
                byte[] buffer = new byte[blockSize];
                this.lastSize = this.is.read(buffer);
                if (this.lastSize >= 0) {
                    this.size += this.lastSize;
                    this.buffer = this.lastSize == blockSize ? buffer : Arrays.copyOf(buffer, this.lastSize);
                } else {
                    this.buffer = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public boolean hasNext() {
            return this.lastSize != -1;
        }

        @Override
        public byte[] next() {
            byte[] res = this.buffer;
            this.read();
            return res;
        }

        @Override
        public void close() throws IOException {
            this.isClosed = true;
            this.is.close();
        }

        @Override
        public long getSize() {
            return this.size;
        }
    }
}

package top.cafebabe.fileManager;

import org.junit.Before;
import org.junit.Test;
import top.cafebabe.fileManager.manger.SimpleFileManager;
import top.cafebabe.fileManager.manger.integrator.Integrator;
import top.cafebabe.fileManager.manger.integrator.IntegratorFactory;
import top.cafebabe.fileManager.manger.integrator.SimpleIntegratorFactory;
import top.cafebabe.fileManager.manger.memory.Memory;
import top.cafebabe.fileManager.manger.memory.TempMongoMemory;
import top.cafebabe.fileManager.manger.splitter.SimpleSplitterFactory;
import top.cafebabe.fileManager.manger.splitter.SplitterFactory;
import top.cafebabe.fileManager.manger.memory.MongodbMemory;
import top.cafebabe.fileManager.manger.splitter.TempFileManager;
import top.cafebabe.fileManager.utils.Md5Util;

import java.io.*;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.Provider;
import java.util.Arrays;

/**
 * @author cafababe
 */
public class test {

    private SimpleFileManager simpleFileManager;
    private TempFileManager tempFileManager;

    private final String path = "/home/zh/a.pdf";

    @Before
    public void init() throws Exception {
        Memory mongodbMemory = new MongodbMemory("192.168.2.202", 27017, "file", "Admin@123", "file");
        TempMongoMemory tempMongoMemory = new TempMongoMemory("192.168.2.202", 27017, "file", "Admin@123", "file");
        SimpleSplitterFactory splitterFactory = new SimpleSplitterFactory(1024 * 1024);
        IntegratorFactory integratorFactory = new SimpleIntegratorFactory();
        this.simpleFileManager = new SimpleFileManager(mongodbMemory, splitterFactory, integratorFactory);
        this.tempFileManager = new TempFileManager(tempMongoMemory, splitterFactory, integratorFactory);
    }

    @Test
    public void add() throws Exception {
        System.out.println(this.simpleFileManager.add(new FileInputStream(this.path)));
    }

    @Test
    public void incrFile() throws Exception {
        System.out.println(this.simpleFileManager.add("22c0e20ff3f4d1c340c307deb5d14c91"));
    }

    @Test
    public void get() throws Exception {
        Integrator file = this.simpleFileManager.get("22c0e20ff3f4d1c340c307deb5d14c91");
        OutputStream os = new FileOutputStream(this.path);
        while (file.hasNext()) {
            os.write(file.next());
        }
    }

    @Test
    public void delete() throws Exception {
        System.out.println(this.simpleFileManager.remove("22c0e20ff3f4d1c340c307deb5d14c91"));
    }

    @Test
    public void exist() {
        System.out.println(this.simpleFileManager.exist("22c0e20ff3f4d1c340c307deb5d14c91"));
    }


    public byte[] cut(byte[] arr, int len) {
        byte[] res = new byte[len];
        System.arraycopy(arr, 0, res, 0, len);
        return res;
    }

    @Test
    public void temp() throws Exception {
        String tempFile = this.tempFileManager.createTempFile();
        System.out.println(tempFile);
        Md5Util md5Util = new Md5Util();
        InputStream is = new FileInputStream(this.path);
        byte[] buffer = new byte[1024];
        int len;
        while ((len = is.read(buffer)) == buffer.length) {
            if (!this.tempFileManager.temp(tempFile, buffer)) {
                System.out.println("fail");
                return;
            }
            md5Util.update(buffer, 0, len);
        }

        this.tempFileManager.temp(tempFile, cut(buffer, len));
        md5Util.update(buffer, 0, len);
        System.out.println(this.tempFileManager.store(tempFile, md5Util.design()));
    }


}

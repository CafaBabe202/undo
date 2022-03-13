package top.cafebabe.fileManager.exception;

/**
 * @author cafababe
 */
public class NotBlockException extends Exception {
    public NotBlockException(String md5) {
        super("没有找到块，MD5：" + md5);
    }
}

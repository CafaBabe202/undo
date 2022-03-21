package top.cafebabe.fileManager.exception;

/**
 * @author cafababe
 */
public class ExistException extends Exception {
    public ExistException(String md5) {

        super("该标识已经存在并禁止重复添加，MD5：" + md5);
    }
}

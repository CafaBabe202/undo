package top.cafebabe.fileManager.exception;

/**
 * @author cafababe
 */
public class NotFileException extends Exception{
    public NotFileException(String md5){
        super("没有找到文件，MD5：" + md5);
    }
}

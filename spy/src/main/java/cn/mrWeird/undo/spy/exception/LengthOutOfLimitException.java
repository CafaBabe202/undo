package cn.mrWeird.undo.spy.exception;

/**
 * 字符串或数组长度长处对定后，将抛出此异常。
 * @author MrWeird
 */
public class LengthOutOfLimitException extends java.lang.Exception {
    public LengthOutOfLimitException(String message){
        super(message);
    }
}

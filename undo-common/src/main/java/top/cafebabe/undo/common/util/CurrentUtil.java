package top.cafebabe.undo.common.util;

/**
 * @author cafababe
 * 获取当前状态的工具包
 */
public class CurrentUtil {
    /**
     * 以 long 的形式返回当前时间。
     * @return 当前时间
     */
    public static long now(){
        return System.currentTimeMillis();
    }
}

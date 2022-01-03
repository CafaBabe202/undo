package cn.mrWeird.undo.spy.bean;

import cn.mrWeird.undo.spy.exception.LengthOutOfLimitException;
import lombok.Data;

import java.nio.charset.StandardCharsets;

/**
 * 这是发送消息的对象，这个对象，你想要发送的数据将封装到这个对象之中，然后将他传递给一个电台，最后由电台负责发送给接受者。
 *
 * @author MrWeird
 */
@Data
public class Message {

    public static final int FULL = 1;
    public static final int WANING = 0;

    private long time;
    private String moduleId;
    private String moduleName;
    private int type;
    private SystemInfo info;
    private String warning;

    public void setWarning(String warning) throws LengthOutOfLimitException {
        if (warning.getBytes(StandardCharsets.UTF_8).length > 32) {
            throw new LengthOutOfLimitException("Warning message too long");
        }
        this.warning = warning;
    }
}

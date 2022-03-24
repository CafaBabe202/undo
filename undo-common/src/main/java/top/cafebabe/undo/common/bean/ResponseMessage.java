package top.cafebabe.undo.common.bean;

import lombok.Data;

/**
 * @author cafababe
 * 返回消息的统一格式。
 */
@Data
public class ResponseMessage {
    public static final int STATUS_FAIL = 100;
    public static final int STATUS_OK = 200;
    public static final int STATUS_ERROR = 500;

    private int status;
    private long timeStamp;
    private Object data;
}

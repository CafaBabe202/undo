package top.cafebabe.undo.common.bean;

import lombok.Data;

/**
 * @author cafababe
 * 返回消息的统一格式。
 */
@Data
public class ResponseMessage {
    public static final int STATUS_FAIL = 400;
    public static final int STATUS_TOKEN_INVALID = 401;
    public static final int PERMISSION_DENIED = 402;

    public static final int STATUS_OK = 200;
    public static final int STATUS_ERROR = 500;

    private int status;
    private long timeStamp;
    private Object data;
}

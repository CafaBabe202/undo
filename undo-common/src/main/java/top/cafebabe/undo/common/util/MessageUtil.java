package top.cafebabe.undo.common.util;


import top.cafebabe.undo.common.bean.ResponseMessage;

/**
 * @author cafababe
 * 用于各种返回消息的工具
 */
public class MessageUtil {
    /**
     * 创建一个成功的消息
     *
     * @param data 返回给前端的数据
     * @return ResponseMessage
     */
    public static ResponseMessage ok(Object data) {
        ResponseMessage responseMessage = create(data);
        responseMessage.setStatus(ResponseMessage.STATUS_OK);
        return responseMessage;
    }

    /**
     * 创建一个失败的消息
     *
     * @param data 返回给前端的数据
     * @return ResponseMessage
     */
    public static ResponseMessage fail(Object data) {
        ResponseMessage responseMessage = create(data);
        responseMessage.setStatus(ResponseMessage.STATUS_FAIL);
        return responseMessage;
    }

    private static ResponseMessage create(Object data) {
        ResponseMessage res = new ResponseMessage();
        res.setTimeStamp(CurrentUtil.now());
        res.setData(data);
        return res;
    }
}

package top.cafebabe.undo.common.util;

import cn.hutool.http.server.HttpServerRequest;

/**
 * @author cafababe
 */
public class RequestUtil {
    public static String getIp(HttpServerRequest request) {
        return request.getClientIP();
    }
}

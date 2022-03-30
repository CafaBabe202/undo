package top.cafebabe.undo.common.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @author cafababe
 */
public class RequestUtil {
    public static String getIp(HttpServletRequest request) {
        return request.getRemoteHost();
    }
}

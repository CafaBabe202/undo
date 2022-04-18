package top.cafebabe.undo.common.util;


import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;

import java.util.Enumeration;

/**
 * @author cafababe
 */
public class RequestUtil {
    public static String getIp(HttpServletRequest request) {
        return request.getRemoteHost();
    }

    public static String getReferer(HttpServletRequest request) {
        return request.getHeader(HttpHeaders.REFERER);
    }

    public static void printHead(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            System.out.println(name + "\t" + request.getHeader(name));
        }
    }
}

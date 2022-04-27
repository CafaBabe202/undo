package top.cafebabe.monitorshow.util;

import cn.hutool.http.HttpRequest;

import java.util.List;
import java.util.Map;

/**
 * @author cafababe
 */
public class HttpUtils {

    /**
     * 进行 Http 请求并返回请求后的结果。
     *
     * @param url        请求的地址。
     * @return 返回请求的结果，如果出现异常将返回 null。
     */
    public static String GET(String url) {
        try {
            HttpRequest request = HttpRequest.get(url);
            return request.execute().body();
        } catch (Exception e) {
            return null;
        }
    }
}

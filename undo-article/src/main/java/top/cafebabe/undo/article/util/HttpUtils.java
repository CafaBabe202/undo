package top.cafebabe.undo.article.util;

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
     * @param headers    请求头，不设置请使用 null。
     * @param parameters 请求参数，没有请使用 null。
     * @param timout     超时时间，不设置请使用 -1。
     * @return 返回请求的结果，如果出现异常将返回 null。
     */
    public static String GET(String url, Map<String, List<String>> headers, Map<String, Object> parameters, int timout) {
        try {
            HttpRequest request = HttpRequest.get(url);
            if (headers != null) request = request.header(headers);
            if (parameters != null) request = request.form(parameters);
            if (timout > 0) request = request.timeout(timout);
            return request.execute().body();
        } catch (Exception e) {
            return null;
        }
    }
}

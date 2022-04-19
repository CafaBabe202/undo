package top.cafebabe.undo.article.component;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import top.cafebabe.undo.article.controller.ArticleCtl;
import top.cafebabe.undo.common.bean.ResponseMessage;
import top.cafebabe.undo.common.util.Logger;

/**
 * 输出日志的统一方式
 * @author cafababe
 */
@ControllerAdvice
public class ControlLogger implements ResponseBodyAdvice<ResponseMessage> {
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return ArticleCtl.class.getPackage().toString().equals(returnType.getContainingClass().getPackage().toString());
    }

    @Override
    public ResponseMessage beforeBodyWrite(ResponseMessage body,
                                           MethodParameter returnType,
                                           MediaType selectedContentType,
                                           Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                           ServerHttpRequest request, ServerHttpResponse response
    ) {
        Logger.logger(request.getRemoteAddress() + "  ==> " + request.getURI());
        Logger.logger(body);
        return body;
    }
}

package top.cafebabe.undo.file.interceptor;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import top.cafebabe.undo.common.util.MessageUtil;
import top.cafebabe.undo.common.util.RequestUtil;
import top.cafebabe.undo.common.util.StringUtil;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author cafababe
 */
@Component
public class CORSInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object o) throws Exception {
        String referer = RequestUtil.getReferer(request);
        if (referer != null && referer.startsWith("http://undo.vip:"))
            return true;
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(StringUtil.toJson(MessageUtil.corsError()).getBytes());
        outputStream.close();
        return false;
    }
}

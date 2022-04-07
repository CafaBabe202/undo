package top.cafebabe.undo.user.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import top.cafebabe.undo.common.util.MessageUtil;
import top.cafebabe.undo.common.util.StringUtil;
import top.cafebabe.undo.user.bean.AppConfig;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author cafababe
 */
@Component
public class InnerAppInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        if (AppConfig.INNER_APP_PASSWORD.equals(request.getHeader(AppConfig.INNER_APP_PASSWORD_NAME_IN_HEADER)))
            return true;
        response.setContentType("application/json");
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(StringUtil.toJson(MessageUtil.fail("密码错误")).getBytes());
        outputStream.close();
        return false;
    }
}

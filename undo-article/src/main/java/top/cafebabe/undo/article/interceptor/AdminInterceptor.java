package top.cafebabe.undo.article.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import top.cafebabe.undo.article.bean.AppConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author cafababe
 */
@Component
public class AdminInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String attribute = (String) request.getSession().getAttribute(AppConfig.ADMIN_LOGIN_TOKEN_KEY_IN_SESSION);
        boolean login = request.getRemoteAddr().equals(attribute);
        if (login)
            return true;
        response.sendRedirect("/login.html");
        return false;
    }
}

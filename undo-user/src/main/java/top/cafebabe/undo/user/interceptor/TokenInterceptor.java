package top.cafebabe.undo.user.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import top.cafebabe.undo.common.util.TokenUtil;
import top.cafebabe.undo.user.bean.AppConfig;
import top.cafebabe.undo.user.dao.TokenRedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author cafababe
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Autowired
    TokenRedis tokenRedis;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String token = request.getHeader(AppConfig.TOKEN_NAME_IN_HEADER);
        if (token == null) return false;
        String token1 = tokenRedis.getToken(TokenUtil.getLoginTokenId(token, AppConfig.TOKEN_KEY));
        return token.equals(token1);
    }
}

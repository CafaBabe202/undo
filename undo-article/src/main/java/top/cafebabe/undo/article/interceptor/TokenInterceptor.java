package top.cafebabe.undo.article.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import top.cafebabe.undo.article.bean.AppConfig;
import top.cafebabe.undo.article.service.TokenService;
import top.cafebabe.undo.common.bean.LoginUser;
import top.cafebabe.undo.common.util.CurrentUtil;
import top.cafebabe.undo.common.util.MessageUtil;
import top.cafebabe.undo.common.util.StringUtil;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author cafababe
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {

    final TokenService tokenService;

    public TokenInterceptor(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {

        // 获取传过来的参数
        String token = request.getHeader(AppConfig.TOKEN_NAME_IN_HEADER);
        HttpSession session = request.getSession();
        LoginUser loginUser = (LoginUser) session.getAttribute(AppConfig.LOGIN_USER_KEY_IN_SESSION);
        Long refreshTime = (Long) session.getAttribute(AppConfig.USER_TOKEN_REFRESH_TIME_KEY_IN_SESSION);
        String nowToken = (String) session.getAttribute(AppConfig.USER_TOKEN_KEY_IN_SESSION);

        if (token != null // token 不为空
                && token.equals(nowToken) // 与之前的相等
                && loginUser != null // loginUser 依旧有效
                && (CurrentUtil.now() - refreshTime) < AppConfig.SESSION_TIMEOUT * 1000 // 没过期
        ) return true;

        // 通过 token 获取 loginUser 失败，返回错误信息
        if (null == (loginUser = tokenService.getLoginUser(token))) {
            response.setContentType("application/json");
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(StringUtil.toJson(MessageUtil.tokenInvalid()).getBytes());
            outputStream.close();
            return false;
        }

        // 在 session 中设置缓存
        session.setAttribute(AppConfig.LOGIN_USER_KEY_IN_SESSION, loginUser);
        session.setAttribute(AppConfig.USER_TOKEN_REFRESH_TIME_KEY_IN_SESSION, CurrentUtil.now());
        session.setAttribute(AppConfig.USER_TOKEN_KEY_IN_SESSION, token);
        return true;
    }
}

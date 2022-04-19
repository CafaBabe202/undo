package top.cafebabe.undo.article.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import top.cafebabe.undo.article.interceptor.AdminInterceptor;
import top.cafebabe.undo.article.interceptor.TokInterceptor;
import top.cafebabe.undo.article.interceptor.TokenInterceptor;

/**
 * 配置拦截器
 * 各个接口的权限由接口的后缀标识。
 * token：需要用户登录的 token。
 * tok：有没有 token 都可以，但是有的敏感资源没有 token 将限制访问。
 * cors：禁止跨域。
 * tokCors：tok 与 Cors 的结合。
 *
 * @author cafababe
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {
    final TokenInterceptor tokenInterceptor;
    final TokInterceptor tokInterceptor;
    final AdminInterceptor adminInterceptor;

    public InterceptorConfig(TokenInterceptor tokenInterceptor, TokInterceptor tokInterceptor, AdminInterceptor adminInterceptor) {
        this.tokenInterceptor = tokenInterceptor;
        this.tokInterceptor = tokInterceptor;
        this.adminInterceptor = adminInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(tokInterceptor)
                .addPathPatterns("/**/*.tok")
                .addPathPatterns("/**/*.tok/**/*");

        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**/*.token")
                .addPathPatterns("/**/*.token/**/*");

        registry.addInterceptor(adminInterceptor)
                .addPathPatterns("/review/**/*")
                .addPathPatterns("/review.html")
                .excludePathPatterns("/review/login")
                .excludePathPatterns("/login.html");
    }
}


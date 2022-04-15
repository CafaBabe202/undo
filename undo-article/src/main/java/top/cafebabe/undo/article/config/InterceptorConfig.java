package top.cafebabe.undo.article.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import top.cafebabe.undo.article.interceptor.AdminInterceptor;
import top.cafebabe.undo.article.interceptor.TokInterceptor;
import top.cafebabe.undo.article.interceptor.TokenInterceptor;

/**
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


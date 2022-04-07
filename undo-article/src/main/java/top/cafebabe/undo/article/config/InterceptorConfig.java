package top.cafebabe.undo.article.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import top.cafebabe.undo.article.interceptor.LogInterceptor;
import top.cafebabe.undo.article.interceptor.TokenInterceptor;

/**
 * @author cafababe
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {
    final TokenInterceptor tokenInterceptor;
    final
    LogInterceptor logInterceptor;

    public InterceptorConfig(TokenInterceptor tokenInterceptor, LogInterceptor logInterceptor) {
        this.tokenInterceptor = tokenInterceptor;
        this.logInterceptor = logInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(logInterceptor)
                .addPathPatterns("/**/*");

        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**/*.token/**/*");
    }
}


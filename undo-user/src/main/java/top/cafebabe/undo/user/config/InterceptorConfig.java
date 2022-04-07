package top.cafebabe.undo.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import top.cafebabe.undo.user.interceptor.InnerAppInterceptor;
import top.cafebabe.undo.user.interceptor.LogInterceptor;
import top.cafebabe.undo.user.interceptor.TokenInterceptor;

/**
 * @author cafababe
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {
    final InnerAppInterceptor innerAppInterceptor;
    final TokenInterceptor tokenInterceptor;
    final LogInterceptor logInterceptor;

    public InterceptorConfig(InnerAppInterceptor innerAppInterceptor, TokenInterceptor tokenInterceptor, LogInterceptor logInterceptor) {
        this.innerAppInterceptor = innerAppInterceptor;
        this.tokenInterceptor = tokenInterceptor;
        this.logInterceptor = logInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(logInterceptor)
                .addPathPatterns("/**/*");

        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**/*.token/**/*");

        registry.addInterceptor(innerAppInterceptor)
                .addPathPatterns("/innerApp/**/*");
    }
}

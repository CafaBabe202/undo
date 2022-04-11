package top.cafebabe.undo.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import top.cafebabe.undo.user.interceptor.InnerAppInterceptor;
import top.cafebabe.undo.user.interceptor.TokenInterceptor;

/**
 * @author cafababe
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {
    final InnerAppInterceptor innerAppInterceptor;
    final TokenInterceptor tokenInterceptor;

    public InterceptorConfig(InnerAppInterceptor innerAppInterceptor, TokenInterceptor tokenInterceptor) {
        this.innerAppInterceptor = innerAppInterceptor;
        this.tokenInterceptor = tokenInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**/*.token")
                .addPathPatterns("/**/*.token/**/*");

        registry.addInterceptor(innerAppInterceptor)
                .addPathPatterns("/innerApp/**/*");
    }
}

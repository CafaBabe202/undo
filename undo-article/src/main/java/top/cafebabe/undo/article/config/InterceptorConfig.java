package top.cafebabe.undo.article.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import top.cafebabe.undo.article.interceptor.TokInterceptor;
import top.cafebabe.undo.article.interceptor.TokenInterceptor;

/**
 * @author cafababe
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {
    final TokenInterceptor tokenInterceptor;
    final TokInterceptor tokInterceptor;

    public InterceptorConfig(TokenInterceptor tokenInterceptor, TokInterceptor tokInterceptor) {
        this.tokenInterceptor = tokenInterceptor;
        this.tokInterceptor = tokInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(tokInterceptor)
                .addPathPatterns("/**/*.tok")
                .addPathPatterns("/**/*.tok/**/*");

        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**/*.token")
                .addPathPatterns("/**/*.token/**/*");
    }
}


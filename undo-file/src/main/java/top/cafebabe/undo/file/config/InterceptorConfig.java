package top.cafebabe.undo.file.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import top.cafebabe.undo.file.interceptor.CORSInterceptor;
import top.cafebabe.undo.file.interceptor.TokInterceptor;
import top.cafebabe.undo.file.interceptor.TokenInterceptor;

/**
 * @author cafababe
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {
    final TokenInterceptor tokenInterceptor;
    final TokInterceptor tokInterceptor;
    final CORSInterceptor corsInterceptor;

    public InterceptorConfig(TokenInterceptor tokenInterceptor, TokInterceptor tokInterceptor, CORSInterceptor corsInterceptor) {
        this.tokenInterceptor = tokenInterceptor;
        this.tokInterceptor = tokInterceptor;
        this.corsInterceptor = corsInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(tokInterceptor)
                .addPathPatterns("/**/*.tok")
                .addPathPatterns("/**/*.tok/**/*")
                .addPathPatterns("/**/*.tokCors")
                .addPathPatterns("/**/*.tokCors/**/*");

        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**/*.token")
                .addPathPatterns("/**/*.token/**/*");

        registry.addInterceptor(corsInterceptor)
                .addPathPatterns("/**/*.cors")
                .addPathPatterns("/**/*.cors/**/*")
                .addPathPatterns("/**/*.tokCors")
                .addPathPatterns("/**/*.tokCors/**/*");
    }
}


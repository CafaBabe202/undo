package top.cafebabe.undo.article;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "top.cafebabe.undo.article.dao",
        "top.cafebabe.undo.article.controller",
        "top.cafebabe.undo.article.service",
        "top.cafebabe.undo.article.config",
        "top.cafebabe.undo.article.interceptor"
})
public class UndoArticleApplication {

    public static void main(String[] args) {
        SpringApplication.run(UndoArticleApplication.class, args);
    }

}
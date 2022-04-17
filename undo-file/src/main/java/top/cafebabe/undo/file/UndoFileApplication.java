package top.cafebabe.undo.file;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {
        "top.cafebabe.undo.file.config",
        "top.cafebabe.undo.file.controller",
        "top.cafebabe.undo.file.component",
        "top.cafebabe.undo.file.dao",
        "top.cafebabe.undo.file.service",
        "top.cafebabe.undo.file.interceptor"
})
@SpringBootApplication(exclude = MongoAutoConfiguration.class)
public class UndoFileApplication {
    public static void main(String[] args) {
        SpringApplication.run(UndoFileApplication.class, args);
    }

}

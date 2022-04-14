package top.cafebabe.undofile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {
        "top.cafebabe.undofile.config",
        "top.cafebabe.undofile.controller",
        "top.cafebabe.undofile.dao",
        "top.cafebabe.undofile.service",
        "top.cafebabe.undofile.interceptor"
})
@SpringBootApplication(exclude = MongoAutoConfiguration.class)
public class UndoFileApplication {
    public static void main(String[] args) {
        SpringApplication.run(UndoFileApplication.class, args);
    }

}

package top.cafebabe.undo.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "top.cafebabe.undo.user.dao",
        "top.cafebabe.undo.user.controller",
        "top.cafebabe.undo.user.interceptor",
        "top.cafebabe.undo.user.config",
        "top.cafebabe.undo.user.service"})
public class UndoUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UndoUserApplication.class, args);
    }

}

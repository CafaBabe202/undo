package top.cafebabe.monitorshow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"top.cafebabe.monitorshow.controller"})
public class MonitorShowApplication {

    public static void main(String[] args) {
        SpringApplication.run(MonitorShowApplication.class, args);
    }

}

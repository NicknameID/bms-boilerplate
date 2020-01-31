package tech.mufeng.boilerplate.bms.id.generator.service;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.dubbo.container.Main;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo(scanBasePackages="tech.mufeng.boilerplate.bms.id.generator.service")
public class IdGeneratorServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(IdGeneratorServiceApplication.class, args);
        // Dubbo main
        Main.main(args);
    }
}

package tech.mufeng.boilerplate.bms.id.generator.rpc.server;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.dubbo.container.Main;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo(scanBasePackages="tech.mufeng.boilerplate.bms.id.generator.rpc.server")
public class IdGeneratorRpcServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(IdGeneratorRpcServerApplication.class, args);
        // Dubbo main
        Main.main(args);
    }
}

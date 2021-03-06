package tech.mufeng.boilerplate.bms.user.center.rpc.server;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.dubbo.container.Main;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableDubbo(scanBasePackages="tech.mufeng.boilerplate.bms.user.center.rpc.server")
public class UserCenterRpcServerApplication {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        SpringApplication.run(UserCenterRpcServerApplication.class, args);
        Main.main(args); // Dubbo Container
    }
}

package ru.relex.minisocialnetwork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:constants.properties")
@SpringBootApplication
public class MiniSocialNetworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiniSocialNetworkApplication.class, args);
    }

}

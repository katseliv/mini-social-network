package ru.relex.minisocialnetwork.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
        "ru.relex.minisocialnetwork.rest"
})
public class RestConfiguration {

}

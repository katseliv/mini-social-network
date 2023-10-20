package ru.relex.minisocialnetwork.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = {
        "ru.relex.minisocialnetwork.model.entity",
        "ru.relex.minisocialnetwork.converter"
})
@EnableJpaRepositories(basePackages = "ru.relex.minisocialnetwork.repository")
public class DatabaseConfiguration {
}

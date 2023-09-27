package me.levani.authorizationserver.utils;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan({"me.levani.authorizationserver"})
@EnableJpaRepositories("me.levani.authorizationserver.repository")
@EntityScan("me.levani.authorizationserver.model")
@Configuration
public class AuthorizationServerConfig {
}

package me.levani.authorizationserver.utils;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Configuration
@Import(AuthorizationServerConfig.class)
public @interface AuthorizationServer {
}

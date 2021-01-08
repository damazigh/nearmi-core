package org.nearmi.core.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import javax.annotation.PostConstruct;

@EnableWebSecurity
@Configuration
@Profile("!unsecure")
@Order(1)
@ConditionalOnProperty(value = "nearmi.config.override-security", havingValue = "false", matchIfMissing = true)
@Slf4j
public class DefaultSecurityConfig extends SecurityConfig {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http.authorizeRequests().anyRequest().authenticated();
        log.warn("Security configuration not overrode, using default config {}", this.getClass());
    }

    @PostConstruct
    public void trace() {
        log.warn("Security configuration not overrode, using default config {}", this.getClass());
    }

}

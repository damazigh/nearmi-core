package org.nearmi.core.security;


import lombok.extern.slf4j.Slf4j;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Slf4j
public abstract class SecurityConfig extends KeycloakWebSecurityConfigurerAdapter {

    @Value("${nearmi.config.debugSecurity:false}")
    private boolean debugSecurity;

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder authBuilder) {
        KeycloakAuthenticationProvider kap = new CustomKeycloakAuthenticationProvider();
        SimpleAuthorityMapper sap = new SimpleAuthorityMapper();
        sap.setPrefix("ROLE_");
        kap.setGrantedAuthoritiesMapper(sap);
        authBuilder.authenticationProvider(kap);
    }

    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new NullAuthenticatedSessionStrategy();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http.cors().and().csrf().disable().sessionManagement()
                .sessionCreationPolicy((SessionCreationPolicy.STATELESS));
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.debug(debugSecurity).ignoring().antMatchers("/actuator/health");
    }

    @Bean
    @ConditionalOnProperty(value = "nearmi.config.enable-dev-cors", havingValue = "true")
    public CorsConfigurationSource corsConfigurationSource() {
        log.info("Development cors configuration enabled");
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("authorization", "content-type", "upgrade-insecure-request"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
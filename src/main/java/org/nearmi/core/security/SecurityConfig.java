package org.nearmi.core.security;


import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@EnableWebSecurity
@Configuration
@Profile("!unsecure")
@Order(1)
public class SecurityConfig extends KeycloakWebSecurityConfigurerAdapter {


    @Autowired
    private void configureGloabl(AuthenticationManagerBuilder authBuilder) {
        authBuilder.authenticationProvider(keycloakAuthenticationProvider());
    }

    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new NullAuthenticatedSessionStrategy();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http.csrf().disable().sessionManagement().sessionCreationPolicy((SessionCreationPolicy.STATELESS))
                .and().authorizeRequests().antMatchers("/sso/login").permitAll().anyRequest().authenticated();
    }
}
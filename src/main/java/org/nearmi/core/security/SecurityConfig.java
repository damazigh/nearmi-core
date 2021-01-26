package org.nearmi.core.security;


import lombok.extern.slf4j.Slf4j;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@Slf4j
public abstract class SecurityConfig extends KeycloakWebSecurityConfigurerAdapter {

    @Value("${nearmi.config.debugSecurity:false}")
    private boolean debugSecurity;

    @Autowired
    protected void configureGloabl(AuthenticationManagerBuilder authBuilder) {
        authBuilder.authenticationProvider(keycloakAuthenticationProvider());
    }

    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new NullAuthenticatedSessionStrategy();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http.csrf().disable().sessionManagement()
                .sessionCreationPolicy((SessionCreationPolicy.STATELESS));
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.debug(debugSecurity).ignoring().antMatchers("/actuator/health");
    }
}
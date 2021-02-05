package org.nearmi.core.security;

import lombok.extern.slf4j.Slf4j;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomKeycloakAuthenticationProvider extends KeycloakAuthenticationProvider {
    @Value("${nearmi.config.debug-security:false}")
    boolean debugSecurity;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        if (debugSecurity && log.isDebugEnabled()) {
            log.warn("User roles >>>>>>>>>>>>>>>>>>>>");
            KeycloakAuthenticationToken kat = (KeycloakAuthenticationToken) authentication;
            for (String role : kat.getAccount().getRoles()) {
                log.warn("Role : {}", role);
            }
            log.warn("end User role >>>>>>>>>>>>>>>>>");
        }


        return super.authenticate(authentication);
    }
}

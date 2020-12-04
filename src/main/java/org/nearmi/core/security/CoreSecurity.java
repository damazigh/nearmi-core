package org.nearmi.core.security;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Map;

public class CoreSecurity {
    private static Map<String, Object> claims;

    public static String getAttribute(String attr) {
        if (claims == null) {
            initClaims();
        }
        return (String) claims.get(attr);
    }

    public static boolean isAuthenticated() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null
                && auth.isAuthenticated();
    }

    private static void initClaims() {
        SecurityContext ctx = SecurityContextHolder.getContext();
        if (ctx.getAuthentication() != null) {
            Authentication auth = ctx.getAuthentication();
            if (auth instanceof KeycloakAuthenticationToken) {
                KeycloakAuthenticationToken keycloakToken = (KeycloakAuthenticationToken) auth;

                if (keycloakToken.getPrincipal() instanceof KeycloakPrincipal) {
                    KeycloakPrincipal<KeycloakSecurityContext> kp = (KeycloakPrincipal<KeycloakSecurityContext>) keycloakToken.getPrincipal();
                    AccessToken token = kp.getKeycloakSecurityContext().getToken();
                    claims = token.getOtherClaims();
                } else {
                    throw new IllegalArgumentException("not a keycloak principal !");
                }
            } else {
                throw new IllegalStateException("not a keycloak authentication !");
            }
        } else {
            throw new IllegalStateException("authentication is null !");
        }
    }
}

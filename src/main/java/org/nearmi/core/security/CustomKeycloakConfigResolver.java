package org.nearmi.core.security;

import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.KeycloakDeploymentBuilder;
import org.keycloak.adapters.spi.HttpFacade;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
public class CustomKeycloakConfigResolver extends KeycloakSpringBootConfigResolver {
    private KeycloakDeployment deployment;


    CustomKeycloakConfigResolver(KeycloakSpringBootProperties props) {
        deployment = KeycloakDeploymentBuilder.build(props);
    }

    @Override
    public KeycloakDeployment resolve(HttpFacade.Request request) {
        return deployment;
    }
}

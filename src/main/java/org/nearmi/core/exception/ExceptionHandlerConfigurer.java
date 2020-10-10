package org.nearmi.core.exception;

import org.nearmi.core.resource.ResourceDictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * expose some needed bean, for runtime exception handling.
 * a global handler is registered that transform {@link MiException } {@link ExceptionResourceLoader}
 *
 * @author A. Djebarri
 * @Since 1.0
 */
@Configuration
public class ExceptionHandlerConfigurer {
    @Autowired
    private ExceptionResourceLoader loader;



    @Bean
    @Qualifier("genericResource")

    public String genericResource() {
        return "common.yml";
    }

    @Bean
    public ResourceDictionary dictionary() {
        return this.loader.getDictionary();
    }
}

package org.nearmi.core.exception;

import lombok.extern.slf4j.Slf4j;
import org.nearmi.core.resource.DictionaryEntry;
import org.nearmi.core.resource.ResourceDictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import org.springframework.http.codec.ServerCodecConfigurer;

/**
 * Global exception handler. a handler is registered to catch every exception raising in the app.<br/>
 * When the exception is type of @{@link MiException} or it's sub classes (corresponding to functional error)
 * a translation is made with the given key {@see MiException#getKey} and registered resources {@code this.dictionary}
 * to build the response error object {@link MiError}<br/>
 * When the exception is any other type, that means an error 500 occurred during execution -
 * to exit properly the error is caught and logged an generic {@link MiError} is built with some contextual details
 *
 * @author A.Djebarri
 * @since 1.0
 * @see ExceptionResourceLoader
 */
@Component
@Order(-2)
@Slf4j
public class ExceptionHandler extends AbstractErrorWebExceptionHandler {
    @Autowired
    private ResourceDictionary dictionary;
    private static final String INTERNAL_SERVER_ERROR_CODE = "NMI_G_0000";


    public ExceptionHandler(ErrorAttributes errorAttributes, ResourceProperties resourceProperties, ServerCodecConfigurer serverCodecConfigurer, ApplicationContext applicationContext) {
        super(errorAttributes, resourceProperties, applicationContext);
        this.setMessageWriters(serverCodecConfigurer.getWriters());
        this.setMessageReaders(serverCodecConfigurer.getReaders());
    }

    /**
     * convert a functional error to rest response ready message
     * @param ex a raised {@link MiException} during runtime
     * @param <T_EX> Type of exception (must be {@link MiException} or its sub classes)
     * @return a built in {@link MiError}
     */
    protected <T_EX extends MiException> MiError translateMiException(T_EX ex) {
        DictionaryEntry entry = this.dictionary.findByKey(ex.getKey());
        MiError err = new MiError();
        if (entry != null) {
            err.setKey(ex.getKey());
            err.setMessage(entry.getMessage());
            err.setDescription(String.format(entry.getDescription(), ex.getDescParams()));
            err.setStatus(entry.getStatus());
        }
        return err;
    }

    /**
     * convert any other exception into built in message to recover properly from error
     * @param ex exception thrown during execution
     * @return {@link MiError}
     */
    protected MiError translateOthers(Exception ex) {
        // wrap error into MiException
        MiException miException = new MiException(INTERNAL_SERVER_ERROR_CODE, ex);
        log.error("unexpected error occurred- id : {}" ,miException.getId(), miException);
        return this.translateMiException(miException);
    }

    /**
     * convert {@link MiError} to reactive response that can be handled by the spring webflux framework
     * @param thr the thrown error during runtime
     * @return {@link Mono<MiError>}
     */
    protected Mono<ServerResponse> toReactiveResponse(Throwable thr) {
        MiError miError = null;
        if (MiException.class.isAssignableFrom(thr.getClass())) {
            miError = this.translateMiException((MiException) thr);
        } else {
            miError = this.translateOthers((Exception) thr);
        }
        int status = miError.getStatus();
        return ServerResponse.status(status).body(BodyInserters.fromValue(miError));
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(),
                req -> this.toReactiveResponse(errorAttributes.getError(req)));
    }
}

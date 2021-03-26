package org.nearmi.core.exception;

import lombok.extern.slf4j.Slf4j;
import org.nearmi.core.resource.DictionaryEntry;
import org.nearmi.core.resource.ResourceDictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

/**
 * Global exception handler. a handler is registered to catch every exception raising in the app.<br/>
 * When the exception is type of @{@link MiException} or it's sub classes (corresponding to functional error)
 * a translation is made with the given key {@see MiException#getKey} and registered resources {@code this.dictionary}
 * to build the response error object {@link MiError}<br/>
 * When the exception is any other type, that means an error 500 occurred during execution -
 * to exit properly the error is caught and logged an generic {@link MiError} is built with some contextual details
 *
 * @author A.Djebarri
 * @see ExceptionResourceLoader
 * @since 1.0
 */
@Component
@Slf4j
@RestControllerAdvice
public class ExceptionHandlerController {
    @Autowired
    private ResourceDictionary dictionary;
    private static final String INTERNAL_SERVER_ERROR_CODE = "NMI_G_0000";


    /**
     * convert a functional error to rest response ready message
     *
     * @param ex     a raised {@link MiException} during runtime
     * @param <T_EX> Type of exception (must be {@link MiException} or its sub classes)
     * @return a built in {@link MiError}
     */
    private <T_EX extends MiException> MiError translateMiException(T_EX ex) {
        DictionaryEntry entry = this.dictionary.findByKey(ex.getKey());
        MiError err = new MiError();
        if (entry != null) {
            err.setKey(ex.getKey());
            err.setMessage(entry.getMessage());
            if (ex.getDescParams() != null && ex.getDescParams().length > 0) {
                err.setDescription(String.format(entry.getDescription(), ex.getDescParams()));
            } else {
                err.setDescription(entry.getDescription());
            }
            err.setStatus(entry.getStatus());
        }
        return err;
    }

    /**
     * convert any other exception into built in message to recover properly from error
     *
     * @param ex       exception thrown during execution
     * @param response spring wrapper for http response (used to set http status)
     * @return {@link MiError}
     */
    @ExceptionHandler(Exception.class)
    public MiError translateOthers(Exception ex, HttpServletResponse response) {
        // wrap error into MiException
        MiException miException = new MiException(INTERNAL_SERVER_ERROR_CODE, ex);
        log.error("unexpected error occurred- id : {}", miException.getId(), ex);
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return this.translateMiException(miException);
    }

    /**
     * handle functional exception and convert them to {@link MiError}
     */
    @ExceptionHandler(MiException.class)
    public MiError translateMiException(MiException ex, HttpServletResponse response) {
        MiError miError = null;
        miError = this.translateMiException(ex);
        int status = miError.getStatus();
        response.setStatus(status);
        return miError;
    }


}

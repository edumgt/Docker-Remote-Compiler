package com.cp.compiler.config;

import com.cp.compiler.exceptions.MonitoredException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

/**
 * Customize Json error returned to the user.
 */
@Slf4j
@Configuration
public class ErrorAttributesConfig {

    @Bean
    public ErrorAttributes errorAttributes() {

        return new DefaultErrorAttributes() {

            @Override
            public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
                Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);
                Throwable error = getError(webRequest);

                if (error instanceof MonitoredException) {
                    MonitoredException monitoredException = (MonitoredException) error;
                    errorAttributes.put("errorCode", monitoredException.getErrorCode());
                    errorAttributes.put("errorType", monitoredException.getErrorType());
                    errorAttributes.put("isRetryableError", monitoredException.isRetryableError());
                    errorAttributes.put("retryIn", monitoredException.getRetryIn());
                } else {
                    // Unexpected errors (or null)
                    String hidden = (error != null ? String.valueOf(error.getMessage()) : "<null>");
                    log.info("The following error message will be hidden to the caller: {}", hidden);
                    errorAttributes.put("message", "Unexpected error occurred");
                }

                return errorAttributes;
            }
        };
    }
}

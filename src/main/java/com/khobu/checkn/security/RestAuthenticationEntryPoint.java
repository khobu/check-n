package com.khobu.checkn.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The Entry Point will not redirect to any sort of Login - it will return the 401
 */
@Component
public final class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestAuthenticationEntryPoint.class);
    @Override
    public void commence(
        final HttpServletRequest request,
        final HttpServletResponse response,
        final AuthenticationException authException) throws IOException {
        System.out.println("User is unauthorized");
        LOGGER.info("User is Unauthorized");
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }

}

package com.khobu.checkn.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AccessDenied implements AccessDeniedHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccessDenied.class);

    @Override
    public void handle(final HttpServletRequest request, final HttpServletResponse response, final AccessDeniedException ex) throws IOException, ServletException {
        System.out.println("Access is denied");
        LOGGER.info("Access is denied");
        response.getOutputStream().print("Insufficient Privileges");
        response.setStatus(403);
        // response.sendRedirect("/my-error-page");
    }


}
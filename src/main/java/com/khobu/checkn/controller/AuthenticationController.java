package com.khobu.checkn.controller;


import com.khobu.checkn.config.Salt;
import com.khobu.checkn.domain.Credential;
import com.khobu.checkn.domain.Credentials;
import com.khobu.checkn.domain.User;
import com.khobu.checkn.service.CredentialService;
import com.khobu.checkn.service.SessionService;
import com.khobu.checkn.service.UserService;
import com.khobu.checkn.util.PasswordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "/api")
public class AuthenticationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

    //@Autowired
    //private AuthenticationManager authenticationManager;


    @Autowired
    private CredentialService credentialService;

    @Autowired
    private UserService userService;

    @Autowired
    private SessionService sessionService;

    @PostMapping("/login")
    public boolean login(@RequestBody Credentials credentials) {
        LOGGER.info("attempting to log in");
        boolean isValid = false;
        Credential credential = new Credential();
        String securePassword = PasswordUtils.generateSecurePassword(credentials.getPassword(), Salt.VALUE);
        credential.setUsername(credentials.getUsername());
        credential.setPassword(securePassword);

        Credential tempCredential = credentialService.findByUsernameAndPassword(credential);
        isValid =  tempCredential != null && tempCredential.isActive();

        if(isValid){
            List<User> userList = userService.findByUsername(tempCredential.getUsername());
            if(userList.size() == 0){
                sessionService.setUser(userList.get(0));
            }
        }

        return isValid;

    }

    @PostMapping("/login/create")
    public Credential create(@RequestBody Credentials credentials) {
        LOGGER.info("creating new credentials");
        Credential credential = new Credential();
        String securePassword = PasswordUtils.generateSecurePassword(credentials.getPassword(), Salt.VALUE);
        credential.setUsername(credentials.getUsername());
        credential.setPassword(securePassword);
        credential.setActive(true);

        Credential result = credentialService.saveCredential(credential);
        return result;

    }

    @DeleteMapping("/logout")
    public boolean logout(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("attempting to logout");
        sessionService.clearUser();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return true;
    }

}

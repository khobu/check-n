package com.khobu.checkn.controller;


import com.khobu.checkn.annotation.IsAdmin;
import com.khobu.checkn.domain.Credential;
import com.khobu.checkn.domain.User;
import com.khobu.checkn.service.CredentialService;
import com.khobu.checkn.service.UserService;
import com.khobu.checkn.service.SessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class CredentialController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CredentialController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private CredentialService credentialService;

    @Autowired
    private SessionService sessionService;

    @IsAdmin
    @GetMapping("/credentials")
    public ResponseEntity<List<Credential>> getCredentials() {
        LOGGER.info("getting all credentials");
        List<Credential> results = credentialService.retrieveAllCredentials();
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @IsAdmin
    @PostMapping("/credentials")
    public ResponseEntity<Credential> createCredential(@RequestBody Credential credential) {
        LOGGER.info("creating credentials");
        Credential result = credentialService.saveCredential(credential);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/credentials/user/{id}")
    public ResponseEntity<Credential> findUserCredential(@PathVariable Long id) {
        LOGGER.info("finding user credentials");
        User user = userService.findUserById(id);
        Credential result = null;
        if(user != null && !user.getUsername().trim().isEmpty()){
            result = credentialService.findByUsername(user.getUsername());
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @IsAdmin
    @GetMapping("/credentials/{id}")
    public ResponseEntity<Credential> findCredential(@PathVariable Long id) {
        LOGGER.info("finding credentials");
        Credential result = credentialService.findCredentialById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @IsAdmin
    @PutMapping("/credentials/{id}")
    public ResponseEntity<Credential> updateCredential(@RequestBody Credential newCredential, @PathVariable Long id) {
        LOGGER.info("updating credentials");
        Credential result = credentialService.updateCredential(newCredential, id);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @IsAdmin
    @DeleteMapping("/credentials/{id}")
    public ResponseEntity<Boolean> deleteCredential(@PathVariable Long id) {
        LOGGER.info("deleting credentials");
        boolean result = credentialService.deleteCredential(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}

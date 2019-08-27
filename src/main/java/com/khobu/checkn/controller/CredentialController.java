package com.khobu.checkn.controller;


import com.khobu.checkn.annotation.IsAdmin;
import com.khobu.checkn.domain.Credential;
import com.khobu.checkn.domain.Employee;
import com.khobu.checkn.service.CredentialService;
import com.khobu.checkn.service.EmployeeService;
import com.khobu.checkn.service.UserService;
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
    private EmployeeService employeeService;

    @Autowired
    private CredentialService credentialService;

    @Autowired
    private UserService userService;

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
        credential.setUpdatedByEmployeeId(userService.getUserId());
        Credential result = credentialService.saveCredential(credential);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/credentials/employee/{id}")
    public ResponseEntity<Credential> findEmployeeCredential(@PathVariable Long id) {
        LOGGER.info("finding employee credentials");
        Employee employee = employeeService.findEmployeeById(id);
        Credential result = null;
        if(employee != null && !employee.getUsername().trim().isEmpty()){
            result = credentialService.findByUsername(employee.getUsername());
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
        newCredential.setUpdatedByEmployeeId(userService.getUserId());
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

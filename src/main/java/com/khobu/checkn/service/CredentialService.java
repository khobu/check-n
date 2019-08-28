package com.khobu.checkn.service;


import com.khobu.checkn.domain.Credential;
import com.khobu.checkn.repository.CredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Random;

@Service
public class CredentialService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    private void init(){

        Credential credential = new Credential();
        credential.setUsername("admin");
        credential.setPassword("admin");
        credential.setActive(true);
        saveCredential(credential);
    }

    @Autowired
    private CredentialRepository credentialRepository;

    public String generateRandomPassword(){
        String generatedString = "";
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        generatedString = new String(array, Charset.forName("UTF-8"));
        return generatedString;
    }

    public List<Credential> retrieveAllCredentials() {
        return credentialRepository.findAll();
    }

    public Credential findByUsernameAndPassword(Credential credential){
        return credentialRepository.findCredentialByUsernameAndPassword(credential.getUsername(), credential.getPassword());
    }

    public Credential findByUsername(String username){
        for(Credential credential:retrieveAllCredentials()){
            System.out.println("Found Username: "+credential.getUsername());
        }
        return credentialRepository.findCredentialByUsername(username);
    }

    public Credential findCredentialById(long id) {
        return credentialRepository.findById(id)
                .orElse(null);
        //.orElseThrow(() -> new Exception("Credential Not Found")); //CredentialNotFoundException(id)
    }

    public Credential saveCredential(Credential credential) {
        Credential returnValue = null;
        encodePassword(credential);
        if(credential.isValid()) {
            try {
                returnValue = credentialRepository.save(credential);
            } catch (Exception ex) {
            }
        }
        return returnValue;
    }

    public Credential updateCredential(Credential pCredential, long id) {
        return credentialRepository.findById(id)
                .map(credential -> {
                    credential = updateCredential(credential, pCredential);
                    return saveCredential(credential);
                })
                .orElseGet(() -> {
                    pCredential.setId(id);
                    return saveCredential(pCredential);
                });
    }

    private Credential updateCredential(Credential originalCredential, Credential updatedCredential) {
        //modify original from updated
        return updatedCredential;
    }

    public boolean deleteCredential(long id) {
        return credentialRepository.findById(id)
                .map(credential -> {
                    credential.setActive(false);
                    credentialRepository.save(credential);
                    return true;
                }).orElse(false);
    }

    private boolean isEncodedPassword(Credential credential){
        return credential.getPassword().startsWith("$2");
    }


    private void encodePassword(List<Credential> credentials){
        for(Credential credential: credentials){
            encodePassword(credential);
        }
    }

    public void encodePassword(Credential credential){
        if(!isEncodedPassword(credential)) {
            credential.setPassword(passwordEncoder.encode(credential.getPassword()));
        }
    }

}

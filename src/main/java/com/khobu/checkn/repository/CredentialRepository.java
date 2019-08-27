package com.khobu.checkn.repository;


import com.khobu.checkn.domain.Credential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CredentialRepository extends JpaRepository<Credential, Long> {

    @Query("SELECT c FROM Credential c WHERE c.username = ?1 and c.password = ?2")
    public Credential findCredentialByUsernameAndPassword(String username, String password);

    @Query("SELECT c FROM Credential c WHERE c.username = ?1")
    public Credential findCredentialByUsername(String username);
}

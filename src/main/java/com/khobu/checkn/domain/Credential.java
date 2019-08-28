package com.khobu.checkn.domain;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Credential {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String username;
    @Column
    private String password;
    @Column
    private LocalDate createdDate;
    @Column
    private LocalDate updatedDate;
    @Column
    private boolean active;

    @PrePersist
    protected void onCreate() {
        createdDate = LocalDate.now();
        active = true;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedDate = LocalDate.now();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isValid(){
        return this.username!=null && !this.username.trim().isEmpty() && this.password!=null && !this.password.trim().isEmpty();
    }
}

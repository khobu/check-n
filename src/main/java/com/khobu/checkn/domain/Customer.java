package com.khobu.checkn.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String firstName;
    @Column
    private String middleName;
    @Column
    private String lastName;
    @Column
    private String street;
    @Column
    private String city;
    @Column
    private String state;
    @Column
    private String zipCode;
    @Column
    private String phone;
    @Column
    private String email;
    @Column
    private LocalDate createdDate = LocalDate.now();
    @Column
    private LocalDate updatedDate = LocalDate.now();
    @Column
    private long updatedByEmployeeId;
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

    public Customer() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public long getUpdatedByEmployeeId() {
        return updatedByEmployeeId;
    }

    public void setUpdatedByEmployeeId(long updatedByEmployeeId) {
        this.updatedByEmployeeId = updatedByEmployeeId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}

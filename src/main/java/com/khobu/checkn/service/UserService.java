package com.khobu.checkn.service;


import com.khobu.checkn.domain.Employee;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private Employee user;

    public boolean hasUser() {
        return user != null;
    }

    public Employee getUser() {
        return user;
    }

    public long getUserId(){
        long returnValue = -1;
        if(hasUser()){
            returnValue = user.getId();
        }
        return returnValue;
    }

    public void setUser(Employee user) {
        this.user = user;
    }

    public void clearUser(){
        this.user = null;
    }
}

package com.khobu.checkn.service;


import com.khobu.checkn.domain.User;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

    private User user;

    public boolean hasUser() {
        return user != null;
    }

    public User getUser() {
        return user;
    }

    public long getUserId(){
        long returnValue = -1;
        if(hasUser()){
            returnValue = user.getId();
        }
        return returnValue;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void clearUser(){
        this.user = null;
    }
}

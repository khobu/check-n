package com.khobu.checkn.controller;


import com.khobu.checkn.domain.User;
import com.khobu.checkn.service.UserService;
import com.khobu.checkn.service.SessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping(path = "/api")
public class SessionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessionController.class);

    @Autowired
    private SessionService sessionService;

    @Autowired
    private UserService userService;

    @GetMapping("/user/username")
    public String currentUserName(Principal principal) {
        LOGGER.info("Getting current Username");
        printRoleInfo();
        return principal.getName();
    }

    @GetMapping("user/current-user")
    public User getCurrentUser(Principal principal){
        LOGGER.info("Getting current user");
        printRoleInfo();
        User user = null;
        if(sessionService.hasUser()){
            user = sessionService.getUser();
        } else if(principal.getName() != null && !principal.getName().trim().isEmpty()){
            List<User> userList = userService.findByUsername(principal.getName());
            if(userList.size() == 1){
                User tempUser = userList.get(0);
                if(tempUser.isActive()){
                    sessionService.setUser(tempUser);
                    user = tempUser;
                }
            }
        }

        return user;
    }


    private void printRoleInfo(){
         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Set<String> roles = authentication.getAuthorities().stream().map(r -> r.getAuthority()).collect(Collectors.toSet());

        boolean hasUserRole = authentication.getAuthorities().stream()
          .anyMatch(r -> r.getAuthority().equals("USER"));

        boolean hasAdminRole = authentication.getAuthorities().stream()
          .anyMatch(r -> r.getAuthority().equals("ADMIN"));

        for(String role: roles){
            System.out.println("Found Role: "+role);
        }

        System.out.println("Has Admin Role: "+hasAdminRole);
        System.out.println("Has User Role: "+hasUserRole);
    }
}
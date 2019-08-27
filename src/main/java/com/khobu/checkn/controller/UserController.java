package com.khobu.checkn.controller;


import com.khobu.checkn.domain.Employee;
import com.khobu.checkn.service.EmployeeService;
import com.khobu.checkn.service.UserService;
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
public class UserController{

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/user/username")
    public String currentUserName(Principal principal) {
        LOGGER.info("Getting current Username");
        printRoleInfo();
        return principal.getName();
    }

    @GetMapping("user/current-user")
    public Employee getCurrentUser(Principal principal){
        LOGGER.info("Getting current user");
        printRoleInfo();
        Employee employee = null;
        if(userService.hasUser()){
            employee = userService.getUser();
        } else if(principal.getName() != null && !principal.getName().trim().isEmpty()){
            List<Employee> employeeList = employeeService.findByUsername(principal.getName());
            if(employeeList.size() == 1){
                Employee tempEmployee = employeeList.get(0);
                if(tempEmployee.isActive()){
                    userService.setUser(tempEmployee);
                    employee = tempEmployee;
                }
            }
        }

        return employee;
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
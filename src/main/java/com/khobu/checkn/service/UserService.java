package com.khobu.checkn.service;


import com.khobu.checkn.domain.User;
import com.khobu.checkn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class UserService {

    @Autowired
	private UserRepository userRepository;

     @PostConstruct
    private void init(){

         User user = new User();
         user.setUsername("admin");
         user.setFirstName("Admin");
         user.setLastName("Admin");
         user.setActive(true);
         saveUser(user);
    }

    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }

    public User findUserById(long id){
        return userRepository.findById(id)
                .orElse(null);
			//.orElseThrow(() -> new Exception("User Not Found")); //UserNotFoundException(id)
    }

    public List<User> findByUsername(String username){
        return userRepository.findByUsername(username);

    }

    public User saveUser(User user){
        User returnValue = null;
        try{
            returnValue = userRepository.save(user);
        } catch (Exception ex){
        }
        return  returnValue;
    }

    public User updateUser(User pUser, long id){
        return userRepository.findById(id)
			.map(user -> {
				user = updateUser(user, pUser);
				return userRepository.save(user);
			})
			.orElseGet(() -> {
				pUser.setId(id);
				return userRepository.save(pUser);
			});
    }

    private User updateUser(User originalUser, User updatedUser){

        return updatedUser;
    }

    public boolean deleteUser(long id){
       return userRepository.findById(id)
			.map(user -> {
			    user.setActive(false);
				userRepository.save(user);
				return true;
			}).orElse(false);
    }
}

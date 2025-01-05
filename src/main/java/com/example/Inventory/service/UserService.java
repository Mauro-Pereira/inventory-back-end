package com.example.Inventory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Inventory.exception.UserAlreadyExistsException;
import com.example.Inventory.model.User;
import com.example.Inventory.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user){

        this.userRepository.findUserByEmail(user.getEmail())
            .ifPresent(userPresent -> {
                throw new UserAlreadyExistsException("User already exists");
            });
        
        return this.userRepository.save(user);
        
    }
    
    

}

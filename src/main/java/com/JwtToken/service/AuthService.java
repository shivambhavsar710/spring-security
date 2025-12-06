package com.JwtToken.service;

import com.JwtToken.model.Users;
import com.JwtToken.repository.UserRepository;
import com.JwtToken.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtUtil jwt;

    public String login(Users user) {
        Optional<Users> us = userRepository.findByUsername(user.getUsername());

        if(us.isPresent()){
            Users dbuser = us.get();
            if(passwordEncoder.matches(user.getPassword(), dbuser.getPassword())){
                UserDetails userDetails = new User(
                        dbuser.getUsername(),
                        dbuser.getPassword(),
                        Collections.singleton(new SimpleGrantedAuthority(dbuser.getRoles()))
                );
                return jwt.generateToken(userDetails);
            }
        }
        return "Invalid Credentials";
    }

    public String signUp(Users user){

        if(!"ROLE_USER".equals(user.getRoles()) && !"ROLE_ADMIN".equals(user.getRoles())){
            return "Please select a valid role";
        }

        Optional<Users> newUser = userRepository.findByUsername(user.getUsername());

        if(newUser.isPresent()){
            return "Already Exists";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "Created successfully";
    }

}
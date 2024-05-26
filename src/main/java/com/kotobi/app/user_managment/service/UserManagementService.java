package com.kotobi.app.user_managment.service;

import com.kotobi.app.user_managment.dto.RequestResponse;
import com.kotobi.app.user_managment.entity.User;
import com.kotobi.app.user_managment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@Service
public class UserManagementService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;



    public RequestResponse getUserByEmail(String email){
        RequestResponse response = new RequestResponse();
        try {
            User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User Not Found!"));
            response.setUser(user);
            response.setStatusCode(200);
            response.setMessage("User found : " + email);

        }catch (Exception e){
            response.setStatusCode(400);
            response.setMessage("User " + email + " Not Found");
        }
        return response;
    }

    public RequestResponse updateUser(UUID uuid , User updatedUser){
        RequestResponse response = new RequestResponse();

        try{
            // Get the authenticated user's details
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String authenticatedUserEmail = ((UserDetails) authentication.getPrincipal()).getUsername();

            // Find the authenticated user by email
            User authenticatedUser = this.getUserByEmail(authenticatedUserEmail).getUser();

            // Check if the authenticated user is trying to update their own details
            if (!authenticatedUser.getId().equals(uuid)) {
                response.setStatusCode(403); // Forbidden
                response.setMessage("You are not authorized to update another user's details.");
                return response;
            }
            Optional<User> userOptional = userRepository.findById(uuid);
            if(userOptional.isPresent()){

                User existingUser = userOptional.get();
                if(updatedUser.getEmail() != null){
                    existingUser.setEmail(updatedUser.getEmail());
                }
                if(updatedUser.getFirst_name() != null){
                    existingUser.setFirst_name(updatedUser.getFirst_name());
                }
                if(updatedUser.getLast_name() != null){
                    existingUser.setLast_name(updatedUser.getLast_name());
                }
                if(updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()){
                    existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
                }

                User savedUser = userRepository.save(existingUser);
                response.setUser(savedUser);
                response.setStatusCode(200);
                response.setMessage("User Updated !");


            }else{
                response.setStatusCode(404);
                response.setMessage("User " + uuid + " Not Found!");
            }

        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error occurred while updating user: " + e.getMessage());
        }
        return response;
    }






}

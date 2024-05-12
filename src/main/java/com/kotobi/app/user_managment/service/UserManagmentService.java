package com.kotobi.app.user_managment.service;

import com.kotobi.app.user_managment.dto.RequestResponse;
import com.kotobi.app.user_managment.entity.User;
import com.kotobi.app.user_managment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class UserManagmentService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public RequestResponse registerUser(RequestResponse registrationRequest){
        RequestResponse response = new RequestResponse();

        try {

            User user = new User();
            user.setEmail(registrationRequest.getEmail());
            user.setFirst_name(registrationRequest.getFirst_name());
            user.setLast_name(registrationRequest.getLast_name());
            user.setRole(registrationRequest.getRole());
            user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            User userResult = userRepository.save(user);
            if(userResult.getId() != null){
                response.setUser(userResult);
                response.setMessage("User Created Successfully!");
                response.setStatusCode(200);
            }

        }catch (Exception e){
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }
        return response;
    }


    public RequestResponse loginUser(RequestResponse loginRequest){
        RequestResponse response = new RequestResponse();

        try{

            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()));

            var user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow();
            var jwt = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(),user);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRefreshToken(refreshToken);
            response.setExpirationTime("24Hrs");
            response.setMessage("Logged User "+ user.getEmail() +" Successfully!");
        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    public RequestResponse refreshToken(RequestResponse refreshTokenRequest){
        RequestResponse response = new RequestResponse();

        try {
            String userEmail = jwtUtils.extractUsername(refreshTokenRequest.getToken());
            User user = userRepository.findByEmail(userEmail).orElseThrow();
            if(jwtUtils.isTokenValid(refreshTokenRequest.getToken(),user)){
                var jwt = jwtUtils.generateToken(user);
                response.setStatusCode(200);
                response.setToken(jwt);
                response.setMessage("Successfully Refreshed Token");
                response.setRefreshToken(refreshTokenRequest.getToken());
                response.setExpirationTime("24Hrs");
            }
        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }


    public RequestResponse getAllUsers(){
        RequestResponse response = new RequestResponse();

        try{
            List<User> resultUsers = userRepository.findAll();
            if(!resultUsers.isEmpty()){
                response.setUserList(resultUsers);
                response.setStatusCode(200);
                response.setMessage("OK");
            }else{
                response.setStatusCode(404);
                response.setMessage("No Users Found!");
            }

        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }


    public RequestResponse getUserById(UUID uuid){
        RequestResponse response = new RequestResponse();
        try {
            User user = userRepository.findById(uuid).orElseThrow(() -> new RuntimeException("User Not Found!"));
            response.setUser(user);
            response.setStatusCode(200);
            response.setMessage("User found : " + uuid);

        }catch (Exception e){
            response.setStatusCode(400);
            response.setMessage("User " + uuid + " Not Found");
        }
        return response;
    }



}

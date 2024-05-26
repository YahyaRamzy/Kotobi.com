package com.kotobi.app.user_managment.service;

import com.kotobi.app.user_managment.dto.RequestResponse;
import com.kotobi.app.user_managment.dto.SellerDto;
import com.kotobi.app.user_managment.entity.Role;
import com.kotobi.app.user_managment.entity.Seller;
import com.kotobi.app.user_managment.entity.User;
import com.kotobi.app.user_managment.repository.SellerRepository;
import com.kotobi.app.user_managment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserManagmentService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SellerRepository sellerRepository;
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
            if(userRepository.findByEmail(registrationRequest.getEmail()).isPresent()){
                response.setMessage("User Already Exists");
                response.setStatusCode(500);
            }else {
                user.setEmail(registrationRequest.getEmail());
                user.setFirst_name(registrationRequest.getFirst_name());
                user.setLast_name(registrationRequest.getLast_name());
                user.setRole(registrationRequest.getRole());
                user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
                User userResult = userRepository.save(user);
                if (userResult.getId() != null) {
                    response.setUser(userResult);
                    response.setMessage("User Created Successfully!");
                    response.setStatusCode(200);
                }
            }

        }catch (Exception e){
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }
        return response;
    }
    public RequestResponse registerSeller(SellerDto sellerDto) {
        RequestResponse response = new RequestResponse();

        try {
            if (userRepository.findByEmail(sellerDto.getEmail()).isPresent()) {
                response.setMessage("Seller Already Exists");
                response.setStatusCode(500);
            } else {
                User user = new User();
                user.setEmail(sellerDto.getEmail());
                user.setFirst_name(sellerDto.getFirstName());
                user.setLast_name(sellerDto.getLastName());
                user.setRole(Role.SELLER);
                user.setPassword(passwordEncoder.encode(sellerDto.getPassword()));

                Seller seller = createSeller(sellerDto, user);

                user.setSeller(seller);

                userRepository.save(user);
                sellerRepository.save(seller);

                response.setUser(user);
                response.setSellerDetails(sellerDto);
                response.setMessage("Seller Registered Successfully, Pending Approval!");
                response.setStatusCode(200);
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }
        return response;
    }

    private static Seller createSeller(SellerDto sellerDto, User user) {
        Seller seller = new Seller();
        seller.setBusinessName(sellerDto.getBusinessName());
        seller.setBusinessAddress(sellerDto.getBusinessAddress());
        seller.setBusinessPhoneNumber(sellerDto.getBusinessPhoneNumber());
        seller.setWebsiteUrl(sellerDto.getWebsiteUrl());
        seller.setTaxId(sellerDto.getTaxId());
        seller.setBusinessLicenseNumber(sellerDto.getBusinessLicenseNumber());
        seller.setApproved(false); // Default to false, requires admin approval
        seller.setUser(user);
        return seller;
    }

    public RequestResponse approveSeller(UUID sellerId){
        RequestResponse response = new RequestResponse();
        try {
            Seller seller = sellerRepository.findById(sellerId).orElseThrow(()-> new RuntimeException("Seller Not Found!"));
            seller.setApproved(true);
            sellerRepository.save(seller);
            response.setStatusCode(200);
            response.setMessage("Seller :  "+ seller.getBusinessName() +" ID : "+ sellerId + " approved !" );

        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    public RequestResponse rejectSeller(UUID sellerId){
        RequestResponse response = new RequestResponse();
        try {
            Seller seller = sellerRepository.findById(sellerId).orElseThrow(()-> new RuntimeException("Seller Not Found!"));
            User user = seller.getUser();
            sellerRepository.delete(seller);
            userRepository.delete(user);
            response.setStatusCode(200);
            response.setMessage("Seller :  "+ seller.getBusinessName() +" ID : "+ sellerId + " not approved DELETED !" );

        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
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

            // Find the user by email
            User seller = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));

            // Check if the user is a seller and not approved
            if (seller.getRole() == Role.SELLER && seller.getSeller() != null && !seller.getSeller().isApproved()) {
                response.setStatusCode(403); // Forbidden status
                response.setMessage("Seller not approved yet.");
                return response;
            }

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

    public RequestResponse deleteUser(UUID uuid){
        RequestResponse response = new RequestResponse();
        try {
            Optional<User> userOptional = userRepository.findById(uuid);
            if(userOptional.isPresent()){
                userRepository.deleteById(uuid);
                response.setStatusCode(200);
                response.setMessage("User Deleted !");
            }else{
                response.setStatusCode(404);
                response.setMessage("User " + uuid + " Not Found!");
            }
        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Could not Delete User" + e.getMessage());
        }
        return response;
    }




}

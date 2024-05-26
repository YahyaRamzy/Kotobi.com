package com.kotobi.app.user_managment.service;

import com.kotobi.app.user_managment.dto.RequestResponse;
import com.kotobi.app.user_managment.entity.Seller;
import com.kotobi.app.user_managment.entity.User;
import com.kotobi.app.user_managment.repository.SellerRepository;
import com.kotobi.app.user_managment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class SellerManagementService {
    @Autowired
    SellerRepository sellerRepository;
    @Autowired
    UserRepository userRepository;

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
    public RequestResponse getMySellerDetails(UUID uuid){
        RequestResponse response = new RequestResponse();

        try{
            // Get the authenticated user's details
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String authenticatedUserEmail = ((UserDetails) authentication.getPrincipal()).getUsername();

            // Find the authenticated user by email
            User authenticatedUser = this.getUserByEmail(authenticatedUserEmail).getUser();

            // Check if the authenticated user is trying to update their own details
            if (authenticatedUser.getSeller() == null || !authenticatedUser.getSeller().getId().equals(uuid)) {
                response.setStatusCode(403); // Forbidden
                System.out.println(authenticatedUser.getId());
                response.setMessage("You are not authorized to view another Seller's details.");
                return response;
            }
            Optional<Seller> sellerOptional = sellerRepository.findById(uuid);
            User user = new User();
            user.setId(authenticatedUser.getId());
            user.setEmail(authenticatedUser.getEmail());
            user.setFirst_name(authenticatedUser.getFirst_name());
            user.setLast_name(authenticatedUser.getLast_name());
            user.setSeller(authenticatedUser.getSeller());
            user.setRole(authenticatedUser.getRole());
            if(sellerOptional.isPresent()){
                response.setUser(user);
                response.setSellerDetailsforService(authenticatedUser.getSeller());
                response.setStatusCode(200);
                response.setMessage("User Retrieved!");
            }else{
                response.setStatusCode(500);
                response.setMessage("Error User is not present!");
            }

        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error occurred while retrieving user: " + e.getMessage());
        }
        return response;
    }
    public RequestResponse updateSeller(UUID uuid, Seller updatedSeller) {
        RequestResponse response = new RequestResponse();

        try {
            // Get the authenticated user's details
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String authenticatedUserEmail = ((UserDetails) authentication.getPrincipal()).getUsername();

            // Find the authenticated user by email
            User authenticatedUser = this.getUserByEmail(authenticatedUserEmail).getUser();

            // Check if the authenticated user is trying to update their own seller details
            if (authenticatedUser.getSeller() == null || !authenticatedUser.getSeller().getId().equals(uuid)) {
                response.setStatusCode(403); // Forbidden
                response.setMessage("You are not authorized to update another seller's details.");
                return response;
            }

            // Find the seller by ID
            Optional<Seller> sellerOptional = sellerRepository.findById(uuid);
            if (sellerOptional.isPresent()) {
                Seller existingSeller = sellerOptional.get();

                // Update seller-specific fields
                if (updatedSeller.getBusinessName() != null) {
                    existingSeller.setBusinessName(updatedSeller.getBusinessName());
                }
                if (updatedSeller.getBusinessAddress() != null) {
                    existingSeller.setBusinessAddress(updatedSeller.getBusinessAddress());
                }
                if (updatedSeller.getBusinessPhoneNumber() != null) {
                    existingSeller.setBusinessPhoneNumber(updatedSeller.getBusinessPhoneNumber());
                }
                if (updatedSeller.getWebsiteUrl() != null) {
                    existingSeller.setWebsiteUrl(updatedSeller.getWebsiteUrl());
                }
                if (updatedSeller.getTaxId() != null) {
                    existingSeller.setTaxId(updatedSeller.getTaxId());
                }
                if (updatedSeller.getBusinessLicenseNumber() != null) {
                    existingSeller.setBusinessLicenseNumber(updatedSeller.getBusinessLicenseNumber());
                }

                Seller savedSeller = sellerRepository.save(existingSeller);
                response.setUser(authenticatedUser); // Assuming response contains the user information
                response.setStatusCode(200);
                response.setMessage("Seller Updated!");
            } else {
                response.setStatusCode(404);
                response.setMessage("Seller " + uuid + " Not Found!");
            }

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error occurred while updating seller: " + e.getMessage());
        }
        return response;
    }
}

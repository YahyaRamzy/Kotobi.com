package com.kotobi.app.user_managment.service;

import com.kotobi.app.user_managment.dto.RequestResponse;
import com.kotobi.app.user_managment.entity.Seller;
import com.kotobi.app.user_managment.entity.User;
import com.kotobi.app.user_managment.repository.SellerRepository;
import com.kotobi.app.user_managment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AdminManagementService {

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    UserRepository userRepository;

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
}

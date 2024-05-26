package com.kotobi.app.user_managment.controller;

import com.kotobi.app.user_managment.dto.RequestResponse;
import com.kotobi.app.user_managment.entity.User;
import com.kotobi.app.user_managment.service.UserManagmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/admin/")
public class AdminManagementController {

    @Autowired
    private UserManagmentService userManagmentService;

    @GetMapping("users/get-all-users")
    public ResponseEntity<RequestResponse> getAllUsers(){
        return ResponseEntity.ok(userManagmentService.getAllUsers());
    }

    @GetMapping("users/{userId}")
    public ResponseEntity<RequestResponse> getAllUsers(@PathVariable UUID userId){
        return ResponseEntity.ok(userManagmentService.getUserById(userId));
    }

//    @PutMapping("users/{userId}")
//    public ResponseEntity<RequestResponse> updateUser(@PathVariable UUID userId , @RequestBody User user){
//        return ResponseEntity.ok(userManagmentService.updateUser(userId,user));
//    }

    @DeleteMapping("users/{userId}")
    public ResponseEntity<RequestResponse> deleteUser(@PathVariable UUID userId){
        return ResponseEntity.ok(userManagmentService.deleteUser(userId));
    }

    @PostMapping("seller/approve/{sellerId}")
    public ResponseEntity<RequestResponse> approveSeller(@PathVariable UUID sellerId){
        return ResponseEntity.ok(userManagmentService.approveSeller(sellerId));
    }
    @PostMapping("seller/reject/{sellerId}")
    public ResponseEntity<RequestResponse> rejectSeller(@PathVariable UUID sellerId){
        return ResponseEntity.ok(userManagmentService.rejectSeller(sellerId));
    }
}

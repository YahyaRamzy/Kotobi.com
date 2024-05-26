package com.kotobi.app.user_managment.controller;

import com.kotobi.app.user_managment.dto.RequestResponse;
import com.kotobi.app.user_managment.service.AdminManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/admin/")
public class AdminManagementController {


    @Autowired
    private AdminManagementService adminManagementService;

    @GetMapping("users/get-all-users")
    public ResponseEntity<RequestResponse> getAllUsers(){
        return ResponseEntity.ok(adminManagementService.getAllUsers());
    }

    @GetMapping("users/{userId}")
    public ResponseEntity<RequestResponse> getAllUsers(@PathVariable UUID userId){
        return ResponseEntity.ok(adminManagementService.getUserById(userId));
    }

    @DeleteMapping("users/{userId}")
    public ResponseEntity<RequestResponse> deleteUser(@PathVariable UUID userId){
        return ResponseEntity.ok(adminManagementService.deleteUser(userId));
    }

    @PostMapping("seller/approve/{sellerId}")
    public ResponseEntity<RequestResponse> approveSeller(@PathVariable UUID sellerId){
        return ResponseEntity.ok(adminManagementService.approveSeller(sellerId));
    }
    @PostMapping("seller/reject/{sellerId}")
    public ResponseEntity<RequestResponse> rejectSeller(@PathVariable UUID sellerId){
        return ResponseEntity.ok(adminManagementService.rejectSeller(sellerId));
    }
}

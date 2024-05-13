package com.kotobi.app.user_managment.controller;

import com.kotobi.app.user_managment.dto.RequestResponse;
import com.kotobi.app.user_managment.entity.User;
import com.kotobi.app.user_managment.service.UserManagmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class UserManagementController {

    @Autowired
    private UserManagmentService userManagmentService;


    @PostMapping("/auth/register")
    public ResponseEntity<RequestResponse> register(@RequestBody RequestResponse registerRequest){
        return ResponseEntity.ok(userManagmentService.registerUser(registerRequest));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<RequestResponse> login(@RequestBody RequestResponse loginRequest){
        return ResponseEntity.ok(userManagmentService.loginUser(loginRequest));
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<RequestResponse> refreshToken(@RequestBody RequestResponse refreshRequest){
        return ResponseEntity.ok(userManagmentService.refreshToken(refreshRequest));
    }

    @GetMapping("/admin/users/get-all-users")
    public ResponseEntity<RequestResponse> getAllUsers(){
        return ResponseEntity.ok(userManagmentService.getAllUsers());
    }

    @GetMapping("/admin/users/{userId}")
    public ResponseEntity<RequestResponse> getAllUsers(@PathVariable UUID userId){
        return ResponseEntity.ok(userManagmentService.getUserById(userId));
    }

    @PutMapping("/admin/users/{userId}")
    public ResponseEntity<RequestResponse> updateUser(@PathVariable UUID userId , @RequestBody User user){
        return ResponseEntity.ok(userManagmentService.updateUser(userId,user));
    }

    @DeleteMapping("/admin/users/{userId}")
    public ResponseEntity<RequestResponse> deleteUser(@PathVariable UUID userId){
        return ResponseEntity.ok(userManagmentService.deleteUser(userId));
    }


}

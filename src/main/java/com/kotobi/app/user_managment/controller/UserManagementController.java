package com.kotobi.app.user_managment.controller;

import com.kotobi.app.user_managment.dto.RequestResponse;
import com.kotobi.app.user_managment.entity.User;
import com.kotobi.app.user_managment.service.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserManagementController {

    @Autowired
    private UserManagementService userManagmentService;
    @PutMapping("/updateDetails/{userId}")
    public ResponseEntity<RequestResponse> updateUser(@PathVariable UUID userId, @RequestBody User user) {
        return ResponseEntity.ok(userManagmentService.updateUser(userId, user));
    }

    @GetMapping("/getMyDetails/{userId}")
    public ResponseEntity<RequestResponse> getMyDetails(@PathVariable UUID userId) {
        return ResponseEntity.ok(userManagmentService.getMyUserDetails(userId));
    }






}

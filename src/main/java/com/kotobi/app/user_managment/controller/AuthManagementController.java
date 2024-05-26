package com.kotobi.app.user_managment.controller;

import com.kotobi.app.user_managment.dto.RequestResponse;
import com.kotobi.app.user_managment.dto.SellerDto;
import com.kotobi.app.user_managment.service.UserManagmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/")
public class AuthManagementController {
    @Autowired
    private UserManagmentService userManagmentService;


    @PostMapping("register")
    public ResponseEntity<RequestResponse> register(@RequestBody RequestResponse registerRequest){
        return ResponseEntity.ok(userManagmentService.registerUser(registerRequest));
    }

    @PostMapping("registerSeller")
    public ResponseEntity<RequestResponse> registerSeller(@RequestBody SellerDto sellerDto){
        return ResponseEntity.ok(userManagmentService.registerSeller(sellerDto));
    }

    @PostMapping("login")
    public ResponseEntity<RequestResponse> login(@RequestBody RequestResponse loginRequest){
        return ResponseEntity.ok(userManagmentService.loginUser(loginRequest));
    }

    @PostMapping("refresh")
    public ResponseEntity<RequestResponse> refreshToken(@RequestBody RequestResponse refreshRequest){
        return ResponseEntity.ok(userManagmentService.refreshToken(refreshRequest));
    }
}

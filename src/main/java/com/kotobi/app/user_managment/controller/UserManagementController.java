package com.kotobi.app.user_managment.controller;

import com.kotobi.app.user_managment.dto.RequestResponse;
import com.kotobi.app.user_managment.dto.SellerDto;
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






}

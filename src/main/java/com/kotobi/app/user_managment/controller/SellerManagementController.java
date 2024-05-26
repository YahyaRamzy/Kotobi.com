package com.kotobi.app.user_managment.controller;

import com.kotobi.app.user_managment.dto.RequestResponse;
import com.kotobi.app.user_managment.dto.SellerDto;
import com.kotobi.app.user_managment.entity.Seller;
import com.kotobi.app.user_managment.service.SellerManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/seller")
public class SellerManagementController {
    @Autowired
    private SellerManagementService sellerManagementService;
    @PutMapping("/updateDetails/{sellerID}")
    public ResponseEntity<RequestResponse> updateSeller(@PathVariable UUID sellerID, @RequestBody SellerDto sellerDto) {
        return ResponseEntity.ok(sellerManagementService.updateSeller(sellerID, sellerDto));
    }
    @GetMapping("/getMyDetails/{sellerId}")
    public ResponseEntity<RequestResponse> getMyDetails(@PathVariable UUID sellerId) {
        return ResponseEntity.ok(sellerManagementService.getMySellerDetails(sellerId));
    }
}

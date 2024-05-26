package com.kotobi.app.user_managment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kotobi.app.user_managment.entity.Role;
import com.kotobi.app.user_managment.entity.Seller;
import com.kotobi.app.user_managment.entity.User;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestResponse {

    private int statusCode;
    private String error;
    private String message;
    private String token;
    private String refreshToken;
    private String expirationTime;
    private String first_name;
    private String last_name;
    private String email;
    private String password;
    private Role role;
    private User user;
    private List<User> UserList;

    // Seller-specific fields
    private String businessName;
    private String businessAddress;
    private String businessPhoneNumber;
    private String websiteUrl;
    private String taxId;
    private String businessLicenseNumber;
    private boolean approved;

    // Add a method to set seller-specific fields
    public void setSellerDetails(SellerDto sellerDto) {
        this.businessName = sellerDto.getBusinessName();
        this.businessAddress = sellerDto.getBusinessAddress();
        this.businessPhoneNumber = sellerDto.getBusinessPhoneNumber();
        this.websiteUrl = sellerDto.getWebsiteUrl();
        this.taxId = sellerDto.getTaxId();
        this.businessLicenseNumber = sellerDto.getBusinessLicenseNumber();
        this.approved = sellerDto.isApproved();
    }

    public void setSellerDetailsforService(Seller seller){
        this.businessName = seller.getBusinessName();
        this.businessAddress = seller.getBusinessAddress();
        this.businessPhoneNumber = seller.getBusinessPhoneNumber();
        this.websiteUrl = seller.getWebsiteUrl();
        this.taxId = seller.getTaxId();
        this.businessLicenseNumber = seller.getBusinessLicenseNumber();
        this.approved = seller.isApproved();
    }

}

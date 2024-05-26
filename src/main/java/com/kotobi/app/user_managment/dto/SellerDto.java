package com.kotobi.app.user_managment.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SellerDto {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String businessName;
    private String businessAddress;
    private String businessPhoneNumber;
    private String websiteUrl;
    private String taxId;
    private String businessLicenseNumber;
    private LocalDate registeredDate;
    private boolean approved;
}

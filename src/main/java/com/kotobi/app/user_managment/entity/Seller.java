package com.kotobi.app.user_managment.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String businessName;
    private String businessAddress;
    private String businessPhoneNumber;
    private String websiteUrl;
    private String taxId;
    private String businessLicenseNumber;
    private LocalDate registeredDate;
    private boolean approved;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
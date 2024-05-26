package com.kotobi.app.user_managment.dto;

import com.kotobi.app.user_managment.entity.Seller;
import lombok.Data;

import java.util.UUID;

@Data
public class UserDto {
    private UUID id;
    private String email;
    private String firstName;
    private String lastName;
    private Seller seller;
}

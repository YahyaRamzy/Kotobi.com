package com.kotobi.app.user_managment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kotobi.app.user_managment.entity.Role;
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

}

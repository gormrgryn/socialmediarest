package com.gor.socialmediarest.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CreateUserRequest {
    private String name;
    private String username;
    private String password;
}

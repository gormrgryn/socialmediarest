package com.gor.socialmediarest.requests;

import lombok.Data;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Data
public class UpdateUserRequest {
    private String name;
    private String username;
}

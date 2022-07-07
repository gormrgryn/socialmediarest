package com.gor.socialmediarest.requests;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsernameAndPasswordAuthenticationRequest {
    private String username;
    private String password;
}

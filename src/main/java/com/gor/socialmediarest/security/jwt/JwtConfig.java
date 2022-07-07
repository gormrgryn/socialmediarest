package com.gor.socialmediarest.security.jwt;

import com.google.common.net.HttpHeaders;

import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@ConfigurationProperties(prefix = "application.jwt")
@Getter
@Setter
@NoArgsConstructor
@Component
public class JwtConfig {
    private String secretKey;
    private String tokenPrefix;
    private Integer tokenExpirationAfterDays;

    public String getAuthorizationHeader() {
        return HttpHeaders.AUTHORIZATION;
    }
}

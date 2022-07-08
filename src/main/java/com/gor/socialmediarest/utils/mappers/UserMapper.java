package com.gor.socialmediarest.utils.mappers;

import com.gor.socialmediarest.db.entities.UserEntity;
import com.gor.socialmediarest.dto.UserDto;
import com.gor.socialmediarest.requests.CreateUserRequest;
import com.gor.socialmediarest.utils.LocalDateFormatter;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public PasswordEncoder passwordEncoder;
    public LocalDateFormatter localDateFormatter;

    @Autowired
    public UserMapper(PasswordEncoder passwordEncoder, LocalDateFormatter localDateFormatter) {
        this.passwordEncoder = passwordEncoder;
        this.localDateFormatter = localDateFormatter;
    }

    public UserDto toDto(UserEntity user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getUsername()
        );
    }

    public UserEntity toEntity(CreateUserRequest request) {
        return new UserEntity(
                request.getName(),
                request.getUsername(),
                passwordEncoder.encode(request.getPassword())
        );
    }
}

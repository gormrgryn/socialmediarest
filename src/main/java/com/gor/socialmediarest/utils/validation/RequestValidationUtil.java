package com.gor.socialmediarest.utils.validation;

import com.gor.socialmediarest.requests.*;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class RequestValidationUtil {

    @Autowired
    private StringValidationUtil stringValidationUtil;

    public boolean isRegisterUserRequestValid(CreateUserRequest request) {
        return stringValidationUtil.isUsernameValid(request.getUsername())
                && stringValidationUtil.isPhoneValid(request.getName())
                && stringValidationUtil.isPasswordValid(request.getPassword());
    }

    public boolean isUpdateUserRequestValid(UpdateUserRequest request) {
        return stringValidationUtil.isUsernameValid(request.getUsername())
                && stringValidationUtil.isPhoneValid(request.getName());
    }
}

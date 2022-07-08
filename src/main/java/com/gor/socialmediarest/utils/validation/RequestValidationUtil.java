package com.gor.socialmediarest.utils.validation;

import com.gor.socialmediarest.requests.*;
import com.gor.socialmediarest.utils.exceptions.InvalidNameException;
import com.gor.socialmediarest.utils.exceptions.InvalidPasswordException;
import com.gor.socialmediarest.utils.exceptions.InvalidUsernameException;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class RequestValidationUtil {

    @Autowired
    private StringValidationUtil stringValidationUtil;

    public void checkRegisterUserRequestValidation(CreateUserRequest request) throws InvalidUsernameException, InvalidNameException, InvalidPasswordException {
        if (!stringValidationUtil.isUsernameValid(request.getUsername()))
            throw new InvalidUsernameException();
        if (!stringValidationUtil.isNameValid(request.getName())) 
            throw new InvalidNameException();
        if (!stringValidationUtil.isPasswordValid(request.getPassword())) 
            throw new InvalidPasswordException();
    }

    public void checkUpdateUserRequestValidation(UpdateUserRequest request) throws InvalidUsernameException, InvalidNameException {
        if (!stringValidationUtil.isUsernameValid(request.getUsername()))
            throw new InvalidUsernameException();
        if (!stringValidationUtil.isNameValid(request.getName())) 
            throw new InvalidNameException();
    }
}

package com.gor.socialmediarest.utils.validation;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import com.google.common.base.Strings;

@Component
public class StringValidationUtil {

    private final Pattern usernamePattern;
    private final Pattern namePattern;
    private final Pattern phonePattern;
    private final Pattern emailPattern;
    private final Pattern licensePattern;

    public StringValidationUtil() {
        usernamePattern = Pattern.compile("^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$");
        namePattern = Pattern.compile("^[\\p{L}'][ \\p{L}'-]*[\\p{L}]$");
        phonePattern = Pattern.compile("^[+]{0,1}[0-9]{1,4}[0-9]{4,8}$");
        emailPattern = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
        licensePattern = Pattern.compile("^(?=.*?\\d)(?=.*?[a-zA-Z])[a-zA-Z\\d]+");
    }

    public boolean isUsernameValid(String username) {
        return !Strings.isNullOrEmpty(username) && usernamePattern.matcher(username).matches();
    }

    public boolean isNameValid(String name) {
        return !Strings.isNullOrEmpty(name) && namePattern.matcher(name).matches();
    }

    public boolean isPhoneValid(String phone) {
        return !Strings.isNullOrEmpty(phone) && phonePattern.matcher(phone).matches();
    }

    public boolean isEmailValid(String email) {
        return !Strings.isNullOrEmpty(email) && emailPattern.matcher(email).matches();
    }

    public boolean isLicenseValid(String license) {
        return !Strings.isNullOrEmpty(license) && licensePattern.matcher(license).matches();
    }

    public boolean isPasswordValid(String password) {
        return !Strings.isNullOrEmpty(password);
    }
}

package com.gor.socialmediarest.utils.validation;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import com.google.common.base.Strings;

@Component
public class StringValidationUtil {

    private final Pattern usernamePattern;
    private final Pattern namePattern;

    public StringValidationUtil() {
        usernamePattern = Pattern.compile("^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$");
        namePattern = Pattern.compile("^[\\p{L}'][ \\p{L}'-]*[\\p{L}]$");
    }

    public boolean isUsernameValid(String username) {
        return !Strings.isNullOrEmpty(username) && usernamePattern.matcher(username).matches();
    }

    public boolean isNameValid(String name) {
        return !Strings.isNullOrEmpty(name) && namePattern.matcher(name).matches();
    }

    public boolean isPasswordValid(String password) {
        return !Strings.isNullOrEmpty(password);
    }
}

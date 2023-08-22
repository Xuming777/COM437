package com.cool.weather;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;
import java.util.regex.Pattern;

public class InputUtils {
    private static Pattern userNamePattern;

    public static String checkInput(TextInputLayout textInputLayout, int minLength, int maxLength, String errorMsg) {
        String inputContent = Objects.requireNonNull(textInputLayout.getEditText()).getText().toString();
        if (inputContent.length() < minLength || inputContent.length() > maxLength) {
            ToastUtils.showToast(errorMsg);
            return null;
        }
        return inputContent;
    }

    public static String checkUserName(TextInputLayout textInputLayout) {
        String userName = InputUtils.checkInput(textInputLayout, 6,
                20, "The length of the username should be 6-20 digits!");
        if (userName == null) {
            return null;
        }
        if (userNamePattern == null) {
            userNamePattern = Pattern.compile("^[0-9]*$");
        }
        if (userNamePattern.matcher(userName).find()) {
            ToastUtils.showToast("Username cannot be numeric only!");
            return null;
        }
        return userName;

    }
}

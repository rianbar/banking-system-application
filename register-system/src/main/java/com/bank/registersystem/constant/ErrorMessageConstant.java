package com.bank.registersystem.constant;

public class ErrorMessageConstant {

    public static final String USER_NOT_FOUND_MESSAGE = "User not found in database";
    public static final String INVALID_IDENTITY_MESSAGE = "CPF or CNPJ already exists in database";
    public static final String INVALID_EMAIL_MESSAGE = "Email already exists in database";
    public static final String INVALID_LOGIN_MESSAGE = "Email or password is wrong";

    private ErrorMessageConstant() {}
}

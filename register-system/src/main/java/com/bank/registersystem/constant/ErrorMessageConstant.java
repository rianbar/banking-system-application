package com.bank.registersystem.constant;

public class ErrorMessageConstant {

    public static final String USER_NOT_FOUND_MESSAGE = "User not found in database";
    public static final String INVALID_IDENTITY_MESSAGE = "CPF or CNPJ already exists in database";
    public static final String INVALID_EMAIL_MESSAGE = "Email already exists in database";
    public static final String INVALID_LOGIN_MESSAGE = "something is wrong with your login, please review it and try again";
    public static final String UNAUTHORIZED_USER_MESSAGE = "you aren't authorized to access this page";


    private ErrorMessageConstant() {}
}

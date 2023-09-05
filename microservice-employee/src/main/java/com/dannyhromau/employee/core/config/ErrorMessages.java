package com.dannyhromau.employee.core.config;

public enum ErrorMessages {
    INCORRECT_DATA_MESSAGE("Incorrect or duplicated data"),
    CREATING_DATA_ERROR_MESSAGE("Unable to create database entry"),
    UPDATING_DATA_ERROR_MESSAGE("Unable to update database entry"),
    DELETING_DATA_ERROR_MESSAGE("Deleting data is failed"),
    ENTITY_NOT_FOUND_MESSAGE("Entity with id : %s not exists"),
    EMPTY_USERNAME_MESSAGE("Username field is empty"),
    WRONG_PASSWORD_MESSAGE("Wrong password format"),
    WRONG_EMAIL_MESSAGE("Wrong email format"),
    WRONG_AUTHENTICATION_MESSAGE("Cannot authorize : check input data or unblock the user"),
    WRONG_REFRESH_TOKEN_MESSAGE("Wrong refresh token");
    public final String label;

    ErrorMessages(String label) {
        this.label = label;
    }
}

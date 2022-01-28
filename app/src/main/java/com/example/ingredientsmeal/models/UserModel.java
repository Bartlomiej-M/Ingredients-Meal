package com.example.ingredientsmeal.models;

public class UserModel {
    public String login, email, number;

    public UserModel() {

    }

    public UserModel(String login, String email, String number) {
        this.login = login;
        this.email = email;
        this.number = number;
    }
}

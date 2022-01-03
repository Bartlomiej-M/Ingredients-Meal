package com.example.ingredientsmeal.models;

public class UserModel {
    public String login, email, number, uid;

    public UserModel() {

    }

    public UserModel(String login, String email, String number, String uid) {
        this.login = login;
        this.email = email;
        this.number = number;
        this.uid = uid;
    }
}

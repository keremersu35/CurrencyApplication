package com.example.currencyapplication.Model;

public class User {

    public String email;
    public String uId;
    public String userName;
    public boolean isAuth;

    public User(String email, String uId, String userName, boolean isAuth){
        this.email = email;
        this.uId = uId;
        this.userName = userName;
        this.isAuth = isAuth;
    }

    public User(String email, String uId, boolean isAuth){
        this.email = email;
        this.uId = uId;
        this.isAuth = isAuth;
    }
}

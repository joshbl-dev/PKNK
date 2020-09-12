package com.hackathon.quackhacks;

public class UserAccount {
    private String email;
    private String username;
    private String password;

    public void UserAccount(String email, String username, String password)
    {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public void changeUsername(String newName)
    {
        this.username = newName;
    }

    public void changePassword(String newPass)
    {
        this.password = newPass;
    }

    public void changeEmail(String newEmail)
    {
        this.email = newEmail;
    }
}

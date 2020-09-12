package com.hackathon.quackhacks.backend;

import com.google.firebase.database.IgnoreExtraProperties;
import com.hackathon.quackhacks.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@IgnoreExtraProperties
public class UserAccount {
    public String email;
    private String username;
    public String password;

    public List<UserAccount> friends = new ArrayList<>();

    public Map<String, Recipe> recipes = new HashMap<>();

    public UserAccount() {
    }

    public UserAccount(MainActivity activity, String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;

        activity.getDatabase().setValue("users", username, this);
    }

    public void changeUsername(String newName) {
        this.username = newName;
    }

    public void changePassword(String newPass) {
        this.password = newPass;
    }

    public void changeEmail(String newEmail) {
        this.email = newEmail;
    }
}

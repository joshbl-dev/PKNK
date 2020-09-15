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

    public List<String> friends = new ArrayList<>();
    public List<String> friendRequests = new ArrayList<>();
    public List<String> friendsPending = new ArrayList<>();

    public Map<String, Recipe> recipes = new HashMap<>();

    // Used for Google Firebase
    public UserAccount() {
    }

    public Map<String, Recipe> getRecipes() {
        return recipes;
    }

    public UserAccount(MainActivity activity, String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;

        Database database = activity.getDatabase();
        database.setValue("users", username, this);

    }

    public String getUsername() {
        return username;
    }

    public List<String> getFriendsPending() {
        return friendsPending;
    }

    public List<String> getFriendRequests() {
        return friendRequests;
    }

    public void addFriend(String friend) {
        friends.add(friend);
    }

    public void addRecipe(String name, Recipe recipe) {
        recipes.put(name, recipe);
    }

    public Recipe getRecipe(String name) {
        return recipes.get(name);
    }

    public List<String> getFriends() {
        return friends;
    }
}

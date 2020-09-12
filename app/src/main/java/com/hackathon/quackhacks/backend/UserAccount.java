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

    public Map<String, Recipe> recipes = new HashMap<>();

    public UserAccount() {
    }

    public UserAccount(MainActivity activity, String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;

        friends.add("Fake friend");
        recipes.put("pizza?", new Recipe());

        activity.getDatabase().setValue("users", username, this);
    }

    public String getUsername() {
        return username;
    }

    public void changePassword(String newPass) {
        this.password = newPass;
    }

    public void changeEmail(String newEmail) {
        this.email = newEmail;
    }

    public void addFriend(String friend) {
        friends.add(friend);
    }

    public void addRecipe(MainActivity activity, String name, Recipe recipe) {
        recipes.put(name, recipe);
    }

    public void adjustRecipe(MainActivity activity, String recipeName, String ingredient, int quan, String unit) {
        recipes.get(recipeName).addIngredient(ingredient, quan, unit);
        activity.getDatabase().setValue(recipes, "users", username, "recipes");
    }

    public List<String> getFriends() {
        return friends;
    }
}

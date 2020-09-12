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

    public Map<String, Recipe> getRecipes() {
        return recipes;
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

    public void addFriend(String friend) {
        friends.add(friend);
    }

    public void addRecipe(String name, Recipe recipe) {
        recipes.put(name, recipe);
    }

    public void adjustRecipe(MainActivity activity, String recipeName, String ingredient, int quan, String unit) {
        Recipe recipe = recipes.get(recipeName);
        if (recipe != null) {
            recipe.addIngredient(ingredient, quan, unit);
            activity.getDatabase().setValue(recipes, "users", username, "recipes");
        }
    }

    public void addResDesc(MainActivity activity, String recipeName, String desc, String inst) {
        Recipe recipe = recipes.get(recipeName);
        if (recipe != null) {
            recipe.addDesc(desc, inst);
            activity.getDatabase().setValue(recipes, "user", username, "recipes");
        }
    }

    public void removeIng(MainActivity activity, String recipeName, String ingredient) {
        Recipe recipe = recipes.get(recipeName);
        if (recipe != null) {
            recipe.removeIngredient(ingredient);
            activity.getDatabase().setValue(recipes, "users", username, "recipes");
        }

    }

    public List<String> getFriends() {
        return friends;
    }
}

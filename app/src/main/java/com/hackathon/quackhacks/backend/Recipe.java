package com.hackathon.quackhacks.backend;

import com.google.firebase.database.IgnoreExtraProperties;
import com.hackathon.quackhacks.MainActivity;

import java.util.ArrayList;
import java.util.List;

@IgnoreExtraProperties
public class Recipe implements Comparable<Recipe> {

    public String title;
    public List<String> ingredients = new ArrayList<>();
    public List<String> units = new ArrayList<>();
    public List<Integer> quantities = new ArrayList<>();
    public String description = "None yet";
    public String instructions = "None yet";
    public String nationality;
    public String type; // This represents whether it is a desert, dinner, breakfast, etc.

    public long timestamp;

    public Recipe() {
        title = "default";
        timestamp = System.currentTimeMillis();
    }

    public Recipe(MainActivity activity, String title, String nationality, String type) {
        this.title = title;
        this.nationality = nationality;
        this.type = type;
        timestamp = System.currentTimeMillis();
    }

    public void addIngredient(String ingredient, int quantity, String unit) {
        ingredients.add(ingredient);
        quantities.add(quantity);
        units.add(unit);
    }

    public void removeIngredient(String ingredient)
    {
        int a = ingredients.indexOf(ingredient);
        ingredients.remove(a);
        quantities.remove(a);
        units.remove(a);
    }

    public void addDesc(String desc, String inst)
    {
        description = desc;
        instructions = inst;
    }

    @Override
    public int compareTo(Recipe r) {
        return Long.compare(timestamp, r.timestamp);
    }

    public long getTimestamp() {
        return timestamp;
    }
}

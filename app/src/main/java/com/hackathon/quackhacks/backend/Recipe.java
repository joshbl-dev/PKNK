package com.hackathon.quackhacks.backend;

import com.google.firebase.database.IgnoreExtraProperties;
import com.hackathon.quackhacks.MainActivity;

import java.util.ArrayList;

@IgnoreExtraProperties
public class Recipe implements Comparable<Recipe> {

    public String title;
    public ArrayList<String> ingredients;
    public ArrayList<String> units;
    public ArrayList<Integer> quantities;
    public String description;
    public String nationality;
    public String type; // This represents whether it is a desert, dinner, breakfast, etc.

    public long timestamp;

    public Recipe() {
    }

    public Recipe(MainActivity activity, String title, String description, String nationality, String type) {
        this.title = title;
        this.description = description;
        this.nationality = nationality;
        this.type = type;

        timestamp = System.currentTimeMillis();

    }

    public void addIngredient(String ingredient, int quantity, String unit) {
        ingredients.add(ingredient);
        quantities.add(quantity);
        units.add(unit);
    }

    @Override
    public int compareTo(Recipe r) {
        return Long.compare(timestamp, r.timestamp);
    }

    public long getTimestamp() {
        return timestamp;
    }
}

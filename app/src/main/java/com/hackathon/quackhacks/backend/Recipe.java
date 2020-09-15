package com.hackathon.quackhacks.backend;

import android.util.Pair;

import androidx.annotation.NonNull;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// Class to represent a recipe
@IgnoreExtraProperties
public class Recipe implements Comparable<Recipe> {
    public String title;

    public Map<String, Pair<Integer, String>> ingredients = new LinkedHashMap<>();

    public String description = "None yet";
    public String instructions = "None yet";
    public String nationality;
    public String type; // This represents whether it is a desert, dinner, breakfast, etc.

    public long timestamp;

    // Used for Google Firebase
    public Recipe() {
        title = "default";
        timestamp = System.currentTimeMillis();
    }

    public Recipe(String title, String nationality, String type) {
        this.title = title;
        this.nationality = nationality;
        this.type = type;
        timestamp = System.currentTimeMillis();
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getIngredients() {
        return new ArrayList<>(ingredients.keySet());
    }

    public String getInstructions() {
        return instructions;
    }

    public List<Integer> getQuantities() {
        List<Integer> quantities = new ArrayList<>();
        for (Pair<Integer, String> value : ingredients.values()) {
            quantities.add(value.first);
        }
        return quantities;
    }

    public List<String> getUnits() {
        List<String> units = new ArrayList<>();
        for (Pair<Integer, String> value : ingredients.values()) {
            units.add(value.second);
        }
        return units;
    }

    public void addIngredient(Database database, String username, String ingredient, int quantity, String unit) {
        ingredients.put(ingredient, new Pair<>(quantity, unit));
        database.setValue(this, "users", username, "recipes", title);
    }

    public void removeIngredient(Database database, String username, String ingredient) {
        ingredients.remove(ingredient);
        database.setValue(this, "users", username, "recipes", title);
    }


    // toString method for sharing a recipe
    @NonNull
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Checkout my newest recipe on PKNK: ").append(title).append("\n\n");
        stringBuilder.append(nationality).append(" | ").append(type).append("\n\n");
        stringBuilder.append("");

        int count = 0;
        for (Map.Entry<String, Pair<Integer, String>> ingredient : ingredients.entrySet()) {
            Pair<Integer, String> ingredientsData = ingredient.getValue();
            stringBuilder.append(++count).append(". ").append(ingredient.getKey()).append(" [Amt: ").append(ingredientsData.first).append(";").append(ingredientsData.second).append("]\n");
        }

        stringBuilder.append("\nDirections:\n");
        stringBuilder.append(description);
        return stringBuilder.toString();
    }

    @Override
    public int compareTo(Recipe r) {
        return Long.compare(r.timestamp, timestamp);
    }
}

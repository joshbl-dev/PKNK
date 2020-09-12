package com.hackathon.quackhacks.backend;

import androidx.annotation.NonNull;

import com.google.firebase.database.IgnoreExtraProperties;

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

    public Recipe(String title, String nationality, String type) {
        this.title = title;
        this.nationality = nationality;
        this.type = type;
        timestamp = System.currentTimeMillis();
    }

    public String getTitle() {
        return title;
    }


    public void addDesc(String desc, String inst) {
        description = desc;
        instructions = inst;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public List<Integer> getQuantity() {
        return quantities;
    }

    public List<String> getUnits() {
        return units;
    }

    public void addIngredient(String ingredient, int quantity, String unit) {
        ingredients.add(ingredient);
        quantities.add(quantity);
        units.add(unit);
    }

    public void removeIngredient(String ingredient) {
        int index = ingredients.indexOf(ingredient);
        if (index > 0) {
            ingredients.remove(index);
            quantities.remove(index);
            units.remove(index);
        }
    }


    @NonNull
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Checkout my newest recipe on PKNK: ").append(title).append("\n\n");
        stringBuilder.append(nationality).append(" | ").append(type).append("\n\n");
        stringBuilder.append("");

        for (int i = 0; i < ingredients.size(); i++) {
            stringBuilder.append((i + 1)).append(". ").append(ingredients.get(i)).append(" [Amt: ").append(quantities.get(i)).append(";").append(units.get(i)).append("]\n");
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

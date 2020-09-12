package com.hackathon.quackhacks;

import java.util.ArrayList;

public class Recipe {
    private String title;
    private ArrayList<String> ingredients;
    private ArrayList<Integer> quantities;
    private String description;
    private String nationality;
    private String type; // This represents whether it is a desert, dinner, breakfast, etc.

    public void Recipe(String title, String description, String nationality, String type)
    {
        this.title = title;
        this.description = description;
        this.nationality = nationality;
        this.type = type;
    }

    public void addIngredient(String ingredient, int quantity)
    {
        ingredients.add(ingredient);
        quantities.add(quantity);
    }

}

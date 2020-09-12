package com.hackathon.quackhacks.backend;

import com.google.firebase.database.IgnoreExtraProperties;
import com.hackathon.quackhacks.MainActivity;

import java.util.ArrayList;
import java.util.List;

@IgnoreExtraProperties
public class Recipe implements Comparable<Recipe> {

    public String title;
    public List<String> ingredients = new ArrayList<String>();
    public List<String> units = new ArrayList<String>();
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

    public String getTitle()
    {
        return title;
    }



    public void addDesc(String desc, String inst)
    {
        description = desc;
        instructions = inst;
    }

    public String getDescription()
    {
        return description;
    }

    public String getInstructions()
    {
        return instructions;
    }

    public List<String> getIngredients()
    {
        List<String> ingList = new ArrayList<String>();
        for(int i = 0; i < ingredients.size(); i++)
        {
            ingList.add(ingredients.get(i));
        }
        return ingList;
    }

    public List<Integer> getQuantity()
    {
        List<Integer> ingList = new ArrayList<Integer>();
        for(int i = 0; i < quantities.size(); i++)
        {
            ingList.add(quantities.get(i));
        }
        return ingList;
    }

    public List<String> getUnits()
    {
        List<String> ingList = new ArrayList<String>();
        for(int i = 0; i < units.size(); i++)
        {
            ingList.add(units.get(i));
        }
        return ingList;
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



    @Override
    public int compareTo(Recipe r) {
        return Long.compare(timestamp, r.timestamp);
    }

    public long getTimestamp() {
        return timestamp;
    }
}

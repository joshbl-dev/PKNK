package com.hackathon.quackhacks.backend;

import java.io.Serializable;

public class IngredientPair implements Serializable {
    private Integer quantity;
    private String unit;

    public IngredientPair() {
    }

    public IngredientPair(Integer quantity, String unit) {
        this.quantity = quantity;
        this.unit = unit;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }
}

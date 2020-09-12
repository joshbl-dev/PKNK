package com.hackathon.quackhacks.views;

import android.content.Context;

import com.hackathon.quackhacks.R;

import static java.lang.Integer.parseInt;

public class AdjustRecipe extends BaseView {
    public AdjustRecipe(Context context, String recipeName) {
        super(context);
        activity.setContentView(R.layout.adjust_recipe);

        activity.findViewById(R.id.button4).setOnClickListener( onclick -> {
            String ingredient = activity.findViewById(R.id.editTextTextPersonName2).toString();
            int quantity = parseInt(activity.findViewById(R.id.editTextNumber).toString());
            String unit = activity.findViewById(R.id.spinner).toString();

            activity.getProfile().adjustRecipe(recipeName, ingredient, quantity, unit);
        });
    }
}

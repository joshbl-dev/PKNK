package com.hackathon.quackhacks.views;

import android.content.Context;
import android.widget.EditText;

import com.hackathon.quackhacks.R;

import static java.lang.Integer.parseInt;

public class AdjustRecipe extends BaseView {
    public AdjustRecipe(Context context, String recipeName) {
        super(context);
        activity.setContentView(R.layout.adjust_recipe);

        activity.findViewById(R.id.button4).setOnClickListener( onclick -> {
            EditText ingredient = activity.findViewById(R.id.editTextTextPersonName2);
            EditText quantity = activity.findViewById(R.id.editTextNumber);
            EditText unit = activity.findViewById(R.id.unit);

            if(ingredient.getText().toString().isEmpty())
            {
                ingredient.setError("Please enter an ingredient");
            }
            if(quantity.getText().toString().isEmpty())
            {
                quantity.setError("Please enter a quantity");
            }
            if(unit.getText().toString().isEmpty())
            {
                unit.setError("Please enter a unit");
            }
            

            activity.getProfile().adjustRecipe(activity, recipeName, ingredient.getText().toString(), parseInt(quantity.getText().toString()), unit.getText().toString());
        });
    }
}

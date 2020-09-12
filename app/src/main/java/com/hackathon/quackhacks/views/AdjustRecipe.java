package com.hackathon.quackhacks.views;

import android.content.Context;
import android.widget.EditText;
import android.widget.TextView;

import com.hackathon.quackhacks.R;

import static java.lang.Integer.parseInt;

public class AdjustRecipe extends BaseView {
    public AdjustRecipe(Context context, String recipeName) {
        super(context);
        activity.setContentView(R.layout.adjust_recipe);

        TextView title = activity.findViewById(R.id.editTextTextPersonName);
        title.setText(recipeName);

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

        activity.findViewById(R.id.button3).setOnClickListener( onclick ->{
            activity.changeView(new FeedView(context));
        });

        activity.findViewById(R.id.button5).setOnClickListener( onclick ->{
            EditText instructions = activity.findViewById(R.id.editTextTextPersonName5);
            EditText description = activity.findViewById(R.id.editTextTextPersonName3);

            if(instructions.getText().toString().isEmpty())
            {
                instructions.setError("Please enter instructions");
            }
            if(description.getText().toString().isEmpty())
            {
                instructions.setError("Please enter a description");
            }

            activity.getProfile().addResDesc(activity, recipeName, description.getText().toString(), instructions.getText().toString());
        });
    }
}

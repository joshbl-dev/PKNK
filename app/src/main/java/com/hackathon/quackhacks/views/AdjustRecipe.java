package com.hackathon.quackhacks.views;

import android.content.Context;
import android.widget.EditText;
import android.widget.LinearLayout;
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

            LinearLayout lay = activity.findViewById(R.id.lin);

            TextView textView1 = new TextView(activity);
            textView1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            textView1.setText(quantity.getText().toString() + " " + unit.getText().toString() + "(s) of " + ingredient.getText().toString());
            textView1.setBackgroundColor(0xff66ff66); // hex color 0xAARRGGBB
            textView1.setPadding(20, 20, 20, 20);// in pixels (left, top, right, bottom)
            lay.addView(textView1);

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

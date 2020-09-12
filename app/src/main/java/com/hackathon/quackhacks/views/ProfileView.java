package com.hackathon.quackhacks.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.hackathon.quackhacks.R;
import com.hackathon.quackhacks.backend.Recipe;

import java.text.DateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;

public class ProfileView extends BaseView {

    private void addPost(String user, Recipe recipe) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        ConstraintLayout layout = (ConstraintLayout) inflater.inflate(R.layout.post_box, null, false);

        ((TextView) layout.findViewById(R.id.usertag_postbox)).setText(user);
        ((TextView) layout.findViewById(R.id.timestamp_postbox)).setText(DateFormat.getDateInstance().format(new Date(recipe.timestamp)));
        ((TextView) layout.findViewById(R.id.recipe_postbox)).setText(recipe.title);

        layout.findViewById(R.id.view_postbox).setOnClickListener(onclick -> {
            activity.changeView(new RecipeView(activity, recipe));
        });

        LinearLayout linear = activity.findViewById(R.id.linearLay);
        linear.addView(layout);
    }

    public ProfileView(Context context) {
        super(context);
        activity.setContentView(R.layout.profile_page);

        TextView enterUser = activity.findViewById(R.id.enterUser);
        enterUser.setText(activity.getProfile().getUsername());

        Map<String, Recipe> recMap = activity.getProfile().getRecipes();
        Set<String> names = recMap.keySet();

        for (String name : names) {
            addPost(name, recMap.get(name));
        }

        activity.findViewById(R.id.ExitSelfProfile).setOnClickListener(onclick -> {
            activity.changeView(new FeedView(context));
        });
        /*An attempt
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        rootRef = rootRef.child("users");
        */
    }
}



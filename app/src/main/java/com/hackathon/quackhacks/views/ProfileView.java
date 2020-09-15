package com.hackathon.quackhacks.views;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hackathon.quackhacks.R;
import com.hackathon.quackhacks.backend.Recipe;
import com.hackathon.quackhacks.widget.PostBox;

// View to display an individual's profile
public class ProfileView extends BaseView {

    private String username;

    public ProfileView(Context context) {
        super(context);
        activity.setContentView(R.layout.profile_page);

        username = activity.getProfile().getUsername();

        TextView enterUser = activity.findViewById(R.id.enterUser);
        enterUser.setText(username);

        for (Recipe recipe : activity.getProfile().getRecipes().values()) {
            new PostBox(activity, this, R.id.userRecipes, username, recipe, true);
        }

        activity.findViewById(R.id.ExitSelfProfile).setOnClickListener(onclick -> activity.changeView(new FeedView(context)));

        activity.findViewById(R.id.logoutBtn).setOnClickListener(onclick -> logout());
    }

    private void logout() {
        SharedPreferences sp = activity.getSharedPreferences("Login", Context.MODE_PRIVATE);
        SharedPreferences.Editor Ed = sp.edit();
        Ed.remove("username");
        Ed.remove("password");
        Ed.apply();
        activity.changeView(new InitialView(activity));
    }

    @Override
    public void reload() {
        ((LinearLayout) activity.findViewById(R.id.userRecipes)).removeAllViewsInLayout();
        for (Recipe recipe : activity.getProfile().getRecipes().values()) {
            new PostBox(activity, this, R.id.userRecipes, username, recipe, true);
        }
    }
}



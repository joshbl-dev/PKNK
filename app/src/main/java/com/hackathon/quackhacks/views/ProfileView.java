package com.hackathon.quackhacks.views;

import android.content.Context;
import android.widget.TextView;

import com.hackathon.quackhacks.R;
import com.hackathon.quackhacks.backend.Recipe;

import java.util.Map;
import java.util.Set;

public class ProfileView extends BaseView {

    public ProfileView(Context context) {
        super(context);
        activity.setContentView(R.layout.profile_page);

        TextView enterUser = activity.findViewById(R.id.enterUser);
        enterUser.setText(activity.getProfile().getUsername());

        Map<String, Recipe> recMap = activity.getProfile().getRecipes();
        Set<String> names = recMap.keySet();

        for (Recipe recipe : activity.getProfile().getRecipes().values()) {
            new PostBox(activity, R.id.linearLay, activity.getProfile().getUsername(), recipe, true);
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



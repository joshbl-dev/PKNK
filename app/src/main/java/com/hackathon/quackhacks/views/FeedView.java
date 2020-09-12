package com.hackathon.quackhacks.views;

import android.content.Context;

import com.hackathon.quackhacks.MainActivity;
import com.hackathon.quackhacks.R;
import com.hackathon.quackhacks.backend.UserAccount;

public class FeedView extends BaseView {


    public FeedView(Context context) {
        super(context);
        activity.findViewById(R.id.feed).setOnClickListener(onclick -> {
            activity.setContentView(R.layout.friend_profile);
            activity.changeView(new FriendProfileView(context));
        });
        activity.findViewById(R.id.posting).setOnClickListener(onclick -> {
            activity.setContentView(R.layout.add_recipe_post);
            activity.changeView(new PostView(context));
        });
        activity.findViewById(R.id.profile).setOnClickListener(onclick -> {
            activity.setContentView(R.layout.profile_page);
            activity.changeView(new ProfileView(context));
        });
    }

}

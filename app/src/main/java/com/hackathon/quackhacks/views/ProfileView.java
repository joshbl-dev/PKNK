package com.hackathon.quackhacks.views;

import android.content.Context;

import com.hackathon.quackhacks.R;

public class ProfileView extends BaseView {

    public ProfileView(Context context) {
        super(context);
        activity.setContentView(R.layout.profile_page);

        activity.findViewById(R.id.ExitSelfProfile).setOnClickListener( onclick -> {
            activity.changeView(new FeedView(context));
        });
    }
}

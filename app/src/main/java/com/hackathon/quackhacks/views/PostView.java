package com.hackathon.quackhacks.views;

import android.content.Context;

import com.hackathon.quackhacks.R;

public class PostView extends BaseView {

    public PostView(Context context) {
        super(context);
        activity.setContentView(R.layout.add_recipe_post);
    }
}

package com.hackathon.quackhacks.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.hackathon.quackhacks.R;

public class RecipePostView extends BaseView {

    public RecipePostView(Context context) {
        super(context);

        LayoutInflater inflater = LayoutInflater.from(activity);
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.post_box, null, false);


    }
}

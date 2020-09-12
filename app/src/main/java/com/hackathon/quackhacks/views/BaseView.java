package com.hackathon.quackhacks.views;

import android.content.Context;
import android.view.View;

import com.hackathon.quackhacks.MainActivity;

public abstract class BaseView extends View {
    protected MainActivity activity;

    public BaseView(Context context) {
        super(context);

        activity = (MainActivity) context;
    }
}

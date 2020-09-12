package com.hackathon.quackhacks.views;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.hackathon.quackhacks.R;

import java.util.ArrayList;

public class FeedView extends BaseView {


    public FeedView(Context context) {
        super(context);
        activity.setContentView(R.layout.feed);

        activity.findViewById(R.id.feed).setOnClickListener(onclick -> {
            activity.changeView(new FriendProfileView(context));
        });
        activity.findViewById(R.id.posting).setOnClickListener(onclick -> {
            activity.changeView(new PostView(context));
        });
        activity.findViewById(R.id.profile).setOnClickListener(onclick -> {
            activity.changeView(new ProfileView(context));
        });

        Spinner friendNames = activity.findViewById(R.id.typeSpin);
//        friendNames.setId(R.id.typeSpin);
        ArrayList<String> temp = new ArrayList<String>();
        for(int i = 0; i < activity.getProfile().getFriends().size(); i++)
        {
            temp.add(activity.getProfile().getUsername());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.feed, temp);
        friendNames.setAdapter(adapter);
    }

}

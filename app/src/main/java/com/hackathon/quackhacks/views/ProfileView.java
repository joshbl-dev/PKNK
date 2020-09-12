package com.hackathon.quackhacks.views;

import android.content.Context;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hackathon.quackhacks.R;

public class ProfileView extends BaseView {

    public ProfileView(Context context) {
        super(context);
        activity.setContentView(R.layout.profile_page);

        TextView enterUser = activity.findViewById(R.id.enterUser);
        enterUser.setText(activity.getProfile().getUsername());

        activity.findViewById(R.id.ExitSelfProfile).setOnClickListener( onclick -> {
            activity.changeView(new FeedView(context));
        });
        /*An attempt
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        rootRef = rootRef.child("users");
        */
    }
}

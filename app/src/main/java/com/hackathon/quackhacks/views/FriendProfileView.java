package com.hackathon.quackhacks.views;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hackathon.quackhacks.R;

public class FriendProfileView extends BaseView {

    public FriendProfileView(Context context) {
        super(context);
        activity.setContentView(R.layout.friend_profile);

        activity.findViewById(R.id.addFriend).setOnClickListener( onclick -> {
            EditText friendName = activity.findViewById(R.id.editTextTextPersonName6);
            TextView friendsLbl = activity.findViewById(R.id.friendsLabel);
            final TextView friendsCount = activity.findViewById(R.id.friendsCounter);
            friendName.setVisibility(View.VISIBLE);
            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
            rootRef = rootRef.child("users");


            rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Object pwd;
                    if (!snapshot.hasChild(friendName.toString())) {
                        friendName.setText("");
                        friendName.setError("Username not found");
                    }
                    else {
                        activity.getProfile().addFriend(friendName.toString());
                        friendsLbl.setVisibility(View.VISIBLE);
                        friendsCount.setVisibility(View.VISIBLE);
                        friendsCount.setText(Integer.toString(activity.getProfile().getFriends().size()));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        });


    }

}
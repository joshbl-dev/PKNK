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
import com.hackathon.quackhacks.backend.UserAccount;

import java.util.Locale;

public class FriendProfileView extends BaseView {

    public FriendProfileView(Context context) {
        super(context);
        activity.setContentView(R.layout.friend_profile);

        activity.findViewById(R.id.addFriend).setOnClickListener(onclick -> {
            EditText friendName = activity.findViewById(R.id.editTextTextPersonName6);

            TextView friendsLbl = activity.findViewById(R.id.friendsLabel);
            final TextView friendsCount = activity.findViewById(R.id.friendsCounter);
            friendName.setVisibility(View.VISIBLE);

            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
            rootRef = rootRef.child("users");


            rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String friendNameStr = friendName.getText().toString();
                    if (!snapshot.hasChild(friendNameStr)) {
                        friendName.setText("");
                        friendName.setError("This friend doesn't exist. Loser");
                    } else if (activity.getProfile().getFriends().contains(friendNameStr)) {
                        friendName.setText("");
                        friendName.setError("You already have this friend! Mr. Popular...");
                    } else {
                        UserAccount profile = activity.getProfile();
                        String profileName = profile.getUsername();
                        profile.addFriend(friendNameStr);
                        UserAccount friendProfile = snapshot.child(friendNameStr).getValue(UserAccount.class);

                        if (friendProfile != null) {
                            friendProfile.addFriend(profileName);

                            friendsLbl.setVisibility(View.VISIBLE);
                            friendsCount.setVisibility(View.VISIBLE);
                            friendsCount.setText(String.format(Locale.ENGLISH, "%d", activity.getProfile().getFriends().size()));
                            activity.getDatabase().setValue(profile.getFriends(), "users", profileName, "friends");
                            activity.getDatabase().setValue(friendProfile.getFriends(), "users", friendNameStr, "friends");
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        });

        activity.findViewById(R.id.ExitSelfProfile).setOnClickListener(onclick -> activity.changeView(new FeedView(context)));


    }

}
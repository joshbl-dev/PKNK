package com.hackathon.quackhacks.views;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

        activity.findViewById(R.id.requestsBtn).setOnClickListener(onclick -> activity.changeView(new FriendRequestView(activity)));
        activity.findViewById(R.id.ExitSelfProfile).setOnClickListener(onclick -> activity.changeView(new FeedView(activity)));

        activity.findViewById(R.id.addFriend).setOnClickListener(onclick -> {
            EditText friendName = activity.findViewById(R.id.editTextTextPersonName6);
            Button friendRemove = activity.findViewById(R.id.removeFriend);

            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
            rootRef = rootRef.child("users");


            rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    String friendNameStr = friendName.getText().toString();
                    if (!snapshot.hasChild(friendNameStr)) {
                        friendName.setText("");
                        friendName.setError("This friend doesn't exist.");
                    } else if (friendNameStr.isEmpty()) {
                        friendName.setText("");
                        friendName.setError("Insert name here.");
                    } else if (activity.getProfile().getFriends().contains(friendNameStr)) {
                        friendName.setText("");
                        friendName.setError("You already have this friend!");
                        friendRemove.setVisibility(View.VISIBLE);
                    } else if (activity.getProfile().getUsername().equalsIgnoreCase(friendNameStr)) {
                        friendName.setText("");
                        friendName.setError("You cannot add yourself.");
                    } else {
                        UserAccount profile = activity.getProfile();
                        String profileName = profile.getUsername();
                        profile.friendsPending.add(friendNameStr);
                        UserAccount friendProfile = snapshot.child(friendNameStr).getValue(UserAccount.class);

                        friendName.setText("Request Sent");

                        friendRemove.setVisibility(View.VISIBLE);

                        activity.getDatabase().storeUser(friendProfile);

                        if (friendProfile != null) {
                            friendProfile.friendRequests.add(profileName);
                            activity.getDatabase().setValue(profile.friendsPending, "users", profileName, "friendsPending");
                            activity.getDatabase().setValue(friendProfile.friendRequests, "users", friendNameStr, "friendRequests");
                        }
                    }
                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        });

        activity.findViewById(R.id.removeFriend).setOnClickListener(onclick -> {

        });

        for (int i = 0; i < activity.getProfile().getFriends().size(); i++) {
            LinearLayout lay = activity.findViewById(R.id.linLa);
            TextView textView = new TextView(activity);
            textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            textView.setText(activity.getProfile().getFriends().get(i));
            textView.setPadding(20, 20, 20, 20);// in pixels (left, top, right, bottom)
            lay.addView(textView);
        }

        TextView friends = activity.findViewById(R.id.friends);
        friends.setText(String.format(Locale.ENGLISH, "My Friends: %d", activity.getProfile().getFriends().size()));

    }

}
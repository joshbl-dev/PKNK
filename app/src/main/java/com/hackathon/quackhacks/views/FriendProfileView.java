package com.hackathon.quackhacks.views;

import android.content.Context;
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

        activity.findViewById(R.id.requestsBtn).setOnClickListener(onclick -> {

        });

        activity.findViewById(R.id.addFriend).setOnClickListener(onclick -> {
            EditText friendName = activity.findViewById(R.id.editTextTextPersonName6);

            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
            rootRef = rootRef.child("users");


            rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String friendNameStr = friendName.getText().toString();
                    UserAccount profile = activity.getProfile();
                    if (!snapshot.hasChild(friendNameStr)) {
                        friendName.setText("");
                        friendName.setError("This friend doesn't exist.");
                    } else if (friendNameStr.isEmpty()) {
                        friendName.setText("");
                        friendName.setError("Insert name here.");
                    } else if (activity.getProfile().getFriends().contains(friendNameStr)) {
                        friendName.setText("");
                        friendName.setError("You already have this friend!");
                    } else if (activity.getProfile().getUsername().equalsIgnoreCase(friendNameStr)) {
                        friendName.setText("");
                        friendName.setError("You cannot add yourself.");
                    } else if (profile.pendingRequests.contains(friendNameStr)) {
                        friendName.setText("");
                        friendName.setError("You already have a pending friend request to " + friendNameStr + ".");
                    }
                    else {
                        String profileName = profile.getUsername();
                        profile.pendingRequests.add(friendNameStr);

                        UserAccount friendProfile = snapshot.child(friendNameStr).getValue(UserAccount.class);

                        activity.getDatabase().storeUser(friendProfile);

                        if (friendProfile != null) {
                            friendProfile.friendRequests.add(profileName);
                            activity.getDatabase().setValue(profile.pendingRequests, "users", profileName, "pendingRequests");
                            activity.getDatabase().setValue(friendProfile.friendRequests, "users", friendNameStr, "friendRequests");
                        }
                    }
                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        });

        for (int i = 0; i < activity.getProfile().getFriends().size(); i++) {
            LinearLayout lay = activity.findViewById(R.id.linLa);
            TextView textView = new TextView(activity);
            textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            textView.setText(activity.getProfile().getFriends().get(i));
            textView.setPadding(20, 20, 20, 20);// in pixels (left, top, right, bottom)
            lay.addView(textView);
        }

        TextView friends = activity.findViewById(R.id.friendsTxt);
        friends.setText(String.format(Locale.ENGLISH, "Friends: %d", activity.getProfile().getFriends().size()));

    }

}
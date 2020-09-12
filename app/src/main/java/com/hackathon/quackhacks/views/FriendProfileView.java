package com.hackathon.quackhacks.views;

import android.content.Context;
import android.view.View;
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

        activity.findViewById(R.id.addFriend).setOnClickListener(onclick -> {
            EditText friendName = activity.findViewById(R.id.editTextTextPersonName6);

            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
            rootRef = rootRef.child("users");


            rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String friendNameStr = friendName.getText().toString();
                    if (!snapshot.hasChild(friendNameStr)) {
                        friendName.setText("");
                        friendName.setError("This friend doesn't exist. Loser");
                    } else if(friendNameStr.isEmpty()) {
                        friendName.setText("");
                        friendName.setError("Type something in, dingus.");
                    } else if (activity.getProfile().getFriends().contains(friendNameStr)) {
                        friendName.setText("");
                        friendName.setError("You already have this friend! Mr. Popular...");
                    } else if (activity.getProfile().getUsername().equalsIgnoreCase(friendNameStr)){
                        friendName.setText("");
                        friendName.setError("You cannot add yourself. Loser");
                    } else {
                        UserAccount profile = activity.getProfile();
                        String profileName = profile.getUsername();
                        profile.addFriend(friendNameStr);
                        UserAccount friendProfile = snapshot.child(friendNameStr).getValue(UserAccount.class);

                        activity.getDatabase().storeUser(friendProfile);

                        if (friendProfile != null) {
                            friendProfile.addFriend(profileName);
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

        for(int i = 0; i < activity.getProfile().getFriends().size(); i++)
        {
            LinearLayout lay = activity.findViewById(R.id.linLa);
            TextView textView1 = new TextView(activity);
            textView1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            textView1.setText(activity.getProfile().getFriends().get(i));
            textView1.setBackgroundColor(0xff66ff66); // hex color 0xAARRGGBB
            textView1.setPadding(20, 20, 20, 20);// in pixels (left, top, right, bottom)
            lay.addView(textView1);
        }

        TextView friends = activity.findViewById(R.id.Friends);
        friends.setText("Friends: " + activity.getProfile().getFriends().size());

    }

}
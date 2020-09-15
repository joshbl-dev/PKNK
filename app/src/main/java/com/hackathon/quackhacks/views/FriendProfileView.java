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

import java.util.List;
import java.util.Locale;

// View for friend list
public class FriendProfileView extends BaseView {

    private EditText friendName;
    private Button friendRemove;

    private UserAccount profile;
    private String profileName;

    public FriendProfileView(Context context) {
        super(context);
        activity.setContentView(R.layout.friend_profile);

        activity.findViewById(R.id.requestsBtn).setOnClickListener(onclick -> activity.changeView(new FriendRequestView(activity)));
        activity.findViewById(R.id.ExitSelfProfile).setOnClickListener(onclick -> activity.changeView(new FeedView(activity)));

        profile = activity.getProfile();
        profileName = profile.getUsername();

        friendName = activity.findViewById(R.id.inputname_profiles);
        friendRemove = activity.findViewById(R.id.removeFriend);

        activity.findViewById(R.id.addFriend).setOnClickListener(onclick -> sendRequest());

        activity.findViewById(R.id.removeFriend).setOnClickListener(onclick -> removeFriend());

        if (profile.getFriends().size() > 0) {
            friendRemove.setEnabled(true);
        }

        reload();
    }

    // Adds friend request to profiles
    private void sendRequest() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference = reference.child("users");


        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String friendNameStr = friendName.getText().toString();
                if (!snapshot.hasChild(friendNameStr)) {
                    setFriendNameError("This friend doesn't exist.");
                } else if (friendNameStr.isEmpty()) {
                    setFriendNameError("Insert name here.");
                } else if (activity.getProfile().getFriends().contains(friendNameStr)) {
                    setFriendNameError("You already have this friend!");
                    friendRemove.setVisibility(View.VISIBLE);
                } else if (activity.getProfile().getUsername().equalsIgnoreCase(friendNameStr)) {
                    setFriendNameError("You cannot add yourself.");
                } else {
                    if (!profile.getFriendsPending().contains(friendNameStr)) {
                        List<String> friendsPending = profile.getFriendsPending();

                        friendsPending.add(friendNameStr);

                        UserAccount friendProfile = snapshot.child(friendNameStr).getValue(UserAccount.class);

                        if (friendProfile != null) {
                            activity.getDatabase().storeUser(friendProfile);

                            List<String> friendRequests = friendProfile.getFriendRequests();
                            friendRequests.add(profileName);

                            activity.getDatabase().setValue(friendsPending, "users", profileName, "friendsPending");
                            activity.getDatabase().setValue(friendRequests, "users", friendNameStr, "friendRequests");
                        }
                    }
                }
                friendName.setHint("Request Sent");
                friendName.setText("");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // Removes friend from profiles
    private void removeFriend() {
        String friendNameStr = friendName.getText().toString();
        List<String> friends = profile.getFriends();
        if (friends.remove(friendNameStr)) {
            friendName.setText("");
            activity.getDatabase().setValue(friends, "users", profileName, "friends");

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
            reference = reference.child("users");

            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.hasChild(friendNameStr)) {
                        activity.getDatabase().storeUser(snapshot.child(friendNameStr).getValue(UserAccount.class));

                        UserAccount friend = activity.getDatabase().getUser(friendNameStr);
                        List<String> friendFriends = friend.getFriends();

                        friendFriends.remove(profileName);

                        activity.getDatabase().setValue(friendFriends, "users", friendNameStr, "friends");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            activity.getDatabase().setValue(friends, "users", profileName, "friends");
            reload();
        }
    }

    private void setFriendNameError(String error) {
        friendName.setError(error);
        friendName.setText("");
    }

    @Override
    public void reload() {
        ((LinearLayout) activity.findViewById(R.id.friendList)).removeAllViewsInLayout();
        LinearLayout friendList = activity.findViewById(R.id.friendList);

        TextView friendCount = new TextView(activity);
        friendCount.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        friendCount.setPadding(20, 20, 20, 20);// in pixels (left, top, right, bottom)
        friendCount.setText(String.format(Locale.ENGLISH, "My Friends: %d", activity.getProfile().getFriends().size()));
        friendList.addView(friendCount);

        for (int i = 0; i < activity.getProfile().getFriends().size(); i++) {
            TextView friend = new TextView(activity);
            friend.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            friend.setText(activity.getProfile().getFriends().get(i));
            friend.setPadding(20, 20, 20, 20);// in pixels (left, top, right, bottom)
            friendList.addView(friend);
        }
    }
}
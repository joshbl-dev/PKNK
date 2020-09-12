package com.hackathon.quackhacks.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hackathon.quackhacks.R;
import com.hackathon.quackhacks.backend.UserAccount;

public class FriendRequestView extends BaseView {

    private UserAccount user;

    public FriendRequestView(Context context) {
        super(context);

        activity.setContentView(R.layout.friend_requests);

        user = activity.getProfile();

        activity.findViewById(R.id.backBtn).setOnClickListener(onclick -> activity.changeView(new FriendProfileView(activity)));

        for (String friendRequest : user.friendRequests) {
            LayoutInflater inflater = LayoutInflater.from(activity);
            ConstraintLayout layout = (ConstraintLayout) inflater.inflate(R.layout.friend_request, null, false);

            LinearLayout linear = activity.findViewById(R.id.requests);
            ((TextView) layout.findViewById(R.id.friendReqName)).setText(friendRequest);
            layout.findViewById(R.id.acceptbtn).setOnClickListener(onclick -> accept(friendRequest));
            layout.findViewById(R.id.rejectBtn).setOnClickListener(onclick -> reject(friendRequest));
            linear.addView(layout);
        }

        for (String friendRequest : user.friendsPending) {
            LayoutInflater inflater = LayoutInflater.from(activity);
            ConstraintLayout layout = (ConstraintLayout) inflater.inflate(R.layout.friend_request, null, false);

            LinearLayout linear = activity.findViewById(R.id.pending);
            ((TextView) layout.findViewById(R.id.friendReqName)).setText(friendRequest);
            layout.findViewById(R.id.acceptbtn).setVisibility(INVISIBLE);
            layout.findViewById(R.id.rejectBtn).setVisibility(INVISIBLE);
            linear.addView(layout);
        }
    }

    private void accept(String friendRequest) {
        user.addFriend(friendRequest);
        user.friendRequests.remove(friendRequest);

        String username = user.getUsername();
        UserAccount friend = activity.getDatabase().getUser(friendRequest);

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        rootRef = rootRef.child("users");

        if (friend == null) {
            rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.hasChild(friendRequest)) {
                        activity.getDatabase().storeUser(snapshot.child(friendRequest).getValue(UserAccount.class));

                        UserAccount friend = activity.getDatabase().getUser(friendRequest);

                        friend.friendsPending.remove(username);
                        friend.addFriend(username);

                        save(friend);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } else {
            friend.friendsPending.remove(username);
            save(friend);
        }

        save(user);
    }

    private void reject(String friendRequest) {
        user.friendRequests.remove(friendRequest);

        UserAccount friend = activity.getDatabase().getUser(friendRequest);

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        rootRef = rootRef.child("users");

        String username = user.getUsername();

        if (friend == null) {
            rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.hasChild(friendRequest)) {
                        activity.getDatabase().storeUser(snapshot.child(friendRequest).getValue(UserAccount.class));

                        UserAccount friend = activity.getDatabase().getUser(friendRequest);

                        friend.friendsPending.remove(username);

                        save(friend);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } else {
            friend.friendsPending.remove(username);
            save(friend);
        }
        save(user);
    }

    private void save(UserAccount userAccount) {
        activity.getDatabase().setValue("users", userAccount.getUsername(), userAccount);
    }
}

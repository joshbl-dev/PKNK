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

// View for accepting/rejecting and viewing friend requests/pending
public class FriendRequestView extends BaseView {

    private UserAccount user;

    public FriendRequestView(Context context) {
        super(context);

        activity.setContentView(R.layout.friend_requests);

        user = activity.getProfile();

        activity.findViewById(R.id.backBtn).setOnClickListener(onclick -> activity.changeView(new FriendProfileView(activity)));

        reload();
    }

    private void accept(String friendRequest) {
        if (!user.getFriends().contains(friendRequest)) {
            user.addFriend(friendRequest);
        }
        user.getFriendRequests().remove(friendRequest);

        String username = user.getUsername();
        UserAccount friend = activity.getDatabase().getUser(friendRequest);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference = reference.child("users");

        if (friend == null) {
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.hasChild(friendRequest)) {
                        activity.getDatabase().storeUser(snapshot.child(friendRequest).getValue(UserAccount.class));

                        UserAccount friend = activity.getDatabase().getUser(friendRequest);

                        friend.getFriendsPending().remove(username);
                        if (!friend.getFriends().contains(username)) {
                            friend.addFriend(username);
                        }

                        save(friend);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } else {
            friend.getFriendsPending().remove(username);
            save(friend);
        }

        save(user);
    }

    private void reject(String friendRequest) {
        user.getFriendRequests().remove(friendRequest);

        UserAccount friend = activity.getDatabase().getUser(friendRequest);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference = reference.child("users");

        String username = user.getUsername();

        if (friend == null) {
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.hasChild(friendRequest)) {
                        activity.getDatabase().storeUser(snapshot.child(friendRequest).getValue(UserAccount.class));

                        UserAccount friend = activity.getDatabase().getUser(friendRequest);

                        friend.getFriendsPending().remove(username);

                        save(friend);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } else {
            friend.getFriendsPending().remove(username);
            save(friend);
        }
        save(user);
    }

    private void save(UserAccount userAccount) {
        activity.getDatabase().setValue("users", userAccount.getUsername(), userAccount);
        reload();
    }

    @Override
    public void reload() {
        ((LinearLayout) activity.findViewById(R.id.pending)).removeAllViewsInLayout();
        ((LinearLayout) activity.findViewById(R.id.requests)).removeAllViewsInLayout();

        for (String friendRequest : user.getFriendRequests()) {
            LayoutInflater inflater = LayoutInflater.from(activity);
            ConstraintLayout request = (ConstraintLayout) inflater.inflate(R.layout.friend_request, null, false);

            LinearLayout linear = activity.findViewById(R.id.requests);
            ((TextView) request.findViewById(R.id.friendReqName)).setText(friendRequest);
            request.findViewById(R.id.acceptbtn).setOnClickListener(onclick -> accept(friendRequest));
            request.findViewById(R.id.rejectBtn).setOnClickListener(onclick -> reject(friendRequest));
            linear.addView(request);
        }

        for (String friendRequest : user.getFriendsPending()) {
            LayoutInflater inflater = LayoutInflater.from(activity);
            ConstraintLayout pending = (ConstraintLayout) inflater.inflate(R.layout.friend_request, null, false);

            LinearLayout linear = activity.findViewById(R.id.pending);
            ((TextView) pending.findViewById(R.id.friendReqName)).setText(friendRequest);
            pending.findViewById(R.id.acceptbtn).setVisibility(INVISIBLE);
            pending.findViewById(R.id.rejectBtn).setVisibility(INVISIBLE);
            linear.addView(pending);
        }
    }
}

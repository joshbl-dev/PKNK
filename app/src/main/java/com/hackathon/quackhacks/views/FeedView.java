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
import com.hackathon.quackhacks.backend.Recipe;
import com.hackathon.quackhacks.backend.UserAccount;

import java.text.DateFormat;
import java.util.Date;

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

        activity.findViewById(R.id.reloadBtn).setOnClickListener(onclick -> {
            reload();
        });

//        Spinner friendNames = activity.findViewById(R.id.typeSpin);
//        List<String> temp = activity.getProfile().getFriends();
//        temp.add("No Friends Yet!");
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.feed, temp);
//        friendNames.setAdapter(adapter);

        reload();
    }

    private void reload() {
        ((LinearLayout) activity.findViewById(R.id.postsLayout)).removeAllViewsInLayout();

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        rootRef = rootRef.child("users");

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (String friend : activity.getProfile().getFriends()) {
                    if (friend != null) {
                        if (snapshot.hasChild(friend)) {
                            UserAccount friendAcc = snapshot.child(friend).getValue(UserAccount.class);
                            if (friendAcc != null) {
                                for (Recipe value : friendAcc.recipes.values()) {
                                    addPost(friend, value);
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void addPost(String user, Recipe recipe) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        ConstraintLayout layout = (ConstraintLayout) inflater.inflate(R.layout.post_box, null, false);

        ((TextView) layout.findViewById(R.id.usertag_postbox)).setText(user);
        ((TextView) layout.findViewById(R.id.timestamp_postbox)).setText(DateFormat.getDateInstance().format(new Date(recipe.timestamp)));
        ((TextView) layout.findViewById(R.id.recipe_postbox)).setText(recipe.title);

        LinearLayout linear = activity.findViewById(R.id.postsLayout);
        linear.addView(layout);
    }

}

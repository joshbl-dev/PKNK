package com.hackathon.quackhacks.views;

import android.content.Context;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hackathon.quackhacks.R;
import com.hackathon.quackhacks.backend.Recipe;
import com.hackathon.quackhacks.backend.UserAccount;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeedView extends BaseView {

    public FeedView(Context context) {
        super(context);
        activity.setContentView(R.layout.feed);

        activity.findViewById(R.id.feed).setOnClickListener(onclick -> activity.changeView(new FriendProfileView(context)));
        activity.findViewById(R.id.posting).setOnClickListener(onclick -> activity.changeView(new PostView(context)));
        activity.findViewById(R.id.profile).setOnClickListener(onclick -> activity.changeView(new ProfileView(context)));

        activity.findViewById(R.id.reloadBtn).setOnClickListener(onclick -> reload());

        reload();
    }

    @Override
    public void reload() {
        ((LinearLayout) activity.findViewById(R.id.postsLayout)).removeAllViewsInLayout();

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        rootRef = rootRef.child("users");

        BaseView view = this;
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            List<Recipe> recipePosts = new ArrayList<>();
            Map<Recipe, String> recipesMap = new HashMap<>();

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (String friend : activity.getProfile().getFriends()) {
                    if (friend != null) {
                        if (snapshot.hasChild(friend)) {
                            UserAccount friendAcc = snapshot.child(friend).getValue(UserAccount.class);
                            activity.getDatabase().storeUser(friendAcc);
                            if (friendAcc != null) {
                                for (Recipe value : friendAcc.recipes.values()) {
                                    recipePosts.add(value);
                                    recipesMap.put(value, friend);
                                }
                            }
                        }
                    }
                }
                Collections.sort(recipePosts);

                for (Recipe recipePost : recipePosts) {
                    new PostBox(activity, view, R.id.postsLayout, recipesMap.get(recipePost), recipePost, false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}

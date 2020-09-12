package com.hackathon.quackhacks.backend;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Database {

    private FirebaseDatabase database;

    public Database() {
        database = FirebaseDatabase.getInstance();
    }

    public DatabaseReference getReference() {
        return database.getReference();
    }

    public void setValue(String node, String value, Object key) {
        getReference().child(node).child(value).setValue(key);
    }

    public boolean hasChild(String node, String value) {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        rootRef = rootRef.child(node);
        final boolean[] hasChild = {false};

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                hasChild[0] = snapshot.hasChild(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return hasChild[0];
    }
}
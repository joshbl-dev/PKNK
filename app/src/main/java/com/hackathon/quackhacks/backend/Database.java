package com.hackathon.quackhacks.backend;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Database {

    private FirebaseDatabase database;

    public Database() {
        database = FirebaseDatabase.getInstance();
    }

    public DatabaseReference getReference() {
        return database.getReference();
    }

    public void setValue(String value, Object key) {
        getReference().setValue(value, key);
    }
}
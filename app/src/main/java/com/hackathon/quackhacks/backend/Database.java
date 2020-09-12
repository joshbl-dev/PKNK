package com.hackathon.quackhacks.backend;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Database {

    private FirebaseDatabase database;

    private Map<String, UserAccount> users = new HashMap<>();

    public Database() {
        database = FirebaseDatabase.getInstance();
    }

    public DatabaseReference getReference() {
        return database.getReference();
    }

    public void setValue(String node, String value, Object key) {
        getReference().child(node).child(value).setValue(key);

        if (node.equals("users")) {
            users.put(value, (UserAccount) key);
        }
    }

    public UserAccount getUser(String user) {
        return users.get(user);
    }

    public void storeUser(UserAccount userAccount) {
        if (userAccount != null) {
            String name = userAccount.getUsername();
            if (!users.containsKey(name)) {
                users.put(name, userAccount);
            }
        }
    }

    public void setValue(Object key, String... nodes) {
        DatabaseReference ref = getReference();

        for (String node : nodes) {
            ref = ref.child(node);
        }

        ref.setValue(key);
    }
}
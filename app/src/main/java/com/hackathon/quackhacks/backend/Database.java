package com.hackathon.quackhacks.backend;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

// Stores/Accesses Google Firebase real time database
public class Database {

    private FirebaseDatabase database;

    private Map<String, UserAccount> users = new HashMap<>();

    public Database() {
        database = FirebaseDatabase.getInstance();
    }

    private DatabaseReference getReference() {
        return database.getReference();
    }

    public void setValue(String node, String value, Object key) {
        getReference().child(node).child(value).setValue(key);

        // Cache users if possible
        if (node.equals("users") && key instanceof UserAccount) {
            users.put(value, (UserAccount) key);
        }
    }

    // Returns user based on username
    public UserAccount getUser(String user) {
        return users.get(user);
    }

    // Stores user account
    public void storeUser(UserAccount userAccount) {
        if (userAccount != null) {
            users.put(userAccount.getUsername(), userAccount);
        }
    }

    // Stores any type of data given node path and value to store
    public void setValue(Object value, String... nodes) {
        DatabaseReference ref = getReference();

        for (String node : nodes) {
            ref = ref.child(node);
        }

        ref.setValue(value);
    }
}
package com.hackathon.quackhacks;

import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hackathon.quackhacks.backend.Database;
import com.hackathon.quackhacks.backend.UserAccount;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Database database = new Database();

    private Map<String, EditText> inputs = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText username = findViewById(R.id.usernameInput);
        EditText email = findViewById(R.id.emailInput);
        EditText password = findViewById(R.id.passwordInput);

        inputs.put("email", email);
        inputs.put("username", username);
        inputs.put("password", password);

        findViewById(R.id.loginBut).setOnClickListener(onclick -> {
            boolean filled = true;
            for (EditText value : inputs.values()) {
                if (value.getText().toString().isEmpty()) {
                    value.setError("Input Required");
                    filled = false;
                }
            }


            String usernameStr = username.getText().toString();

            if (filled) {
                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                rootRef = rootRef.child("users");

                MainActivity currentActivity = this;

                rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(usernameStr)) {
                            username.setText("");
                            username.setError("Username taken");
                            return;
                        }
                        new UserAccount(currentActivity, email.getText().toString(), usernameStr, password.getText().toString());
                        setContentView(R.layout.feed);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    public Database getDatabase() {
        return database;
    }
}
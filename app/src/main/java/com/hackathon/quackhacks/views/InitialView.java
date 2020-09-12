package com.hackathon.quackhacks.views;

import android.content.Context;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hackathon.quackhacks.R;
import com.hackathon.quackhacks.backend.UserAccount;

import java.util.HashMap;
import java.util.Map;

public class InitialView extends BaseView {

    private Map<String, EditText> inputs = new HashMap<>();
    private EditText username;
    private EditText password;

    public InitialView(Context context) {
        super(context);

        activity.setContentView(R.layout.activity_main);

        username = activity.findViewById(R.id.usernameInput);
        password = activity.findViewById(R.id.passwordInput);

        inputs.put("username", username);
        inputs.put("password", password);

        activity.findViewById(R.id.createAccountbutton).setOnClickListener(onclick -> signup());
        activity.findViewById(R.id.loginBut).setOnClickListener(onclick -> login());
    }

    public void login() {
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

            rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Object pwd;
                    if (!snapshot.hasChild(usernameStr)) {
                        username.setText("");
                        username.setError("Username not found");
                    } else if ((pwd = snapshot.child(usernameStr).child("password").getValue()) != null && pwd.equals(password)) {
                        activity.setProfile((UserAccount) snapshot.child(usernameStr).getValue());
                        activity.changeView(new FeedView(activity));

                    } else {
                        username.setText("");
                        username.setError("Password invalid");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    public void signup() {
        activity.changeView(new SignupView(activity));
    }
}

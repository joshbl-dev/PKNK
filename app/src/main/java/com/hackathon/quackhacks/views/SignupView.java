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

public class SignupView extends BaseView {

    private Map<String, EditText> inputs = new HashMap<>();
    private EditText username;
    private EditText password;
    private EditText email;

    public SignupView(Context context) {
        super(context);
        activity.setContentView(R.layout.sign_up);

        username = activity.findViewById(R.id.usernameInput_signup);
        password = activity.findViewById(R.id.passwordInput_signup);
        email = activity.findViewById(R.id.emailInput_signup);

        inputs.put("username", username);
        inputs.put("email", email);
        inputs.put("password", password);

        activity.findViewById(R.id.createAccount_signup).setOnClickListener(onclick -> signup());
    }

    public void signup() {
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
                    if (snapshot.hasChild(usernameStr)) {
                        username.setText("");
                        username.setError("Username taken");
                        return;
                    }
                    activity.setProfile(new UserAccount(activity, email.getText().toString(), usernameStr, password.getText().toString()));
                    activity.changeView(new FeedView(activity));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}

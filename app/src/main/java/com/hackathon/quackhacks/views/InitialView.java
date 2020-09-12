package com.hackathon.quackhacks.views;

import android.content.Context;
import android.content.SharedPreferences;
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

        attemptLogin();

        setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == 66) {
                login();
                return true;
            }
            return false;
        });
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
                    } else if ((pwd = snapshot.child(usernameStr).child("password").getValue()) != null && pwd.equals(password.getText().toString())) {
                        activity.setProfile(snapshot.child(usernameStr).getValue(UserAccount.class));
                        saveLogin(usernameStr, (String) pwd);
                        activity.changeView(new FeedView(activity));

                    } else {
                        username.setText(pwd != null ? pwd.toString() : "Null");
                        username.setError("Password invalid");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    private void saveLogin(String username, String password) {
        SharedPreferences sp = activity.getSharedPreferences("Login", Context.MODE_PRIVATE);
        SharedPreferences.Editor Ed = sp.edit();
        Ed.putString("Unm", username);
        Ed.putString("Psw", password);
        Ed.apply();
    }

    private void attemptLogin() {
        SharedPreferences sp = activity.getSharedPreferences("Login", Context.MODE_PRIVATE);

        String username = sp.getString("Unm", null);
        String password = sp.getString("Psw", null);

        if (username != null && password != null) {
            this.username.setText(username);
            this.password.setText(password);
            login();
        }
    }

    public void signup() {
        activity.changeView(new SignupView(activity));
    }
}

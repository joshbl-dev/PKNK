package com.hackathon.quackhacks;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

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
                if (database.hasChild("users", usernameStr)) {
                    System.out.println("Has child");
                    username.setText("");
                    username.setError("Username taken");
                } else {
                    new UserAccount(this, email.getText().toString(), usernameStr, password.getText().toString());
                    setContentView(R.layout.feed);
                }
            }
        });
    }

    public Database getDatabase() {
        return database;
    }
}
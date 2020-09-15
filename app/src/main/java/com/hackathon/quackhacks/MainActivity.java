package com.hackathon.quackhacks;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.hackathon.quackhacks.backend.Database;
import com.hackathon.quackhacks.backend.UserAccount;
import com.hackathon.quackhacks.views.BaseView;
import com.hackathon.quackhacks.views.InitialView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private Database database = new Database();
    private UserAccount profile;
    private BaseView currentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Objects.requireNonNull(getSupportActionBar()).hide();

        changeView(new InitialView(this));
    }

    public void changeView(BaseView view) {
        currentView = view;
    }

    // possible future implementations
    public BaseView getCurrentView() {
        return currentView;
    }

    public UserAccount getProfile() {
        return profile;
    }

    public void setProfile(UserAccount profile) {
        database.storeUser(profile);
        this.profile = profile;
    }

    public Database getDatabase() {
        return database;
    }
}
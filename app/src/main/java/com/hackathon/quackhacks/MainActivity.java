package com.hackathon.quackhacks;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.hackathon.quackhacks.backend.Database;
import com.hackathon.quackhacks.views.InitialView;

public class MainActivity extends AppCompatActivity {

    private Database database = new Database();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new InitialView(this);
    }

    public Database getDatabase() {
        return database;
    }
}
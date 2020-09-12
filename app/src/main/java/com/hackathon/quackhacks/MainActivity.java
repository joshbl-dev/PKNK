package com.hackathon.quackhacks;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.hackathon.quackhacks.backend.Database;

public class MainActivity extends AppCompatActivity {

    private Database database = new Database();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database.setValue("Test", "test");


        TextView introText = findViewById(R.id.intro_text);
    }
}
package com.hackathon.quackhacks;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.hackathon.quackhacks.backend.GoogleHandler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TextView introText = findViewById(R.id.intro_text);

        new GoogleHandler();




    }
}
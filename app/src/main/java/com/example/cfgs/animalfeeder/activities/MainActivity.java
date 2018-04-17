package com.example.cfgs.animalfeeder.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.brouding.blockbutton.BlockButton;
import com.example.cfgs.animalfeeder.R;

public class MainActivity extends AppCompatActivity {

    BlockButton btnProfile, btnSettings, btnBowl, btnList, btnChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnProfile  = findViewById(R.id.btnProfile);
        btnSettings = findViewById(R.id.btnSettings);
        btnBowl     = findViewById(R.id.btnBowl);
        btnList     = findViewById(R.id.btnList);
        btnChat     = findViewById(R.id.btnChat);

        btnProfile.setEnabled(true);
        btnSettings.setEnabled(true);
        btnBowl.setEnabled(true);
        btnList.setEnabled(true);
        btnChat.setEnabled(true);

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnBowl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}

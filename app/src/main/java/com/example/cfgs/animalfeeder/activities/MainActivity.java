package com.example.cfgs.animalfeeder.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.brouding.blockbutton.BlockButton;
import com.example.cfgs.animalfeeder.R;
import com.example.cfgs.animalfeeder.fragments.MainFragment;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.containerFragment, new MainFragment()).addToBackStack("Main").commit();

    }
}

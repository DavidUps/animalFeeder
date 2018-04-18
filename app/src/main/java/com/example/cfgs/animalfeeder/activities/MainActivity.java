package com.example.cfgs.animalfeeder.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.brouding.blockbutton.BlockButton;
import com.example.cfgs.animalfeeder.R;
import com.example.cfgs.animalfeeder.fragments.MainFragment;
import com.example.cfgs.animalfeeder.fragments.ProfileFragment;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        getSupportFragmentManager().beginTransaction().replace(R.id.containerFragment, new MainFragment()).addToBackStack("Main").commit();

    }

    public void profileFragment(){
        getSupportFragmentManager().beginTransaction().replace(R.id.containerFragment, new ProfileFragment()).addToBackStack("Profile").commit();
    }

    //Change All Fragment
    public void settingFragment(){
        getSupportFragmentManager().beginTransaction().replace(R.id.containerFragment, new ProfileFragment()).addToBackStack("Setting").commit();
    }
    public void bowlFragment(){
        getSupportFragmentManager().beginTransaction().replace(R.id.containerFragment, new ProfileFragment()).addToBackStack("Bowl").commit();
    }
    public void listFragment(){
        getSupportFragmentManager().beginTransaction().replace(R.id.containerFragment, new ProfileFragment()).addToBackStack("List").commit();
    }
    public void chatFragment(){
        getSupportFragmentManager().beginTransaction().replace(R.id.containerFragment, new ProfileFragment()).addToBackStack("Chat").commit();
    }
    public void mainActivity(){
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }
}

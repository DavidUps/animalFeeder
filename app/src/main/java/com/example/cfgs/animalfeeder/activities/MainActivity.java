package com.example.cfgs.animalfeeder.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.cfgs.animalfeeder.R;
import com.example.cfgs.animalfeeder.fragments.ChatFragment;
import com.example.cfgs.animalfeeder.fragments.ListFragment;
import com.example.cfgs.animalfeeder.fragments.MainFragment;
import com.example.cfgs.animalfeeder.fragments.ProfileFragment;
import com.example.cfgs.animalfeeder.fragments.SettingsFragment;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        getSupportFragmentManager().beginTransaction().replace(R.id.containerFragment, new MainFragment()).addToBackStack("Main").commit();

    }

    /**
     * Open Profile Fragment
     */
    public void profileFragment(){
        getSupportFragmentManager().beginTransaction().replace(R.id.containerFragment, new ProfileFragment()).addToBackStack("Profile").commit();
    }

    /**
     * Open Settings Fragment
     */
    public void settingFragment(){
        getSupportFragmentManager().beginTransaction().replace(R.id.containerFragment, new SettingsFragment()).addToBackStack("Setting").commit();
    }

    /**
     * Open Bowl Fragment
     */
    public void bowlFragment(){
        Toast.makeText(this, "Proximamente", Toast.LENGTH_SHORT).show();
    }

    /**
     * Open List Fragment
     */
    public void listFragment(){
        getSupportFragmentManager().beginTransaction().replace(R.id.containerFragment, new ListFragment()).addToBackStack("List").commit();
    }

    /**
     * Open Chat Fragment
     */
    public void chatFragment(){
        getSupportFragmentManager().beginTransaction().replace(R.id.containerFragment, new ChatFragment()).addToBackStack("Chat").commit();
    }

    /**
     * Open Main Fragment
     */
    public void mainFragment(){
        getSupportFragmentManager().beginTransaction().replace(R.id.containerFragment, new MainFragment()).addToBackStack("main").commit();
    }

    /**
     * Open Main Activity
     */
    public void mainActivity(){
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }
}

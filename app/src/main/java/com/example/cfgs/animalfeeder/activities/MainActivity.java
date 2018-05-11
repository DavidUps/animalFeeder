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
import com.example.cfgs.animalfeeder.fragments.SetUpOneFragment;
import com.example.cfgs.animalfeeder.fragments.SetUpTwoFragment;
import com.example.cfgs.animalfeeder.fragments.SettingsFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();


        database = FirebaseDatabase.getInstance();
        DatabaseReference profileRef = database.getReference("users").child(FirebaseAuth.getInstance().getUid().toString());

        profileRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("firstTime").getValue() == null ){
                    setUpOneFragment();
                }else{
                    mainFragment();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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
        DatabaseReference profileRef = database.getReference("users").child(FirebaseAuth.getInstance().getUid().toString());
        profileRef.child("firstTime").setValue("1");

        getSupportFragmentManager().beginTransaction().replace(R.id.containerFragment, new MainFragment()).addToBackStack("main").commit();
    }

    /**
     * Open Main Activity
     */
    public void mainActivity(){
        startActivity(new Intent(MainActivity.this, MainActivity.class));
    }

    /**
     * Open Set Up One
     */
    public void setUpOneFragment(){
        getSupportFragmentManager().beginTransaction().replace(R.id.containerFragment, new SetUpOneFragment()).addToBackStack("OneFragment").commit();
    }

    /**
     * Open Set Up Two
     */
    public void setUpTwoFragment(){
        getSupportFragmentManager().beginTransaction().replace(R.id.containerFragment, new SetUpTwoFragment()).addToBackStack("TwoFragment").commit();
    }

    /**
     * Open Login Activity
     */
    public void loginActivity(){
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }
}

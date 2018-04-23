package com.example.cfgs.animalfeeder.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.cfgs.animalfeeder.R;
import com.example.cfgs.animalfeeder.io.DownloadProfileImage;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            startActivity(new Intent(this, MainActivity.class));
        }else{
            // Choose authentication providers
            List<AuthUI.IdpConfig> providers = Arrays.asList(
                    //new AuthUI.IdpConfig.Builder(AuthUI.PHONE_VERIFICATION_PROVIDER).build(),
                    //new AuthUI.IdpConfig.Builder(AuthUI.TWITTER_PROVIDER).build(),
                    //new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build(),
                    new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                    new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build());

            // Create and launch sign-in intent
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .build(),
                    RC_SIGN_IN);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN){
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK){
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference userReference = firebaseDatabase.getReference("users/"+user.getUid());
                userReference.child("name").setValue(user.getDisplayName());
                userReference.child("email").setValue(user.getEmail());

                String[] url = {FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString()};
                new DownloadProfileImage().execute(url);
                startActivity(new Intent(this, MainActivity.class));
            }else{
                Toast.makeText(this, R.string.user_error, Toast.LENGTH_SHORT).show();
            }
        }
    }


}

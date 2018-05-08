package com.example.cfgs.animalfeeder.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.cfgs.animalfeeder.R;
import com.example.cfgs.animalfeeder.io.DownloadProfileImage;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;
    FirebaseStorage storage;
    FirebaseDatabase database;
    DatabaseReference profileRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        storage  = FirebaseStorage.getInstance("gs://animalfeeder-cae79.appspot.com/");
        database = FirebaseDatabase.getInstance();

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
                profileRef = database.getReference("users").child(FirebaseAuth.getInstance().getUid().toString());
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                profileRef.child("name").setValue(user.getDisplayName());
                profileRef.child("email").setValue(user.getEmail());

                if ()
                if(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl() != null){
                    String[] url = {FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString()};
                    new DownloadProfileImage().execute(url);
                }
                startActivity(new Intent(this, MainActivity.class));
            }else{
                Toast.makeText(this, R.string.user_error, Toast.LENGTH_SHORT).show();
            }
        }
    }
}

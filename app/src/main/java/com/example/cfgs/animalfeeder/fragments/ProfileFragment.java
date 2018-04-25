package com.example.cfgs.animalfeeder.fragments;

import android.app.ActivityManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.brouding.blockbutton.BlockButton;
import com.bumptech.glide.Glide;
import com.example.cfgs.animalfeeder.R;
import com.example.cfgs.animalfeeder.activities.MainActivity;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static android.app.Activity.RESULT_CANCELED;

public class ProfileFragment extends Fragment {

    private final int GALLERY = 1;

    private OnFragmentInteractionListener mListener;
    //XML References
    BlockButton btnSave, btnLogOut;
    ImageView imgProfile;
    EditText etProfileName, etProfilePet;

    //Firebase References
    FirebaseStorage storage;
    FirebaseDatabase database;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        btnSave         = view.findViewById(R.id.btnSave);
        btnLogOut       = view.findViewById(R.id.btnLogOut);
        imgProfile      = view.findViewById(R.id.imgvProfile);
        etProfileName   = view.findViewById(R.id.etProfileName);
        etProfilePet = view.findViewById(R.id.etProfilePet);

        //Firebase Storage Reference.
        storage = FirebaseStorage.getInstance("gs://animalfeeder-cae79.appspot.com/");
        StorageReference downloadImg = storage.getReference("profileImage/"+FirebaseAuth.getInstance().getCurrentUser().getUid().toString()+".jpg");

        //Firebase Database Reference.
        database = FirebaseDatabase.getInstance();
        final DatabaseReference profileRef = database.getReference("users").child(FirebaseAuth.getInstance().getUid().toString());


        //Download Profile Photo.
        Glide.with(getActivity())
                .using(new FirebaseImageLoader())
                .load(downloadImg)
                .error(R.drawable.profile)
                .into(imgProfile);

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthUI.getInstance()
                        .signOut(getActivity())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getActivity(), R.string.correct_logout, Toast.LENGTH_SHORT).show();
                                ((MainActivity) getActivity()).mainActivity();
                            }
                        });
            }
        });

        //Take Profile Photo
        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent;
                Intent cameraIntent;

                Intent chooser = new Intent(Intent.ACTION_CHOOSER);
                chooser.putExtra(Intent.EXTRA_INTENT, cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE));
                chooser.putExtra(Intent.EXTRA_INTENT, galleryIntent = new Intent(Intent.ACTION_PICK,null));
                chooser.putExtra(Intent.EXTRA_TITLE, "Choose an image or take it");

                Intent[] intentArray =  {cameraIntent, galleryIntent};
                chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
                startActivityForResult(chooser, 1);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etProfileName.getText().equals("")){
                    etError();
                }else{
                    profileRef.child("name").setValue(etProfileName.getText().toString());
                }
                if (etProfilePet.getText().equals("")){
                    etError();
                }else{
                    profileRef.child("pet").setValue(etProfilePet.getText().toString());
                }
            }
        });

        //Get Name and Animal.
        profileRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                etProfileName.setText(dataSnapshot.child("name").getValue().toString());
                etProfilePet.setText(dataSnapshot.child("pet").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;
    }

    private void etError() {
        Toast.makeText(getActivity(), R.string.etError, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_CANCELED){
            return;
        }
        StorageReference uploadImg = storage.getReference("profileImage");

        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentUri = data.getData();
                try {
                    //Upload Image To Firebase.
                    //Create a bitmap, a ByteArrayOutputStream, make a compress and in the UploadTask the route is put and casted.
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), contentUri);
                    imgProfile.setImageBitmap(bitmap);
                    ByteArrayOutputStream bout = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bout);
                    UploadTask uploadTask = uploadImg.child(FirebaseAuth.getInstance().getCurrentUser().getUid() + ".jpg").putBytes(bout.toByteArray());
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }



    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

package com.example.cfgs.animalfeeder.fragments;

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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static android.app.Activity.RESULT_CANCELED;

public class ProfileFragment extends Fragment {

    private final int GALLERY = 1;

    private OnFragmentInteractionListener mListener;
    //Buttons
    BlockButton btnSave, btnLogOut;
    ImageView imgProfile;

    FirebaseStorage storage;


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

        btnSave    = view.findViewById(R.id.btnSave);
        btnLogOut  = view.findViewById(R.id.btnLogOut);
        imgProfile = view.findViewById(R.id.imgvProfile);

        //Firebase Main Reference.
        storage = FirebaseStorage.getInstance("gs://animalfeeder-cae79.appspot.com/");

        //Firebase Profile Imagen Reference.
        StorageReference downloadImg = storage.getReference("profileImage/"+FirebaseAuth.getInstance().getCurrentUser()+".jpg");

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

        return view;
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
                    //Subir la imagen a Firebase.
                    //Se crea un bitmap, un ByteArrayOutputStream, se hace un compress y en el UploadTask se pone la ruta y se castea.
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

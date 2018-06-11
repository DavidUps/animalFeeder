package com.example.cfgs.animalfeeder.fragments;

import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.brouding.blockbutton.BlockButton;
import com.example.cfgs.animalfeeder.R;
import com.example.cfgs.animalfeeder.activities.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Timer;

public class SetUpOneFragment extends Fragment {

    BlockButton btnNext, btnCat, btnDog;
    private OnFragmentInteractionListener mListener;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference userRef;
    EditText etRaspberryIp, etPetName;
    ImageView ivSetUpTwo;


    public SetUpOneFragment() {
    }
    public static SetUpOneFragment newInstance(String param1, String param2) {
        SetUpOneFragment fragment = new SetUpOneFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_set_up, container, false);

        etRaspberryIp = view.findViewById(R.id.etRaspberryIpSetUp);
        btnNext = view.findViewById(R.id.btnSetUpTwo);
        btnCat = view.findViewById(R.id.btnCat);
        btnDog = view.findViewById(R.id.btnDog);
        ivSetUpTwo = view.findViewById(R.id.ivSetUpTwo);
        etPetName = view.findViewById(R.id.etPetNameSetUpTwo);

        final Resources res = getResources();

        firebaseDatabase = FirebaseDatabase.getInstance();
        userRef = firebaseDatabase.getReference("users/" + FirebaseAuth.getInstance().getUid().toString());

        btnCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivSetUpTwo.setImageDrawable(res.getDrawable(R.drawable.cat));
            }
        });

        btnDog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivSetUpTwo.setImageDrawable(res.getDrawable(R.drawable.dog));
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userRef.child("raspberryIp").setValue(etRaspberryIp.getText().toString());
                userRef.child("pet").setValue(etPetName.getText().toString());
                userRef.child("firstTime").setValue("1");
                new CountDownTimer(30000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        Log.i("Time:","time"+ millisUntilFinished / 1000);
                        //here you can have your logic to set text to edittext
                    }

                    public void onFinish() {
                        ((MainActivity)getActivity()).mainFragment();
                    }
                }.start();
            }
        });

        return view;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}

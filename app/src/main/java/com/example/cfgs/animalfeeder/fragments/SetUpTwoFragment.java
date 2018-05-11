package com.example.cfgs.animalfeeder.fragments;

import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.brouding.blockbutton.BlockButton;
import com.example.cfgs.animalfeeder.R;
import com.example.cfgs.animalfeeder.activities.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SetUpTwoFragment extends Fragment {

    BlockButton btnNext,btnDog,btnCat;
    private OnFragmentInteractionListener mListener;
    ImageView ivSetUpTwo;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference userRef;
    EditText etPetName;

    public SetUpTwoFragment() {
    }

    public static SetUpTwoFragment newInstance(String param1, String param2) {
        SetUpTwoFragment fragment = new SetUpTwoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_set_up_two, container, false);

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
                userRef.child("pet").setValue(etPetName.getText().toString());
                ((MainActivity)getActivity()).mainFragment();
            }
        });

        return view;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}

package com.example.cfgs.animalfeeder.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.brouding.blockbutton.BlockButton;
import com.example.cfgs.animalfeeder.R;
import com.example.cfgs.animalfeeder.activities.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SettingsFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    BlockButton btnSave;
    EditText etPet, etRaspberry;
    FirebaseDatabase firebaseDatabase;

    public SettingsFragment() {
        // Required empty public constructor
    }

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        btnSave = view.findViewById(R.id.btnSave);
        etPet   = view.findViewById(R.id.etPet);
        etRaspberry = view.findViewById(R.id.etRaspberry);

        firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference profileRef = firebaseDatabase.getReference("users").child(FirebaseAuth.getInstance().getUid().toString());


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profileRef.child("pet").setValue(etPet.getText().toString());
                profileRef.child("raspberrypiIp").setValue(etRaspberry.getText().toString());
                //((MainActivity) getActivity()).mainFragment();

            }
        });
        return view;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}

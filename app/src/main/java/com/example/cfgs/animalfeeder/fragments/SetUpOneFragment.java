package com.example.cfgs.animalfeeder.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.ButtonBarLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.brouding.blockbutton.BlockButton;
import com.example.cfgs.animalfeeder.R;
import com.example.cfgs.animalfeeder.activities.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SetUpOneFragment extends Fragment {

    BlockButton btnNext;
    private OnFragmentInteractionListener mListener;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference userRef;
    EditText etRaspberryIp;

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
        View view = inflater.inflate(R.layout.fragment_set_up_one, container, false);

        btnNext = view.findViewById(R.id.btnSetUpOne);
        etRaspberryIp = view.findViewById(R.id.etRaspberryIpSetUp);

        firebaseDatabase = FirebaseDatabase.getInstance();
        userRef = firebaseDatabase.getReference("users/" + FirebaseAuth.getInstance().getUid().toString());

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userRef.child("raspberryIp").setValue(etRaspberryIp.getText().toString());
                ((MainActivity)getActivity()).setUpTwoFragment();
            }
        });

        return view;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}

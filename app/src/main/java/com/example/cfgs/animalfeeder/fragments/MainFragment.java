package com.example.cfgs.animalfeeder.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brouding.blockbutton.BlockButton;
import com.example.cfgs.animalfeeder.R;
import com.example.cfgs.animalfeeder.activities.MainActivity;

public class MainFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    BlockButton btnProfile, btnSettings, btnBowl, btnList, btnChat;


    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        btnProfile  = view.findViewById(R.id.btnProfile);
        btnSettings = view.findViewById(R.id.btnSettings);
        btnBowl     = view.findViewById(R.id.btnBowl);
        btnList     = view.findViewById(R.id.btnList);
        btnChat     = view.findViewById(R.id.btnChat);

        btnProfile.setEnabled(true);
        btnSettings.setEnabled(true);
        btnList.setEnabled(true);
        btnChat.setEnabled(true);
        btnBowl.setEnabled(true);


        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).profileFragment();
            }
        });


        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).settingFragment();

            }
        });

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).listFragment();
            }
        });

        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).chatFragment();

            }
        });

        btnBowl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).bowlFragment();
            }
        });

        return view;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

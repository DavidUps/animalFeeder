package com.example.cfgs.animalfeeder.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cfgs.animalfeeder.R;
import com.example.cfgs.animalfeeder.adapters.FoodListAdapter;
import com.example.cfgs.animalfeeder.models.FoodList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ListFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    RecyclerView rvFoodList;
    ArrayList<FoodList> foodList = new ArrayList<>();
    FirebaseDatabase firebaseDatabase;
    FoodListAdapter foodListAdapter;


    public ListFragment() {
        // Required empty public constructor
    }

    public static ListFragment newInstance(String param1, String param2) {
        ListFragment fragment = new ListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseDatabase = FirebaseDatabase.getInstance();

        //Inicializar adapter.
        foodListAdapter = new FoodListAdapter(foodList);

        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, container, false);

        rvFoodList = view.findViewById(R.id.rvFoodList);

        rvFoodList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        DatabaseReference foodListRef = firebaseDatabase.getReference("users/"+ FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("foodTime");
        ChildEventListener messageList = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                foodList.add(new FoodList(dataSnapshot.child("dayStamp").getValue().toString(),dataSnapshot.child("timeStamp").getValue().toString()));
                foodListAdapter.setList(foodList);
                foodListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        foodListRef.addChildEventListener(messageList);

        rvFoodList.setAdapter(foodListAdapter);

        return view;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}

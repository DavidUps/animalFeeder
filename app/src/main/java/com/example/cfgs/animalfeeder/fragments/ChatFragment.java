package com.example.cfgs.animalfeeder.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cfgs.animalfeeder.R;
import com.example.cfgs.animalfeeder.models.Messages;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ChatFragment extends Fragment {

    FirebaseStorage firebaseStorage;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference chatReference;
    ArrayList<Messages> alMessages;

    private OnFragmentInteractionListener mListener;

    public ChatFragment() {
    }

    public static ChatFragment newInstance(String param1, String param2) {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage  = FirebaseStorage.getInstance("gs://animalfeeder-cae79.appspot.com/profileImage");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        getmMessages();

        return view;
    }

    private void getmMessages() {
        chatReference = firebaseDatabase.getReference("chats");

        final ChildEventListener messageList = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                StorageReference imgRef = firebaseStorage.getReference(dataSnapshot.child("photo").getValue().toString());
                alMessages.add(new Messages(dataSnapshot.child("from").getValue().toString(),
                                            dataSnapshot.child("pet").getValue().toString(),
                                            dataSnapshot.child("message").getValue().toString(),
                                            imgRef));
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

        chatReference.addChildEventListener(messageList);
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}

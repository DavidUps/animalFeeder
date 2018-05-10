package com.example.cfgs.animalfeeder.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.cfgs.animalfeeder.R;
import com.example.cfgs.animalfeeder.adapters.MessageAdapter;
import com.example.cfgs.animalfeeder.models.MessageUpdate;
import com.example.cfgs.animalfeeder.models.Messages;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChatFragment extends Fragment {

    FirebaseStorage firebaseStorage;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference chatReference;
    ArrayList<Messages> alMessages = new ArrayList<>();
    MessageAdapter messageAdapter;
    DatabaseReference userRef;

    FloatingActionButton btnSend;
    EditText etMessage;
    RecyclerView rvMessage;

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
        firebaseStorage  = FirebaseStorage.getInstance("gs://animalfeeder-cae79.appspot.com");
        userRef = firebaseDatabase.getReference("users").child(FirebaseAuth.getInstance().getUid().toString());
        messageAdapter = new MessageAdapter(alMessages, getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        btnSend   = view.findViewById(R.id.fabSend);
        etMessage = view.findViewById(R.id.etSendMessage);
        rvMessage = view.findViewById(R.id.rvMensaggeList);

        rvMessage.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateMessage();
                etMessage.setText("");
            }
        });

        getMessages();

        return view;
    }

    private void updateMessage() {
        if(!etMessage.getText().toString().equals("")){
            /*String name = "", pet = "";
            userRef.child("name").setValue(name);
            userRef.child("pet").setValue(pet);*/
            Messages message = new Messages(
                    "david",
                    "pollas",
                    etMessage.getText().toString(),
                    "profileImage/"+FirebaseAuth.getInstance().getCurrentUser().getUid().toString()+".jpg");
            Map<String, Object> messageValues = message.toMap();
            Map<String, Object> childUpdates = new HashMap<>();
            childUpdates.put(userRef.push().getKey(), messageValues);
            chatReference.updateChildren(childUpdates);
        }
    }

    private void getMessages() {
        chatReference = firebaseDatabase.getReference("chats/");
        if(chatReference != null){
            final ChildEventListener messageList = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    alMessages.add(new Messages(
                            dataSnapshot.child("name").getValue().toString(),
                            dataSnapshot.child("pet").getValue().toString(),
                            dataSnapshot.child("message").getValue().toString(),
                            dataSnapshot.child("idProfile").getValue().toString()));
                    rvMessage.setAdapter(messageAdapter);

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


    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}

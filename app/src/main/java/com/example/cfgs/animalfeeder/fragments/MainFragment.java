package com.example.cfgs.animalfeeder.fragments;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Toast;

import com.brouding.blockbutton.BlockButton;
import com.example.cfgs.animalfeeder.R;
import com.example.cfgs.animalfeeder.activities.MainActivity;
import com.example.cfgs.animalfeeder.models.Feed;
import com.example.cfgs.animalfeeder.models.Messages;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    BlockButton btnProfile, btnSettings, btnBowl, btnList, btnChat;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference userRef;
    WebView motor;

    public MainFragment() {
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
        motor       = view.findViewById(R.id.motor);

        btnProfile.setEnabled(true);
        btnSettings.setEnabled(true);
        btnList.setEnabled(true);
        btnChat.setEnabled(true);
        btnBowl.setEnabled(true);

        firebaseDatabase = FirebaseDatabase.getInstance();
        userRef = firebaseDatabase.getReference("users").child(FirebaseAuth.getInstance().getUid().toString()).child("foodTime");



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
           @RequiresApi(api = Build.VERSION_CODES.O)
           @Override
           public void onClick(View view) {

               Calendar calendar = Calendar.getInstance();
               String currentHour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
               Date date = calendar.getTime();
               String currentDay = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());
               if (currentDay.equals("Monday")){
                   currentDay = "Lunes";
               }
               if (currentDay.equals("Tuesday")){
                   currentDay = "Martes";
               }
               if (currentDay.equals("Wednesday")){
                   currentDay = "Miercoles";
               }
               if (currentDay.equals("Thursday")){
                   currentDay = "Jueves";
               }
               if (currentDay.equals("Friday")) {
                   currentDay = "Viernes";
               }
               if (currentDay.equals("Saturday")) {
                   currentDay = "Sabado";
               }
               if (currentDay.equals("Sunday")) {
                   currentDay = "Domingo";
               }

               Feed feed = new Feed(currentDay, currentHour);
               Map<String, Object> messageValues = feed.toMap();
               Map<String, Object> childUpdates = new HashMap<>();
               childUpdates.put(userRef.push().getKey(), messageValues);
               userRef.updateChildren(childUpdates);

               Toast.makeText(getActivity(), "Day: " + currentDay + " Hour: " + currentHour, Toast.LENGTH_SHORT).show();

               motor.loadUrl("http://192.168.1.7");
           }
       });
       return view;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

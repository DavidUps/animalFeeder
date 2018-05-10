package com.example.cfgs.animalfeeder.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.cfgs.animalfeeder.R;
import com.example.cfgs.animalfeeder.models.Messages;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolderDatos>{

    ArrayList<Messages> alMessageAdapter;
    Context context;

    public MessageAdapter(ArrayList<Messages> alMessageAdapter, Context context){
        this.context = context;
        this.alMessageAdapter = alMessageAdapter;
    }

    @Override
    public MessageAdapter.ViewHolderDatos onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_chat_adapter, parent, false);
        return new MessageAdapter.ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(MessageAdapter.ViewHolderDatos holder, int position) {
        Messages messages = alMessageAdapter.get(position);
        holder.message.setText(messages.getMessage());
        holder.name.setText(messages.getName());
        holder.pet.setText(messages.getPet());
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance("gs://animalfeeder-cae79.appspot.com/");
        StorageReference storage = firebaseStorage.getReference(messages.getImgProfile());
        Glide.with(context)
                .using(new FirebaseImageLoader())
                .load(storage)
                .error(R.drawable.profile)
                .into(holder.imgProfile);

    }

    @Override
    public int getItemCount(){
        return alMessageAdapter.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder{
        ImageView   imgProfile;
        TextView    message, name, pet;

        public ViewHolderDatos(View itemView) {
            super(itemView);
            imgProfile  = itemView.findViewById(R.id.ivProfileAdapter);
            message     = itemView.findViewById(R.id.tvMessageAdapter);
            name        = itemView.findViewById(R.id.tvNameAdapter);
            pet         = itemView.findViewById(R.id.tvPetAdapter);
        }
    }
}

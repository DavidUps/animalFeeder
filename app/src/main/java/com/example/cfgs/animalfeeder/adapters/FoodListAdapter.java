package com.example.cfgs.animalfeeder.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cfgs.animalfeeder.R;
import com.example.cfgs.animalfeeder.models.FoodList;

import java.util.ArrayList;


public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.ViewHolderDatos> {

    private ArrayList<FoodList> alFoodList;

    public FoodListAdapter(ArrayList<FoodList> alFoodList) {
        this.alFoodList = alFoodList;
    }

    @Override
    public FoodListAdapter.ViewHolderDatos onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_list_adapter, parent, false);
        return new FoodListAdapter.ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(FoodListAdapter.ViewHolderDatos holder, int position) {
        FoodList foodList= alFoodList.get(position);
        holder.day.setText(foodList.getDay());
        holder.hour.setText(foodList.getTime());
    }

    @Override
    public int getItemCount() {
        return alFoodList.size();
    }

    public class ViewHolderDatos extends  RecyclerView.ViewHolder{


        TextView day, hour;

        public ViewHolderDatos(View itemView) {
            super(itemView);
            hour   = itemView.findViewById(R.id.tvHour);
            day = itemView.findViewById(R.id.tvDay);
        }

    }

    public void setList(ArrayList<FoodList> alFoodLists) {
        this.alFoodList = alFoodLists;
    }
}


package com.example.ingredientsmeal.menuFragments.adapters;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ingredientsmeal.R;
import com.example.ingredientsmeal.menuFragments.DinnerFragment;
import com.example.ingredientsmeal.menuFragments.TypeOfDinnerFragment;


import java.util.ArrayList;

public class DinnerRecyclerViewAdapter extends RecyclerView.Adapter<DinnerRecyclerViewAdapter.DinnerViewHolder> {

    public String FirebaseFirstStepDinner;
    public ArrayList<String> dinnerValueArrayList;

    public DinnerRecyclerViewAdapter(ArrayList<String> myValues) {
        this.dinnerValueArrayList = myValues;
    }

    @Override
    public DinnerRecyclerViewAdapter.DinnerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_dinner, parent, false);
        return new DinnerRecyclerViewAdapter.DinnerViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(DinnerRecyclerViewAdapter.DinnerViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.textUniversal.setText(dinnerValueArrayList.get(position));

        holder.cardViewDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Bundle data = new Bundle();
                data.putString("FirebaseFirstStepDinner", FirebaseFirstStepDinner);
                data.putString("FirebaseFirstSecondDinner", dinnerValueArrayList.get(position));

                TypeOfDinnerFragment typeOfDinnerFragment = new TypeOfDinnerFragment();
                typeOfDinnerFragment.setArguments(data);

                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentMenu, typeOfDinnerFragment).addToBackStack(null).commit();

            }
        });
    }

    @Override
    public int getItemCount() {
        return dinnerValueArrayList.size();
    }

    class DinnerViewHolder extends RecyclerView.ViewHolder {

        private TextView textUniversal;
        private CardView cardViewDinner;

        public DinnerViewHolder(View itemView) {
            super(itemView);

            FirebaseFirstStepDinner = DinnerFragment.getFirebaseFirstStepDinner();
            textUniversal = (TextView) itemView.findViewById(R.id.textViewDinnerName);
            cardViewDinner = (CardView) itemView.findViewById(R.id.cardViewDinner);
        }
    }
}
package com.example.ingredientsmeal.menuFragments.adapters;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ingredientsmeal.R;
import com.example.ingredientsmeal.menuFragments.DishFragment;
import com.example.ingredientsmeal.menuFragments.TypeOfDinnerFragment;

import java.util.ArrayList;

public class TypeRecyclerViewAdapter extends RecyclerView.Adapter<TypeRecyclerViewAdapter.TypeViewHolder> {

    public String FirebaseFirstStepDinner, FirebaseFirstSecondDinner;
    public ArrayList<String> typeValueArrayList;

    public TypeRecyclerViewAdapter(ArrayList<String> myValues) {
        this.typeValueArrayList = myValues;
    }

    @Override
    public TypeRecyclerViewAdapter.TypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_type, parent, false);
        return new TypeRecyclerViewAdapter.TypeViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(TypeRecyclerViewAdapter.TypeViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.textUniversal.setText(typeValueArrayList.get(position));

        holder.cardViewDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Bundle data = new Bundle();

                data.putString("FirebaseFirstStepDinner", FirebaseFirstStepDinner);
                data.putString("FirebaseFirstSecondDinner", FirebaseFirstSecondDinner);
                data.putString("FirebaseFirstthirdDinner", typeValueArrayList.get(position));

                Fragment fragment = new DishFragment();
                fragment.setArguments(data);

                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentMenu, fragment).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return typeValueArrayList.size();
    }

    class TypeViewHolder extends RecyclerView.ViewHolder {

        private TextView textUniversal;
        private CardView cardViewDinner;

        public TypeViewHolder(View itemView) {
            super(itemView);

            FirebaseFirstStepDinner = TypeOfDinnerFragment.getFirebaseFirstStepDinner();
            FirebaseFirstSecondDinner = TypeOfDinnerFragment.getFirebaseFirstSecondDinner();
            textUniversal = (TextView) itemView.findViewById(R.id.textViewTypeName);
            cardViewDinner = (CardView) itemView.findViewById(R.id.cardViewType);
        }
    }
}
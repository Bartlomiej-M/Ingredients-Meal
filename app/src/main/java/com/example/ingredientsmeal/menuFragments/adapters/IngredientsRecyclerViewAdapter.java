package com.example.ingredientsmeal.menuFragments.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ingredientsmeal.R;

import java.util.ArrayList;

public class IngredientsRecyclerViewAdapter extends RecyclerView.Adapter<IngredientsRecyclerViewAdapter.IngredientsViewHolder> {
    public ArrayList<String> myValues;
    public ArrayList<String> key;

    public IngredientsRecyclerViewAdapter(ArrayList<String> ingredientskeyArrayList, ArrayList<String> myValues) {
        this.key = ingredientskeyArrayList;
        this.myValues = myValues;
    }

    @Override
    public IngredientsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_ingredients, parent, false);
        return new IngredientsViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(IngredientsViewHolder holder, int position) {
        holder.numberIngredients.setText(key.get(position));
        holder.textIngredients.setText(myValues.get(position));
    }

    @Override
    public int getItemCount() {
        return myValues.size();
    }

    public static class IngredientsViewHolder extends RecyclerView.ViewHolder {

        private TextView textIngredients, numberIngredients;

        public IngredientsViewHolder(View itemView) {
            super(itemView);
            textIngredients = (TextView) itemView.findViewById(R.id.textIngredients);
            numberIngredients = (TextView) itemView.findViewById(R.id.numberIngredients);
        }
    }
}
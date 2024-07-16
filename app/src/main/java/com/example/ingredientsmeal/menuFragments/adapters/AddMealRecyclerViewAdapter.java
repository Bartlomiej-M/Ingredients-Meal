package com.example.ingredientsmeal.menuFragments.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ingredientsmeal.R;
import java.util.ArrayList;

public class AddMealRecyclerViewAdapter extends RecyclerView.Adapter<AddMealRecyclerViewAdapter.AddMealViewHolder> {
    public ArrayList<String> myValues;
    public ArrayList<String> key;

    public AddMealRecyclerViewAdapter(ArrayList<String> ingredientskeyArrayList, ArrayList<String> myValues) {
        this.key = ingredientskeyArrayList;
        this.myValues = myValues;
    }

    @Override
    public AddMealRecyclerViewAdapter.AddMealViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_addmeal, parent, false);
        return new AddMealRecyclerViewAdapter.AddMealViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(AddMealRecyclerViewAdapter.AddMealViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.numberIngredients.setText(key.get(position));
        holder.textIngredients.setText(myValues.get(position));
    }

    @Override
    public int getItemCount() {
        return myValues.size();
    }

    public static class AddMealViewHolder extends RecyclerView.ViewHolder {

        private TextView textIngredients, numberIngredients;

        public AddMealViewHolder(View itemView) {
            super(itemView);
            textIngredients = (TextView) itemView.findViewById(R.id.textAddmeal);
            numberIngredients = (TextView) itemView.findViewById(R.id.numberAddmeal);

        }
    }
}
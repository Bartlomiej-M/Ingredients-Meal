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

public class RecipeRecyclerViewAdapter extends RecyclerView.Adapter<RecipeRecyclerViewAdapter.RecipeViewHolder> {

    public ArrayList<String> recipeValueArrayList;
    public ArrayList<String> recipeKeyArrayList;

    public RecipeRecyclerViewAdapter(ArrayList<String> recipeKeyArrayList, ArrayList<String> recipeValueArrayList) {
        this.recipeKeyArrayList = recipeKeyArrayList;
        this.recipeValueArrayList = recipeValueArrayList;
    }

    @Override
    public RecipeRecyclerViewAdapter.RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_recipe, parent, false);
        return new RecipeRecyclerViewAdapter.RecipeViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(RecipeRecyclerViewAdapter.RecipeViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.textNumberRecipe.setText(recipeKeyArrayList.get(position));
        holder.textViewRecipeName.setText(recipeValueArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return recipeValueArrayList.size();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder {

        private TextView textNumberRecipe, textViewRecipeName;
        private CardView cardViewRecipe;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            textNumberRecipe = (TextView) itemView.findViewById(R.id.textNumberRecipe);
            textViewRecipeName = (TextView) itemView.findViewById(R.id.textViewRecipeName);
            cardViewRecipe = (CardView) itemView.findViewById(R.id.cardViewRecipe);
        }
    }
}
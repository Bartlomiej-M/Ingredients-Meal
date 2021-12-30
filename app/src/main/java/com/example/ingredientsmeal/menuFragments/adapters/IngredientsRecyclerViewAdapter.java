package com.example.ingredientsmeal.menuFragments.adapters;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ingredientsmeal.R;

import java.util.ArrayList;
import java.util.List;

public class IngredientsRecyclerViewAdapter extends RecyclerView.Adapter<IngredientsRecyclerViewAdapter.IngredientsViewHolder> {
    public ArrayList<String> myValues;
    public ArrayList<String> key;
    public ArrayList<String> myResult = new ArrayList<>();

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
    public void onBindViewHolder(IngredientsViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.numberIngredients.setText(key.get(position));
        holder.textIngredients.setText(myValues.get(position));

        holder.checkBox.setOnClickListener( new View.OnClickListener()
        {
            public void onClick( View v )
            {
                CheckBox cb = ( CheckBox ) v;
                if ( cb.isChecked() )
                {
                    myValues.get(position);
                    myResult.add(myValues.get(position));
                }
                else
                {
                    myValues.get(position);
                    myResult.remove(myValues.get(position));
                }
            }
        });

    }

    public List<String> listofselectedactivites(){
        return myResult;
    }

    @Override
    public int getItemCount() {
        return myValues.size();
    }

    public static class IngredientsViewHolder extends RecyclerView.ViewHolder {

        private TextView textIngredients, numberIngredients;
        CheckBox checkBox;

        public IngredientsViewHolder(View itemView) {
            super(itemView);
            textIngredients = (TextView) itemView.findViewById(R.id.textIngredients);
            numberIngredients = (TextView) itemView.findViewById(R.id.numberIngredients);
            checkBox = (CheckBox) itemView.findViewById(R.id.chbContent);
        }
    }
}
package com.example.ingredientsmeal.menuFragments.adapters;

import android.annotation.SuppressLint;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ingredientsmeal.R;

import com.example.ingredientsmeal.models.HistoryModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;


public class HistoryRecyclerViewAdapter extends FirebaseRecyclerAdapter<HistoryModel, HistoryRecyclerViewAdapter.HistoryViewHolder> {


    public HistoryRecyclerViewAdapter(@NonNull FirebaseRecyclerOptions<HistoryModel> options) {
        super(options);
    }


    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull HistoryRecyclerViewAdapter.HistoryViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull HistoryModel model) {

        holder.rMealName.setText(getRef(position).getKey());
        holder.hDate.setText(model.getHistoryCurrentDate() + ", " + model.getHistoryCurrentTime());
        holder.hType.setText(model.getHistoryTypeMeal());
        holder.hPreference.setText(model.getHistoryPreferenceMeal());
        holder.hNazwa.setText(getRef(position).getKey());

        holder.btnSeeHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });

    }

    @NonNull
    @Override
    public HistoryRecyclerViewAdapter.HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_history, parent, false);

        return new HistoryRecyclerViewAdapter.HistoryViewHolder(view);
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder {

        TextView rMealName, hDate, hType, hPreference, hNazwa;
        CardView btnSeeHistory;
        LinearLayout second, third;


        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            second = (LinearLayout) itemView.findViewById(R.id.second);
            third = (LinearLayout) itemView.findViewById(R.id.third);
            btnSeeHistory = (CardView) itemView.findViewById(R.id.btnSeeHistory);

            rMealName = (TextView) itemView.findViewById(R.id.rMealName);
            hDate = (TextView) itemView.findViewById(R.id.hDate);
            hType = (TextView) itemView.findViewById(R.id.hType);
            hPreference = (TextView) itemView.findViewById(R.id.hPreference);
            hNazwa = (TextView) itemView.findViewById(R.id.hNazwa);


        }
    }
}

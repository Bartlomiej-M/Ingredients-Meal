package com.example.ingredientsmeal.menuFragments.adapters;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ingredientsmeal.R;
import com.example.ingredientsmeal.menuFragments.DetailsFragment;
import com.example.ingredientsmeal.menuFragments.DishFragment;
import com.example.ingredientsmeal.menuFragments.menuModels.DishModel;
import com.example.ingredientsmeal.models.SendMessageModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class MyMessagesRecyclerViewAdapter extends FirebaseRecyclerAdapter<SendMessageModel, MyMessagesRecyclerViewAdapter.MyMessagesViewHolder> {

    public MyMessagesRecyclerViewAdapter(@NonNull FirebaseRecyclerOptions<SendMessageModel> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull MyMessagesRecyclerViewAdapter.MyMessagesViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull SendMessageModel model) {

        //holder.imageRecipientField.setImageDrawable(R.drawable.ic_baseline_account_image_48);

        holder.dataField.setText(getRef(position).getKey());

        holder.contentsField.setText(model.getContents());

        holder.consignorField.setText(model.getConsignor());

    }

    @NonNull
    @Override
    public MyMessagesRecyclerViewAdapter.MyMessagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_message, parent, false);

        return new MyMessagesRecyclerViewAdapter.MyMessagesViewHolder(view);
    }

    class MyMessagesViewHolder extends RecyclerView.ViewHolder {
        ImageView imageRecipientField;
        TextView dataField, consignorField, contentsField;


        public MyMessagesViewHolder(@NonNull View itemView) {
            super(itemView);
            imageRecipientField = (ImageView) itemView.findViewById(R.id.imageRecipientField);
            dataField = (TextView) itemView.findViewById(R.id.dataField);
            consignorField = (TextView) itemView.findViewById(R.id.consignorField);
            contentsField = (TextView) itemView.findViewById(R.id.contentsField);

        }
    }
}
package com.example.ingredientsmeal.menuFragments.holders;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ingredientsmeal.R;
import com.squareup.picasso.Picasso;

public class DishViewHolder extends RecyclerView.ViewHolder {

    View view;

    public DishViewHolder(@NonNull View itemView) {
        super(itemView);

        view = itemView;

    }

    public void setdetails(Context context, String title, String image){
        TextView mtitletv = view.findViewById(R.id.rTitleView);
        ImageView mImagetv = view.findViewById(R.id.rImageView);

        mtitletv.setText(title);
        Picasso.get().load(image).into(mImagetv);

        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        itemView.startAnimation(animation);
    }
}

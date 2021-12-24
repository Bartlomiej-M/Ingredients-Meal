package com.example.ingredientsmeal.menuFragments.adapters;

import static android.media.CamcorderProfile.get;

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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ingredientsmeal.R;
import com.example.ingredientsmeal.menuFragments.DetailsFragment;
import com.example.ingredientsmeal.menuFragments.DishFragment;
import com.example.ingredientsmeal.menuFragments.menuModels.DishModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class DishViewAdapter extends FirebaseRecyclerAdapter<DishModel, DishViewAdapter.DishViewHolder>{

    public String FirebaseFirstStepDinner, FirebaseFirstSecondDinner, FirebaseFirstthirdDinner;

    public DishViewAdapter(@NonNull FirebaseRecyclerOptions<DishModel> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull DishViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull DishModel model) {

        Picasso.get().load(model.getZdjecie()).into(holder.img);
        holder.rMealName.setText(getRef(position).getKey());
        holder.rPoziom.setText(model.getPoziom());
        holder.rCzasTrwania.setText(model.getCzasTrwania());
        holder.rEnergia_w_Porcji.setText(model.getEnergiaWporcji());
        holder.rPorcja.setText(model.getPorcja());

        holder.btnSeeDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                holder.btnSeeDetails.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AppCompatActivity activity = (AppCompatActivity) view.getContext();
                        DetailsFragment detailsFragment = new DetailsFragment();
                        Bundle data = new Bundle();

                        data.putString("FirebaseFirstStepDinner", FirebaseFirstStepDinner);
                        data.putString("FirebaseFirstSecondDinner", FirebaseFirstSecondDinner);
                        data.putString("FirebaseFirstthirdDinner", FirebaseFirstthirdDinner);
                        data.putString("FirebaseFirstfourthDinner", getRef(position).getKey());

                        detailsFragment.setArguments(data);
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentMenu, detailsFragment).addToBackStack(null).commit();
                    }
                });
            }
        });
    }

    @NonNull
    @Override
    public DishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_dish, parent, false);

        return new DishViewHolder(view);
    }

    class DishViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView rMealName, rPoziom, rCzasTrwania, rEnergia_w_Porcji, rPorcja;
        CardView btnSeeDetails;

        public DishViewHolder(@NonNull View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.rImageView);
            rMealName = (TextView) itemView.findViewById(R.id.rMealName);
            rPoziom = (TextView) itemView.findViewById(R.id.rPoziom);
            rCzasTrwania = (TextView) itemView.findViewById(R.id.rCzasTrwania);
            rEnergia_w_Porcji = (TextView) itemView.findViewById(R.id.rEnergia_w_Porcji);
            rPorcja = (TextView) itemView.findViewById(R.id.rPorcja);
            btnSeeDetails = (CardView) itemView.findViewById(R.id.btnSeeDetails);

            FirebaseFirstStepDinner = DishFragment.getFirebaseFirstStepDinner();
            FirebaseFirstSecondDinner = DishFragment.getFirebaseFirstSecondDinner();
            FirebaseFirstthirdDinner = DishFragment.getFirebaseFirstthirdDinner();
        }
    }
}

package com.example.ingredientsmeal.menuFragments.adapters;

import static android.media.CamcorderProfile.get;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ingredientsmeal.R;
import com.example.ingredientsmeal.dialog.CustomSendIngredientsDialog;
import com.example.ingredientsmeal.menuFragments.DetailsFragment;
import com.example.ingredientsmeal.menuFragments.DishFragment;
import com.example.ingredientsmeal.menuFragments.menuModels.DishModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class DishRecyclerViewAdapter extends FirebaseRecyclerAdapter<DishModel, DishRecyclerViewAdapter.DishViewHolder>{

    public String FirebaseFirstStepDinner, FirebaseFirstSecondDinner, FirebaseFirstthirdDinner;
    public String userOnline;

    public DishRecyclerViewAdapter(@NonNull FirebaseRecyclerOptions<DishModel> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull DishViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull DishModel model) {
        getConsignorName();
        Picasso.get().load(model.getZdjecie()).into(holder.img);
        holder.rMealName.setText(getRef(position).getKey());
        holder.rPoziom.setText(model.getPoziom());
        holder.rCzasTrwania.setText(model.getCzasTrwania());
        holder.rEnergia_w_Porcji.setText(model.getEnergiaWporcji());
        holder.rPorcja.setText(model.getPorcja());

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

        holder.ibtn_addLiked.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View itemView) {

                holder.ibtn_addLiked.setVisibility(View.GONE);
                DatabaseReference fromPath = FirebaseDatabase.getInstance()
                        .getReference()
                        .child(FirebaseFirstStepDinner)
                        .child(FirebaseFirstSecondDinner)
                        .child(FirebaseFirstthirdDinner)
                        .child(getRef(position).getKey());


                DatabaseReference toPath = FirebaseDatabase.getInstance()
                        .getReference()
                        .child("Users")
                        .child(userOnline)
                        .child("Liked")
                        .child(getRef(position).getKey());

                copyRecord(itemView.getContext(), fromPath, toPath);

            }

        });

    }

    private void copyRecord(Context context, Query fromPath, final DatabaseReference toPath) {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                toPath.setValue(dataSnapshot.getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isComplete()) {
                            Toast.makeText(context, "Udało się dodać do ulubionych", Toast.LENGTH_LONG).show();
                        } else {

                            Toast.makeText(context, "Nie udało się dodać do ulubionych", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };
        fromPath.addListenerForSingleValueEvent(valueEventListener);
    }

    public void getConsignorName() {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        Query query = rootRef.child("Users").orderByChild("uid").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid().toString());

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    userOnline = ds.getKey();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        query.addListenerForSingleValueEvent(valueEventListener);
    }

    @NonNull
    @Override
    public DishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_dish, parent, false);

        return new DishViewHolder(view);
    }

    class DishViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        ImageButton ibtn_addLiked;
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
            btnSeeDetails = (CardView) itemView.findViewById(R.id.cardViewDish);

            ibtn_addLiked = (ImageButton) itemView.findViewById(R.id.ibtn_addLiked);

            FirebaseFirstStepDinner = DishFragment.getFirebaseFirstStepDinner();
            FirebaseFirstSecondDinner = DishFragment.getFirebaseFirstSecondDinner();
            FirebaseFirstthirdDinner = DishFragment.getFirebaseFirstthirdDinner();
        }
    }
}

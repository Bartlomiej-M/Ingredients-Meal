package com.example.ingredientsmeal.menuFragments.adapters;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ingredientsmeal.R;
import com.example.ingredientsmeal.menuFragments.DetailsFragment;
import com.example.ingredientsmeal.menuFragments.LikedFragment;
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

public class LikedRecyclerViewAdapter extends FirebaseRecyclerAdapter<DishModel, LikedRecyclerViewAdapter.LikedViewHolder> {

    public String FirebaseFirstStepUsers, FirebaseSecondStepUsers, FirebaseThirdStepUsers;
    public String userOnline;

    EventListener listener;

    public interface EventListener {

        void setVisibleEmptyVISIBLE();

    }

    public LikedRecyclerViewAdapter(@NonNull FirebaseRecyclerOptions<DishModel> options, EventListener listener) {
        super(options);
        this.listener = listener;
    }


    @Override
    protected void onBindViewHolder(@NonNull LikedRecyclerViewAdapter.LikedViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull DishModel model) {

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

                data.putString("FirebaseFirstStepDinner", FirebaseFirstStepUsers);
                data.putString("FirebaseFirstSecondDinner", FirebaseSecondStepUsers);
                data.putString("FirebaseFirstthirdDinner", FirebaseThirdStepUsers);
                data.putString("FirebaseFirstfourthDinner", getRef(position).getKey());

                detailsFragment.setArguments(data);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentMenu, detailsFragment).addToBackStack(null).commit();
            }
        });

        holder.ibtn_delLiked.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View itemView) {

                FirebaseDatabase.getInstance().getReference()
                        .child("Users")
                        .child(userOnline)
                        .child("Liked")
                        .child(getRef(position).getKey()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("Delete", "Notification has been deleted");

                        } else {

                        }

                        if (getItemCount() == 0) {
                            listener.setVisibleEmptyVISIBLE();
                        }
                        notifyItemRemoved(position);
                        notifyDataSetChanged();
                    }
                });
            }
        });
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
    public LikedRecyclerViewAdapter.LikedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_liked, parent, false);

        return new LikedRecyclerViewAdapter.LikedViewHolder(view);
    }

    class LikedViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        ImageButton ibtn_delLiked;
        TextView rMealName, rPoziom, rCzasTrwania, rEnergia_w_Porcji, rPorcja;
        CardView btnSeeDetails;

        public LikedViewHolder(@NonNull View itemView) {
            super(itemView);

            img = (ImageView) itemView.findViewById(R.id.rImageView);

            rMealName = (TextView) itemView.findViewById(R.id.rMealName);
            rPoziom = (TextView) itemView.findViewById(R.id.rPoziom);
            rCzasTrwania = (TextView) itemView.findViewById(R.id.rCzasTrwania);
            rEnergia_w_Porcji = (TextView) itemView.findViewById(R.id.rEnergia_w_Porcji);
            rPorcja = (TextView) itemView.findViewById(R.id.rPorcja);

            btnSeeDetails = (CardView) itemView.findViewById(R.id.cardViewLiked);

            ibtn_delLiked = (ImageButton) itemView.findViewById(R.id.ibtn_delLiked);

            FirebaseFirstStepUsers = LikedFragment.getFirebaseFirstStepUsers();
            FirebaseSecondStepUsers = LikedFragment.getFirebaseSecondStepUsers();
            FirebaseThirdStepUsers = LikedFragment.getFirebaseThirdStepUsers();
        }

    }

}

package com.example.ingredientsmeal.menuFragments.adapters;

import static android.media.CamcorderProfile.get;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ingredientsmeal.R;
import com.example.ingredientsmeal.menuFragments.menuModels.DishModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class DishViewAdapter extends FirebaseRecyclerAdapter<DishModel, DishViewAdapter.DishViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
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
                        Toast.makeText(view.getContext(), getRef(position).getKey(), Toast.LENGTH_LONG).show();
                    }
                });
            } });
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
        }
    }
}

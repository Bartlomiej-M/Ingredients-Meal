package com.example.ingredientsmeal.menuFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ingredientsmeal.R;
import com.example.ingredientsmeal.menuFragments.adapters.DishViewAdapter;
import com.example.ingredientsmeal.menuFragments.menuModels.DishModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class DishFragment extends Fragment implements View.OnClickListener {

    private Button btnArrowBackDish;
    private RecyclerView recview;
    private DishViewAdapter dishViewAdapter;

    private static String FirebaseFirstStepDinner;
    private static String FirebaseFirstSecondDinner;
    private static String FirebaseFirstthirdDinner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dish, container, false);

        recview = (RecyclerView) rootView.findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseFirstStepDinner = getArguments().getString("FirebaseFirstStepDinner");
        FirebaseFirstSecondDinner = getArguments().getString("FirebaseFirstSecondDinner");
        FirebaseFirstthirdDinner = getArguments().getString("FirebaseFirstthirdDinner");

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView toolbarTitleTextView = (TextView) toolbar.findViewById(R.id.toolbarTitleTextView);
        toolbarTitleTextView.setText(FirebaseFirstthirdDinner.toString());
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);


        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference hotelRef = rootRef.child(FirebaseFirstStepDinner).child(FirebaseFirstSecondDinner).child(FirebaseFirstthirdDinner);

        FirebaseRecyclerOptions<DishModel> options =
                new FirebaseRecyclerOptions.Builder<DishModel>()
                        .setQuery(hotelRef, DishModel.class)
                        .build();
        dishViewAdapter = new DishViewAdapter(options);
        recview.setAdapter(dishViewAdapter);

        return rootView;
    }

    public static String getFirebaseFirstStepDinner() {

        return FirebaseFirstStepDinner;
    }

    public static String getFirebaseFirstSecondDinner() {
        return FirebaseFirstSecondDinner;
    }

    public static String getFirebaseFirstthirdDinner() {
        return FirebaseFirstthirdDinner;
    }

    @Override
    public void onStart() {
        super.onStart();
        dishViewAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        dishViewAdapter.stopListening();
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = null;

        switch (v.getId()) {


        }
    }

    public void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_left);
        fragmentTransaction.replace(R.id.fragmentMenu, fragment).commit();
        fragmentTransaction.addToBackStack(null);
    }

}
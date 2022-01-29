package com.example.ingredientsmeal.menuFragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ingredientsmeal.R;
import com.example.ingredientsmeal.menuFragments.adapters.AddedMealsRecyclerViewAdapter;
import com.example.ingredientsmeal.menuFragments.adapters.DishRecyclerViewAdapter;
import com.example.ingredientsmeal.menuFragments.menuModels.DishModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MyAddedMealsFragment extends Fragment {
    private RecyclerView recview;
    private AddedMealsRecyclerViewAdapter addedMealsRecyclerViewAdapter;



    private static String FirebaseFirstStepDinner;
    private static String FirebaseFirstSecondDinner;
    private static String FirebaseFirstthirdDinner;

    String userOnline;

    public MyAddedMealsFragment(String userOnline) {
        this.userOnline = userOnline;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_added_meals, container, false);

        recview = (RecyclerView) rootView.findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(getContext()));

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView toolbarTitleTextView = (TextView) toolbar.findViewById(R.id.toolbarTitleTextView);
        toolbarTitleTextView.setText("Twoje potrawy");
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        setHasOptionsMenu(true);

        FirebaseFirstStepDinner = "Users";
        FirebaseFirstSecondDinner = userOnline;
        FirebaseFirstthirdDinner = "Dinner";

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference hotelRef = rootRef.child(FirebaseFirstStepDinner).child(FirebaseFirstSecondDinner).child(FirebaseFirstthirdDinner);

        FirebaseRecyclerOptions<DishModel> options =
                new FirebaseRecyclerOptions.Builder<DishModel>()
                        .setQuery(hotelRef, DishModel.class)
                        .build();
        addedMealsRecyclerViewAdapter = new AddedMealsRecyclerViewAdapter(options);
        recview.setAdapter(addedMealsRecyclerViewAdapter);


        return rootView;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.nav_search).setVisible(false);
        menu.findItem(R.id.my_Messages).setVisible(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        addedMealsRecyclerViewAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        addedMealsRecyclerViewAdapter.stopListening();
    }

    public void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_left);
        fragmentTransaction.replace(R.id.fragmentMenu, fragment).commit();
        fragmentTransaction.addToBackStack(null);
    }

}
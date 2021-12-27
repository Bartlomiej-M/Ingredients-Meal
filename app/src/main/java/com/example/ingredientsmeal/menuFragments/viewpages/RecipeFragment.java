package com.example.ingredientsmeal.menuFragments.viewpages;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.ingredientsmeal.R;
import com.example.ingredientsmeal.menuFragments.adapters.RecipeRecyclerViewAdapter;
import com.example.ingredientsmeal.menuFragments.adapters.TypeRecyclerViewAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class RecipeFragment extends Fragment {

    public static String FirebaseFirstStepDinner, FirebaseFirstSecondDinner, FirebaseFirstthirdDinner, FirebaseFirstfourthDinner;

    private Bundle bundle;
    public ArrayList<String> recipeValueArrayList = new ArrayList<>();
    public ArrayList<String> recipeKeyArrayList = new ArrayList<>();
    private RecyclerView recipeRecyclerView;

    public RecipeFragment() {
        // Required empty public constructor
    }


    public static RecipeFragment newInstance(String param1, String param2) {
        RecipeFragment fragment = new RecipeFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            bundle = getArguments();
            FirebaseFirstStepDinner = (String) bundle.getSerializable("FirebaseFirstStepDinner");
            FirebaseFirstSecondDinner = (String) bundle.getSerializable("FirebaseFirstSecondDinner");
            FirebaseFirstthirdDinner = (String) bundle.getSerializable("FirebaseFirstthirdDinner");
            FirebaseFirstfourthDinner = (String) bundle.getSerializable("FirebaseFirstfourthDinner");
        }
    }

    public static String getFirebaseFirstStepDinner() { return FirebaseFirstStepDinner; }

    public static String getFirebaseFirstSecondDinner() { return FirebaseFirstSecondDinner; }

    public static String getFirebaseFirstthirdDinner() { return FirebaseFirstthirdDinner; }

    public static String getFirebaseFirstfourthDinner() { return FirebaseFirstfourthDinner; }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe, container, false);

        recipeRecyclerView = (RecyclerView) rootView.findViewById(R.id.recipeRecyclerView);

        openViewRecipe();

        return rootView;
    }

    public void openViewRecipe() {
        recipeValueArrayList.clear();

        DatabaseReference rootRef2 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference hotelRef2 = rootRef2.child(FirebaseFirstStepDinner)
                .child(FirebaseFirstSecondDinner)
                .child(FirebaseFirstthirdDinner)
                .child(FirebaseFirstfourthDinner)
                .child("Przygotowanie");

        ValueEventListener eventListener2 = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    String key = childSnapshot.getKey();
                    String value = (String) childSnapshot.getValue();

                    recipeValueArrayList.add(value);
                    recipeKeyArrayList.add(key);

                    RecipeRecyclerViewAdapter adapter = new RecipeRecyclerViewAdapter(recipeKeyArrayList, recipeValueArrayList);

                    recipeRecyclerView.setHasFixedSize(true);
                    recipeRecyclerView.setAdapter(adapter);

                    LinearLayoutManager llm = new LinearLayoutManager(getContext());
                    llm.setOrientation(LinearLayoutManager.VERTICAL);
                    recipeRecyclerView.setLayoutManager(llm);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        hotelRef2.addListenerForSingleValueEvent(eventListener2);
    }

    public void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_left);
        fragmentTransaction.replace(R.id.fragmentMenu, fragment).commit();
        fragmentTransaction.addToBackStack(null);
    }
}
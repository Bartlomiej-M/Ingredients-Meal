package com.example.ingredientsmeal.menuFragments.viewpages;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.ingredientsmeal.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class RecipeFragment extends Fragment {

    public String FirebaseFirstStepDinner, FirebaseFirstSecondDinner, FirebaseFirstthirdDinner, FirebaseFirstfourthDinner;

    public ListView recipeListView;
    private Bundle bundle;
    public ArrayList<String> recipeArrayList = new ArrayList<>();
    public ArrayAdapter<String> recipeArrayAdapter;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe, container, false);

        recipeArrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.row_list, recipeArrayList);

        recipeListView = (ListView) rootView.findViewById(R.id.recipeListView);
        recipeListView.setAdapter(recipeArrayAdapter);

        openViewRecipe();

        return rootView;
    }


    public void openViewRecipe() {
        recipeArrayList.clear();
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

                    recipeArrayList.add(key + " " + value);
                    recipeArrayAdapter.notifyDataSetChanged();
                }

                recipeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                recipeArrayAdapter.notifyDataSetChanged();
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
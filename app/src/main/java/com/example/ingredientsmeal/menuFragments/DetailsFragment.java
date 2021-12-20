package com.example.ingredientsmeal.menuFragments;

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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ingredientsmeal.R;
import com.example.ingredientsmeal.startFragments.ForgotPasswordFragment;
import com.example.ingredientsmeal.startFragments.WelcomeFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;


public class DetailsFragment extends Fragment implements View.OnClickListener {

    private Button btnArrowBackdetails, btnViewRecipe, btnViewIngredients;
    public String FirebaseFirstStepDinner, FirebaseFirstSecondDinner, FirebaseFirstthirdDinner, FirebaseFirstfourthDinner;
    private ListView detailsListView;

    private ArrayList<String> detailsArrayList = new ArrayList<>();
    public ArrayAdapter<String> detailsArrayAdapter;

    public DetailsFragment() {
        // Required empty public constructor
    }


    public static DetailsFragment newInstance(String param1, String param2) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);

        btnViewRecipe = (Button) rootView.findViewById(R.id.btnViewRecipe);
        btnViewRecipe.setOnClickListener(this);

        btnViewIngredients = (Button) rootView.findViewById(R.id.btnViewIngredients);
        btnViewIngredients.setOnClickListener(this);

        detailsArrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.row_list, detailsArrayList);

        detailsListView = (ListView) rootView.findViewById(R.id.detailsListView);
        detailsListView.setAdapter(detailsArrayAdapter);

        FirebaseFirstStepDinner = getArguments().getString("FirebaseFirstStepDinner");
        FirebaseFirstSecondDinner = getArguments().getString("FirebaseFirstSecondDinner");
        FirebaseFirstthirdDinner = getArguments().getString("FirebaseFirstthirdDinner");
        FirebaseFirstfourthDinner = getArguments().getString("FirebasefourthStepeDinner");

        return rootView;
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = null;

        switch (v.getId()) {
            case R.id.btnViewRecipe:
                openViewRecipe();
                break;
            case R.id.btnViewIngredients:
                openViewIngredients();

                break;
        }
    }

    public void openViewRecipe() {
        detailsArrayList.clear();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference hotelRef = rootRef.child(FirebaseFirstStepDinner)
                .child(FirebaseFirstSecondDinner)
                .child(FirebaseFirstthirdDinner)
                .child(FirebaseFirstfourthDinner)
                .child("Przygotowanie");

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    String key = childSnapshot.getKey();
                    String value = (String) childSnapshot.getValue();

                    detailsArrayList.add(key + " " + value);
                    detailsArrayAdapter.notifyDataSetChanged();
                }

                detailsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                detailsArrayAdapter.notifyDataSetChanged();
            }
        };
        hotelRef.addListenerForSingleValueEvent(eventListener);
    }

    public void openViewIngredients() {
        detailsArrayList.clear();
        DatabaseReference rootRef2 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference hotelRef2 = rootRef2.child(FirebaseFirstStepDinner)
                .child(FirebaseFirstSecondDinner)
                .child(FirebaseFirstthirdDinner)
                .child(FirebaseFirstfourthDinner)
                .child("Składniki");

        ValueEventListener eventListener2 = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    String key = childSnapshot.getKey();
                    String value = (String) childSnapshot.getValue();

                    detailsArrayList.add(key + " " + value);
                    detailsArrayAdapter.notifyDataSetChanged();
                }

                detailsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                detailsArrayAdapter.notifyDataSetChanged();
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
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ingredientsmeal.R;
import com.example.ingredientsmeal.menu.MenuFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;


public class TypeOfDinnerFragment extends Fragment implements View.OnClickListener {

    private ListView typeOfDinnerListView;
    private ArrayList<String> typeOfDinnerArrayList = new ArrayList<>();
    private ArrayAdapter<String> typeOfDinnerArrayAdapter;

    private Button btnArrowBackType;
    private String FirebaseFirstStepDinner, FirebaseFirstSecondDinner, FirebaseFirstthirdDinner;


    public TypeOfDinnerFragment() {
        // Required empty public constructor
    }


    public static TypeOfDinnerFragment newInstance(String param1, String param2) {
        TypeOfDinnerFragment fragment = new TypeOfDinnerFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_type_of_dinner, container, false);

        typeOfDinnerArrayList.clear();
        typeOfDinnerArrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.row_list, typeOfDinnerArrayList);

        typeOfDinnerListView = (ListView) rootView.findViewById(R.id.typeOfDinnerListView);
        typeOfDinnerListView.setAdapter(typeOfDinnerArrayAdapter);

        displayCategoryDinnerList();//wywołanie metody wyswietlajace liste typów dań z wybranej kategori posiłkowych

        return rootView;
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = null;

        switch (v.getId()) {
        }
    }

    private void displayCategoryDinnerList() {
        FirebaseFirstStepDinner = getArguments().getString("FirebaseFirstStepDinner");
        FirebaseFirstSecondDinner = getArguments().getString("FirebaseFirstSecondDinner");

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference hotelRef = rootRef.child(FirebaseFirstStepDinner).child(FirebaseFirstSecondDinner);

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    String key2 = childSnapshot.getKey();

                    typeOfDinnerArrayList.add(key2);
                    typeOfDinnerArrayAdapter.notifyDataSetChanged();

                    typeOfDinnerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Bundle data = new Bundle();

                            data.putString("FirebaseFirstStepDinner", FirebaseFirstStepDinner);
                            data.putString("FirebaseFirstSecondDinner", FirebaseFirstSecondDinner);
                            data.putString("FirebaseFirstthirdDinner", typeOfDinnerArrayList.get(position));

                            Fragment fragment = new DishFragment();
                            fragment.setArguments(data);
                            loadFragment(fragment);
                        }
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                typeOfDinnerArrayAdapter.notifyDataSetChanged();
            }
        };
        hotelRef.addListenerForSingleValueEvent(eventListener);
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_left);
        fragmentTransaction.replace(R.id.fragmentMenu, fragment).commit();
        fragmentTransaction.addToBackStack(null);
    }
}
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ingredientsmeal.R;
import com.example.ingredientsmeal.dialog.CustomToastDialog;
import com.example.ingredientsmeal.menu.MenuFragment;
import com.example.ingredientsmeal.menuFragments.adapters.DinnerRecyclerViewAdapter;
import com.example.ingredientsmeal.menuFragments.adapters.TypeRecyclerViewAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;


public class TypeOfDinnerFragment extends Fragment implements View.OnClickListener {

    public ArrayList<String> typeValueArrayList = new ArrayList<>();
    private RecyclerView typeRecyclerView;

    private static String FirebaseFirstStepDinner, FirebaseFirstSecondDinner;

    public TypeOfDinnerFragment() {
        // Required empty public constructor
    }

    public static String getFirebaseFirstStepDinner() {
        return FirebaseFirstStepDinner;
    }

    public static String getFirebaseFirstSecondDinner() {
        return FirebaseFirstSecondDinner;
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

        typeRecyclerView = (RecyclerView) rootView.findViewById(R.id.typeRecyclerView);

        FirebaseFirstStepDinner = getArguments().getString("FirebaseFirstStepDinner");
        FirebaseFirstSecondDinner = getArguments().getString("FirebaseFirstSecondDinner");

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView toolbarTitleTextView = (TextView) toolbar.findViewById(R.id.toolbarTitleTextView);
        toolbarTitleTextView.setText(FirebaseFirstSecondDinner.toString());
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);

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

        typeValueArrayList.clear();

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference hotelRef = rootRef.child(FirebaseFirstStepDinner).child(FirebaseFirstSecondDinner);

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    String key = childSnapshot.getKey();
                    typeValueArrayList.add(key);

                    TypeRecyclerViewAdapter adapter = new TypeRecyclerViewAdapter(typeValueArrayList);

                    typeRecyclerView.setHasFixedSize(true);
                    typeRecyclerView.setAdapter(adapter);

                    LinearLayoutManager llm = new LinearLayoutManager(getContext());
                    llm.setOrientation(LinearLayoutManager.VERTICAL);
                    typeRecyclerView.setLayoutManager(llm);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                new CustomToastDialog(getContext(), R.string.msg_toast_internet_problem, R.id.custom_toast_message, R.layout.toast_warning).show();
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
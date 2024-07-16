package com.example.ingredientsmeal.menuFragments;

import android.annotation.SuppressLint;
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
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ingredientsmeal.R;
import com.example.ingredientsmeal.dialog.CustomToastDialog;
import com.example.ingredientsmeal.menuFragments.adapters.DinnerRecyclerViewAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DinnerFragment extends Fragment implements View.OnClickListener {

    public ArrayList<String> dinnerValueArrayList = new ArrayList<>();
    private RecyclerView dinnerRecyclerView;
    private static String FirebaseFirstStepDinner = "Dinner";

    public DinnerFragment() {
        // Required empty public constructor
    }

    public static DinnerFragment newInstance(String param1, String param2) {
        DinnerFragment fragment = new DinnerFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    public static String getFirebaseFirstStepDinner() { return FirebaseFirstStepDinner; }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dinner, container, false);

        dinnerRecyclerView = (RecyclerView) rootView.findViewById(R.id.dinnerRecyclerView);

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView toolbarTitleTextView = (TextView) toolbar.findViewById(R.id.toolbarTitleTextView);
        toolbarTitleTextView.setText("Rodzaj");
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);

        displayCategoryDinnerList();

        return rootView;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = null;

        switch (v.getId()) {

        }
    }

    private void displayCategoryDinnerList() {

        dinnerValueArrayList.clear();

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference hotelRef = rootRef.child(FirebaseFirstStepDinner);

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {

                    String key = childSnapshot.getKey();
                    dinnerValueArrayList.add(key);

                    DinnerRecyclerViewAdapter adapter = new DinnerRecyclerViewAdapter(dinnerValueArrayList);

                    dinnerRecyclerView.setHasFixedSize(true);
                    dinnerRecyclerView.setAdapter(adapter);

                    LinearLayoutManager llm = new LinearLayoutManager(getContext());
                    llm.setOrientation(LinearLayoutManager.VERTICAL);
                    dinnerRecyclerView.setLayoutManager(llm);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
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
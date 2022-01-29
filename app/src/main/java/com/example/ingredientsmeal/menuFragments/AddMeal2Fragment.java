package com.example.ingredientsmeal.menuFragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ingredientsmeal.MainActivity;
import com.example.ingredientsmeal.R;
import com.example.ingredientsmeal.dialog.CustomToastDialog;
import com.example.ingredientsmeal.menuFragments.adapters.AddMealRecyclerViewAdapter;
import com.example.ingredientsmeal.menuFragments.adapters.DinnerRecyclerViewAdapter;
import com.example.ingredientsmeal.menuFragments.adapters.IngredientsRecyclerViewAdapter;
import com.example.ingredientsmeal.models.AddModel;
import com.example.ingredientsmeal.models.SendMessageModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class AddMeal2Fragment extends Fragment implements View.OnClickListener {

    private String userOnline, meal, duration, portion, level, energy;
    public static int position = 0;
    Button btnSecondStepAddMeal;
    Button addButton;

    EditText editTextGetValue;

    public ArrayList<String> step2ValueArrayList = new ArrayList<>();
    public ArrayList<String> step2ValuekeyArrayList = new ArrayList<>();
    private RecyclerView step2RecyclerView;
    public AddMealRecyclerViewAdapter adapter;


    public AddMeal2Fragment(String userOnline, String meal, String duration, String portion, String level, String energy) {
        // Required empty public constructor
        this.userOnline = userOnline;
        this.meal = meal;
        this.duration = duration;
        this.portion = portion;
        this.level = level;
        this.energy = energy;
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
        View rootView = inflater.inflate(R.layout.fragment_add_meal2, container, false);

        Log.d("Tag:", String.valueOf(userOnline + meal + duration + portion + level + energy));

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        TextView toolbarTitleTextView = (TextView) toolbar.findViewById(R.id.toolbarTitleTextView);
        toolbarTitleTextView.setText("Krok 2");
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        setHasOptionsMenu(true);

        btnSecondStepAddMeal = (Button) rootView.findViewById(R.id.btnSecondStepAddMeal);
        btnSecondStepAddMeal.setOnClickListener(this);

        step2RecyclerView = (RecyclerView) rootView.findViewById(R.id.step2RecyclerView);

        addButton = rootView.findViewById(R.id.addButton);
        addButton.setOnClickListener(this);
        editTextGetValue = rootView.findViewById(R.id.editTextGetValue);

        return rootView;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.nav_search).setVisible(false);
        menu.findItem(R.id.my_Messages).setVisible(true);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        Fragment fragment = null;

        switch (v.getId()) {
            case R.id.btnSecondStepAddMeal:
                fragment = new AddMeal3Fragment(userOnline, meal, duration, portion, level, energy);
                loadFragment(fragment);
                break;
            case R.id.addButton:
                position = position + 1;
                addToIngredients(position);
                break;
        }
    }

    public void addToIngredients(int position){
        String getValue = editTextGetValue.getText().toString().trim();

        if (getValue.isEmpty()) {
            editTextGetValue.setError("Pole nie może być puste.");
            editTextGetValue.requestFocus();
            return;
        }

        FirebaseDatabase.getInstance().getReference("Users")
                .child(userOnline).child("Dinner").child(meal)
                .child("Składniki").child(String.valueOf(position)).setValue(getValue).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    openViewIngredients();
                    editTextGetValue.setText("");
                } else {
                    Toast.makeText(getContext(), "Problem z dodaniem składników", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void openViewIngredients() {
        step2ValueArrayList.clear();
        step2ValuekeyArrayList.clear();

        DatabaseReference rootRef2 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference hotelRef2 = rootRef2.child("Users")
                .child(userOnline)
                .child("Dinner")
                .child(meal)
                .child("Składniki");

        ValueEventListener eventListener2 = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {

                    String key = childSnapshot.getKey();
                    String value = (String) childSnapshot.getValue();

                    step2ValuekeyArrayList.add(key);
                    step2ValueArrayList.add(value);

                    adapter = new AddMealRecyclerViewAdapter(step2ValuekeyArrayList, step2ValueArrayList);
                    step2RecyclerView.setHasFixedSize(true);
                    step2RecyclerView.setAdapter(adapter);

                    LinearLayoutManager llm = new LinearLayoutManager(getContext());
                    llm.setOrientation(LinearLayoutManager.VERTICAL);
                    step2RecyclerView.setLayoutManager(llm);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Problem z internetem sprawdzi połączenie", Toast.LENGTH_LONG).show();
            }
        };
        hotelRef2.addListenerForSingleValueEvent(eventListener2);
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentMenu, fragment).commit();
        fragmentTransaction.addToBackStack(null);
    }
}
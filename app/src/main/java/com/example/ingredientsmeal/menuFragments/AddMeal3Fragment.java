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
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ingredientsmeal.R;
import com.example.ingredientsmeal.dialog.CustomToastDialog;
import com.example.ingredientsmeal.menu.MenuFragment;
import com.example.ingredientsmeal.menuFragments.adapters.AddMealRecyclerViewAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class AddMeal3Fragment extends Fragment implements View.OnClickListener {

    private String userOnline, meal, duration, portion, level, energy;
    int position = 0;

    private Button btnLastStepAddMeal;
    private Button btnAddPreparation;
    private TextInputEditText editTextGetValue;

    public ArrayList<String> step3ValueArrayList = new ArrayList<>();
    public ArrayList<String> step3ValuekeyArrayList = new ArrayList<>();
    private RecyclerView step3RecyclerView;
    public AddMealRecyclerViewAdapter adapter;

    public AddMeal3Fragment(String userOnline, String meal, String duration, String portion, String level, String energy) {
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
        View rootView = inflater.inflate(R.layout.fragment_add_meal3, container, false);

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        TextView toolbarTitleTextView = (TextView) toolbar.findViewById(R.id.toolbarTitleTextView);
        toolbarTitleTextView.setText("Krok 3");
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        setHasOptionsMenu(true);

        btnLastStepAddMeal = (Button) rootView.findViewById(R.id.btnLastStepAddMeal);
        btnLastStepAddMeal.setOnClickListener(this);

        step3RecyclerView = (RecyclerView) rootView.findViewById(R.id.step3RecyclerView);

        btnAddPreparation = rootView.findViewById(R.id.btnAddPreparation);
        btnAddPreparation.setOnClickListener(this);

        editTextGetValue =  (TextInputEditText) rootView.findViewById(R.id.editText1);

        return rootView;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.nav_search).setVisible(false);
        menu.findItem(R.id.my_Messages).setVisible(true);
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = null;
        switch (v.getId()) {
            case R.id.btnAddPreparation:
                position = position + 1;
                addToPreparation(position);
                break;
            case R.id.btnLastStepAddMeal:
                new CustomToastDialog(getContext(), R.string.msg_toast_succ_add_meal, R.id.custom_toast_message, R.layout.toast_success).show();
                fragment = new MenuFragment();
                loadFragment(fragment);
                break;
        }
    }

    public void addToPreparation(int position) {

        String getValue = editTextGetValue.getText().toString().trim();

        if (getValue.isEmpty()) {
            editTextGetValue.setError("Pole nie może być puste.");
            editTextGetValue.requestFocus();
            return;
        }

        FirebaseDatabase.getInstance().getReference("Users")
                .child(userOnline).child("Dinner").child(meal)
                .child("Przygotowanie").child(String.valueOf(position)).setValue(getValue).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    openViewPreparation();
                    editTextGetValue.setText("");
                } else {
                    new CustomToastDialog(getContext(), R.string.msg_toast_err_add_meal_plane, R.id.custom_toast_message, R.layout.toast_warning).show();
                }
            }
        });
    }

    public void openViewPreparation() {
        step3ValueArrayList.clear();
        step3ValuekeyArrayList.clear();

        DatabaseReference rootRef2 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference hotelRef2 = rootRef2.child("Users")
                .child(userOnline)
                .child("Dinner")
                .child(meal)
                .child("Przygotowanie");

        ValueEventListener eventListener2 = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {

                    String key = childSnapshot.getKey();
                    String value = (String) childSnapshot.getValue();

                    step3ValuekeyArrayList.add(key);
                    step3ValueArrayList.add(value);

                    adapter = new AddMealRecyclerViewAdapter(step3ValuekeyArrayList, step3ValueArrayList);
                    step3RecyclerView.setHasFixedSize(true);
                    step3RecyclerView.setAdapter(adapter);

                    LinearLayoutManager llm = new LinearLayoutManager(getContext());
                    llm.setOrientation(LinearLayoutManager.VERTICAL);
                    step3RecyclerView.setLayoutManager(llm);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                new CustomToastDialog(getContext(), R.string.msg_toast_internet_problem, R.id.custom_toast_message, R.layout.toast_warning).show();
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
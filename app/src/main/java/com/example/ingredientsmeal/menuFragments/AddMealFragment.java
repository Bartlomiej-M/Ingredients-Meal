package com.example.ingredientsmeal.menuFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ingredientsmeal.R;
import com.example.ingredientsmeal.dialog.CustomToastDialog;
import com.example.ingredientsmeal.models.AddModel;
import com.example.ingredientsmeal.startFragments.WelcomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.FirebaseDatabase;

public class AddMealFragment extends Fragment implements View.OnClickListener {

    private String userOnline, meal, duration, portion, level, energy;
    private TextInputEditText mealNameTextReg, durationTextReg, portionTextReg, levelTextReg, energyInPortionsTextReg;
    private Button btnFirstStepAddMeal;

    public AddMealFragment(String userOnline) {
        this.userOnline = userOnline;
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
        View rootView = inflater.inflate(R.layout.fragment_add_meal, container, false);

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView toolbarTitleTextView = (TextView) toolbar.findViewById(R.id.toolbarTitleTextView);
        toolbarTitleTextView.setText("Dodaj własną potrawe");
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        setHasOptionsMenu(true);

        mealNameTextReg = (TextInputEditText) rootView.findViewById(R.id.MealNameTextReg);
        durationTextReg = (TextInputEditText) rootView.findViewById(R.id.DurationTextReg);
        portionTextReg = (TextInputEditText) rootView.findViewById(R.id.PortionTextReg);
        levelTextReg = (TextInputEditText) rootView.findViewById(R.id.LevelTextReg);
        energyInPortionsTextReg = (TextInputEditText) rootView.findViewById(R.id.EnergyInPortionsTextReg);

        btnFirstStepAddMeal = (Button) rootView.findViewById(R.id.btnFirstStepAddMeal);
        btnFirstStepAddMeal.setOnClickListener(this);

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
            case R.id.btnFirstStepAddMeal:

                addToMeal();
                fragment = new AddMeal2Fragment(userOnline, meal, duration, portion, level, energy);
                loadFragment(fragment);
                break;
        }
    }

    public void addToMeal(){

        meal = mealNameTextReg.getText().toString().trim();
        duration = durationTextReg.getText().toString().trim();
        portion = portionTextReg.getText().toString().trim();
        level = levelTextReg.getText().toString().trim();
        energy = energyInPortionsTextReg.getText().toString().trim();

        AddModel addModel = new AddModel(duration , portion , level , energy);

        FirebaseDatabase.getInstance().getReference("Users")
                .child(userOnline).child("Dinner").child(meal)
                .setValue(addModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "Udało się dodać inicjalizacje potrawy", Toast.LENGTH_LONG).show();

                } else {
                    new CustomToastDialog(getContext(), R.string.msg_toast_err_message, R.id.custom_toast_message, R.layout.toast_warning).show();
                }
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentMenu, fragment).commit();
        fragmentTransaction.addToBackStack(null);
    }


}
package com.example.ingredientsmeal.menuFragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ingredientsmeal.R;
import com.example.ingredientsmeal.menu.MainMenu;
import com.example.ingredientsmeal.menu.MenuFragment;
import com.example.ingredientsmeal.startFragments.ForgotPasswordFragment;
import com.example.ingredientsmeal.startFragments.WelcomeFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class DinnerFragment extends Fragment implements View.OnClickListener {

    private ListView categoryDinnerList;
    private ArrayList<String> categoryArrayList = new ArrayList<>();
    private ArrayAdapter<String> categoryArrayAdapter;

    private String FirebaseFirstStepDinner;
    private android.app.Fragment ActionBarActivity;

    public DinnerFragment() {
        // Required empty public constructor
    }

    public static DinnerFragment newInstance(String param1, String param2) {
        DinnerFragment fragment = new DinnerFragment();
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

    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dinner, container, false);
        categoryArrayList.clear();
        categoryArrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.row_list, categoryArrayList);
        categoryDinnerList = (ListView) rootView.findViewById(R.id.categoryDinnerList);
        categoryDinnerList.setAdapter(categoryArrayAdapter);

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        displayCategoryDinnerList();//wywołanie metody wyswietlajacej liste kategori posiłkowych

        return rootView;
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.custom_menu_toolbar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.nav_search) {
            Toast.makeText(getContext(), "DUPADUPADUPA", Toast.LENGTH_LONG).show();
        }
/*        switch(item.getItemId()){
            case android.R.id.home:
                getActivity().onBackPressed();
        }*/
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = null;

        switch (v.getId()) {

        }
    }

    private void displayCategoryDinnerList() {

        categoryArrayList.clear();

        FirebaseFirstStepDinner = "Dinner";

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference hotelRef = rootRef.child(FirebaseFirstStepDinner);

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {

                    String key = childSnapshot.getKey();
                    categoryArrayList.add(key);
                    categoryArrayAdapter.notifyDataSetChanged();
                }

                categoryDinnerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Bundle data = new Bundle();
                        data.putString("FirebaseFirstStepDinner", FirebaseFirstStepDinner);
                        data.putString("FirebaseFirstSecondDinner", categoryArrayList.get(position));

                        Fragment fragment = new TypeOfDinnerFragment();
                        fragment.setArguments(data);
                        loadFragment(fragment);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                categoryArrayAdapter.notifyDataSetChanged();
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
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
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ingredientsmeal.R;
import com.example.ingredientsmeal.menuFragments.DetailsFragment;
import com.example.ingredientsmeal.menuFragments.adapters.RecyclerViewAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class IngredientsFragment extends Fragment implements View.OnClickListener {

    public String FirebaseFirstStepDinner, FirebaseFirstSecondDinner, FirebaseFirstthirdDinner, FirebaseFirstfourthDinner;
    private Button btnSendIngredients;

    private Bundle bundle;

    public ArrayList<String> ingredientsArrayList = new ArrayList<>();
    public ArrayList<String> ingredientskeyArrayList = new ArrayList<>();
    RecyclerView myView;

    public IngredientsFragment() {
        // Required empty public constructor
    }

    public static IngredientsFragment newInstance(int param1, String param2) {
        IngredientsFragment fragment = new IngredientsFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_ingredients, container, false);

        btnSendIngredients = (Button) rootView.findViewById(R.id.btnSendIngredients);
        btnSendIngredients.setOnClickListener(this);

        myView =  (RecyclerView) rootView.findViewById(R.id.ingredientsListView);
        openViewIngredients();

        return rootView;
    }


    public void openViewIngredients() {
        ingredientsArrayList.clear();

        DatabaseReference rootRef2 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference hotelRef2 = rootRef2.child(FirebaseFirstStepDinner.toString())
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

                    ingredientskeyArrayList.add(key);
                    ingredientsArrayList.add(value);


                    RecyclerViewAdapter adapter = new RecyclerViewAdapter(ingredientskeyArrayList, ingredientsArrayList);

                    myView.setHasFixedSize(true);
                    myView.setAdapter(adapter);

                    LinearLayoutManager llm = new LinearLayoutManager(getContext());
                    llm.setOrientation(LinearLayoutManager.VERTICAL);
                    myView.setLayoutManager(llm);

                }

/*                ingredientsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                });*/
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            /*    ingredientsArrayAdapter.notifyDataSetChanged();*/
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

    @Override
    public void onClick(View v) {
        Fragment fragment = null;

        switch (v.getId()) {
            case R.id.btnSendIngredients:
                Toast.makeText(getContext(), "jnfijndfjngf", Toast.LENGTH_LONG).show();
                break;

        }
    }
}
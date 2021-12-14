package com.example.ingredientsmeal.menuFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ingredientsmeal.R;
import com.example.ingredientsmeal.menuFragments.holders.DishViewHolder;
import com.example.ingredientsmeal.menuFragments.menuModels.DishModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.Map;


public class DishFragment extends Fragment implements View.OnClickListener {

    private ListView listView3;

    private ArrayList<String> myArrayList = new ArrayList<>();



    private Button btnArrowBackDish;

    RecyclerView mRecyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;


    public DishFragment() {
        // Required empty public constructor
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
        View rootView = inflater.inflate(R.layout.fragment_dish, container, false);

        ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.row_list, myArrayList);

        listView3 = (ListView) rootView.findViewById(R.id.listView3);
        listView3.setAdapter(myArrayAdapter);

        btnArrowBackDish = (Button) rootView.findViewById(R.id.btnArrowBackDish);
        btnArrowBackDish.setOnClickListener(this);


        String Dinner = getArguments().getString("dinner");
        String Dish = getArguments().getString("dish");

        Log.d("DishFragment:Dinner:", String.valueOf(Dinner));
        Log.d("DishFragment:Dish:", String.valueOf(Dish));

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference hotelRef = rootRef.child("Dinner").child(Dinner).child(Dish);

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    String key = childSnapshot.getKey();
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    myArrayList.add(key);
                    myArrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                myArrayAdapter.notifyDataSetChanged();
            }
        };
        hotelRef.addListenerForSingleValueEvent(eventListener);


        return rootView;
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = null;

        switch (v.getId()) {
            case R.id.btnArrowBackDish:
                fragment = new DinnerFragment();
                loadFragment(fragment);
                break;

        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_left);
        fragmentTransaction.replace(R.id.fragmentMenu, fragment).commit();
        fragmentTransaction.addToBackStack(null);
    }

}
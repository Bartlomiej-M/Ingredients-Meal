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

    private ListView listView2;

    private ArrayList<String> myArrayList = new ArrayList<>();

    private DatabaseReference mRef;

    private Button btnArrowBackType;

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

        ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.row_list, myArrayList);

        listView2 = (ListView) rootView.findViewById(R.id.listView2);
        listView2.setAdapter(myArrayAdapter);

        btnArrowBackType = (Button) rootView.findViewById(R.id.btnArrowBackType);
        btnArrowBackType.setOnClickListener(this);

        String Dinner = getArguments().getString("dinner");
        Log.d("TypeOfDinnerFragment", String.valueOf(Dinner));
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference hotelRef = rootRef.child("Dinner").child(Dinner);

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    String key2 = childSnapshot.getKey();
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    myArrayList.add(key2);
                    myArrayAdapter.notifyDataSetChanged();

                    listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Bundle data = new Bundle();

                            data.putString("dinner", Dinner);
                            data.putString("dish", myArrayList.get(position));

                            Fragment fragment = new DishFragment();
                            fragment.setArguments(data);
                            loadFragment(fragment);
                        }
                    });

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
            case R.id.btnArrowBackType:
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
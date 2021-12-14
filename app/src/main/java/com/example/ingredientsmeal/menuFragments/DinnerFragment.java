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

    private ListView listView1;

    private ArrayList<String> myArrayList = new ArrayList<>();

    private DatabaseReference mRef;

    private Button btnArrowBackDinner;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dinner, container, false);

        ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.row_list, myArrayList);

        listView1 = (ListView) rootView.findViewById(R.id.listView1);
        listView1.setAdapter(myArrayAdapter);

        btnArrowBackDinner = (Button) rootView.findViewById(R.id.btnArrowBackDinner);
        btnArrowBackDinner.setOnClickListener(this);

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference hotelRef = rootRef.child("Dinner");

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    String key = childSnapshot.getKey();
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    myArrayList.add(key);
                    myArrayAdapter.notifyDataSetChanged();
                }

                listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Bundle data = new Bundle();
                        Log.d("Nazwa zmiennej", myArrayList.get(position));
                        data.putString("dinner", myArrayList.get(position));

                        Fragment fragment = new TypeOfDinnerFragment();
                        fragment.setArguments(data);
                        loadFragment(fragment);
                    }
                });
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
            case R.id.btnArrowBackDinner:
                fragment = new MenuFragment();
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
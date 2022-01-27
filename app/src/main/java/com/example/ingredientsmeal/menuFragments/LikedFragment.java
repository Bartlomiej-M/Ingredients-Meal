package com.example.ingredientsmeal.menuFragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ingredientsmeal.R;
import com.example.ingredientsmeal.menuFragments.adapters.LikedRecyclerViewAdapter;
import com.example.ingredientsmeal.menuFragments.menuModels.DishModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LikedFragment extends Fragment implements View.OnClickListener, LikedRecyclerViewAdapter.EventListener {

    private RecyclerView recview;
    private LikedRecyclerViewAdapter likedRecyclerViewAdapter;
    public String userOnline;
    private static String FirebaseFirstStepUsers;
    private static String FirebaseSecondStepUsers;
    private static String FirebaseThirdStepUsers;
    public ConstraintLayout constraintEmptyLayout;


    public LikedFragment(String userOnline) {
        // Required empty public constructor
        this.userOnline = userOnline;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_liked, container, false);

        constraintEmptyLayout = rootView.findViewById(R.id.recyclerViewLikedNONE);

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView toolbarTitleTextView = (TextView) toolbar.findViewById(R.id.toolbarTitleTextView);
        toolbarTitleTextView.setText("Polubione");
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);

        recview = (RecyclerView) rootView.findViewById(R.id.recviewLiked);
        recview.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseFirstStepUsers = "Users";
        FirebaseSecondStepUsers = userOnline;
        FirebaseThirdStepUsers = "Liked";

        checkVisibility(FirebaseSecondStepUsers);

        DatabaseReference rootRef2 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference hotelRef = rootRef2.child(String.valueOf(FirebaseFirstStepUsers)).child(String.valueOf(FirebaseSecondStepUsers)).child(String.valueOf(FirebaseThirdStepUsers));

        FirebaseRecyclerOptions<DishModel> options =
                new FirebaseRecyclerOptions.Builder<DishModel>()
                        .setQuery(hotelRef, DishModel.class)
                        .build();

        likedRecyclerViewAdapter = new LikedRecyclerViewAdapter(options,  this);
        Log.d("str333", String.valueOf(likedRecyclerViewAdapter.getItemCount()));
        recview.setAdapter(likedRecyclerViewAdapter);

        return rootView;
    }

     public void setVisibleEmptyVISIBLE() {
        constraintEmptyLayout.setVisibility(View.VISIBLE);
    }

    public void setVisibleEmptyGONE() {
        constraintEmptyLayout.setVisibility(View.GONE);
    }

    public static String getFirebaseFirstStepUsers() {
        return FirebaseFirstStepUsers;
    }

    public static String getFirebaseSecondStepUsers() {
        return FirebaseSecondStepUsers;
    }

    public static String getFirebaseThirdStepUsers() {
        return FirebaseThirdStepUsers;
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
    public void onStart() {
        super.onStart();
        likedRecyclerViewAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        likedRecyclerViewAdapter.stopListening();
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = null;

        switch (v.getId()) {

        }
    }

    public void checkVisibility(String FirebaseSecondStepUsers) {

        DatabaseReference root = FirebaseDatabase.getInstance().getReference();
        DatabaseReference users = root.child("Users").child(FirebaseSecondStepUsers);
        Log.d("str2", String.valueOf(FirebaseSecondStepUsers));
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.child("Liked").exists()) {
                    setVisibleEmptyGONE();
                }else{
                    setVisibleEmptyVISIBLE();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    public void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_left);
        fragmentTransaction.replace(R.id.fragmentMenu, fragment).commit();
        fragmentTransaction.addToBackStack(null);
    }
}
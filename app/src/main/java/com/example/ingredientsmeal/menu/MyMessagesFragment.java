package com.example.ingredientsmeal.menu;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ingredientsmeal.R;
import com.example.ingredientsmeal.menuFragments.adapters.DishRecyclerViewAdapter;
import com.example.ingredientsmeal.menuFragments.adapters.MyMessagesRecyclerViewAdapter;
import com.example.ingredientsmeal.menuFragments.menuModels.DishModel;
import com.example.ingredientsmeal.models.SendMessageModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MyMessagesFragment extends Fragment {

    private RecyclerView messagesRecyclerView;
    private MyMessagesRecyclerViewAdapter myMessagesRecyclerViewAdapter;
    public String consignorField, consignor;
    DatabaseReference rootRef;
    public ConstraintLayout constraintEmptyLayout;


    public MyMessagesFragment() {
        // Required empty public constructor
    }


    public static MyMessagesFragment newInstance(String param1, String param2) {
        MyMessagesFragment fragment = new MyMessagesFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_my_messages, container, false);
        rootRef = FirebaseDatabase.getInstance().getReference();

        constraintEmptyLayout = rootView.findViewById(R.id.recyclerViewMessagesNONE);

        consignor = getArguments().getString("user");
        Log.d("test log", consignor);

        messagesRecyclerView = (RecyclerView) rootView.findViewById(R.id.messagesRecyclerView);
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView toolbarTitleTextView = (TextView) toolbar.findViewById(R.id.toolbarTitleTextView);
        toolbarTitleTextView.setText("Moje wiadomości");
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        setHasOptionsMenu(true);

        displayMessages();
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        myMessagesRecyclerViewAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        myMessagesRecyclerViewAdapter.stopListening();
    }

    public void setVisibleEmptyVISIBLE() {
        constraintEmptyLayout.setVisibility(View.VISIBLE);
    }

    public void setVisibleEmptyGONE() {
        constraintEmptyLayout.setVisibility(View.GONE);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.nav_search).setVisible(false);
        menu.findItem(R.id.my_Messages).setVisible(false);
    }


    public void displayMessages() {
        checkVisibility(consignor);
        DatabaseReference hotelRef = rootRef.child("Users").child(consignor).child("Messages");
        FirebaseRecyclerOptions<SendMessageModel> options =
                new FirebaseRecyclerOptions.Builder<SendMessageModel>()
                        .setQuery(hotelRef, SendMessageModel.class)
                        .build();
        myMessagesRecyclerViewAdapter = new MyMessagesRecyclerViewAdapter(options);
        messagesRecyclerView.setAdapter(myMessagesRecyclerViewAdapter);
    }

    public void checkVisibility(String FirebaseSecondStepUsers) {

        DatabaseReference root = FirebaseDatabase.getInstance().getReference();
        DatabaseReference users = root.child("Users").child(FirebaseSecondStepUsers);
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.child("Messages").exists()) {
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
}
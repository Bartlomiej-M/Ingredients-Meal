package com.example.ingredientsmeal.menuFragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
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
import com.example.ingredientsmeal.dialog.CustomToastDialog;
import com.example.ingredientsmeal.menuFragments.adapters.DishRecyclerViewAdapter;
import com.example.ingredientsmeal.menuFragments.adapters.HistoryRecyclerViewAdapter;
import com.example.ingredientsmeal.menuFragments.menuModels.DishModel;
import com.example.ingredientsmeal.models.HistoryModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class HistoryFragment extends Fragment {

    public String userOnline;

    private RecyclerView recviewHistory;
    private HistoryRecyclerViewAdapter historyRecyclerViewAdapter;
    public ConstraintLayout constraintEmptyLayout, recyclerViewHistoryRecycler;

    public HistoryFragment(String userOnline) {
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
        View rootView = inflater.inflate(R.layout.fragment_history, container, false);

        constraintEmptyLayout = rootView.findViewById(R.id.recyclerViewHistoryNONE);
        recyclerViewHistoryRecycler = rootView.findViewById(R.id.recyclerViewHistoryRecycler);

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView toolbarTitleTextView = (TextView) toolbar.findViewById(R.id.toolbarTitleTextView);
        toolbarTitleTextView.setText("Historia");
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);

        recviewHistory = rootView.findViewById(R.id.recviewHistory);
        recviewHistory.setLayoutManager(new LinearLayoutManager(getContext()));

        checkVisibility(userOnline);

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference hotelRef = rootRef.child("Users").child(userOnline).child("History");

        FirebaseRecyclerOptions<HistoryModel> options =
                new FirebaseRecyclerOptions.Builder<HistoryModel>()
                        .setQuery(hotelRef, HistoryModel.class)
                        .build();
        historyRecyclerViewAdapter = new HistoryRecyclerViewAdapter(options);
        recviewHistory.setAdapter(historyRecyclerViewAdapter);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        historyRecyclerViewAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        historyRecyclerViewAdapter.stopListening();
    }

    public void setVisibleEmptyVISIBLE() {
        constraintEmptyLayout.setVisibility(View.VISIBLE);
        recyclerViewHistoryRecycler.setVisibility(View.GONE);
    }

    public void setVisibleEmptyGONE() {
        constraintEmptyLayout.setVisibility(View.GONE);
        recyclerViewHistoryRecycler.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    public void checkVisibility(String FirebaseSecondStepUsers) {

        DatabaseReference root = FirebaseDatabase.getInstance().getReference();
        DatabaseReference users = root.child("Users").child(FirebaseSecondStepUsers);

        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.child("History").exists()) {
                    setVisibleEmptyGONE();
                }else{
                    setVisibleEmptyVISIBLE();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                new CustomToastDialog(getContext(), R.string.msg_toast_internet_problem, R.id.custom_toast_message, R.layout.toast_warning).show();
            }
        });

    }
}
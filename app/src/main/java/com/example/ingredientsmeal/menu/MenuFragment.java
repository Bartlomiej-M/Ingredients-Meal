package com.example.ingredientsmeal.menu;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ingredientsmeal.R;
import com.example.ingredientsmeal.dialog.CustomAlertDialog;

import com.example.ingredientsmeal.dialog.CustomLoadingDialog;
import com.example.ingredientsmeal.menuFragments.DinnerFragment;
import com.example.ingredientsmeal.menuFragments.LikedFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class MenuFragment extends Fragment implements View.OnClickListener {

    private CardView card_btn_dinner, card_btn_history, card_btn_receipt,
            card_btn_addMeal, card_btn_settings, card_btn_logout;

    public String userOnline;

    private Fragment fragment = null;

    private float v = 0;

    final CustomLoadingDialog customLoadingDialog = new CustomLoadingDialog(getActivity());

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
        View rootView = inflater.inflate(R.layout.fragment_menu, container, false);

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        TextView toolbarTitleTextView = (TextView) toolbar.findViewById(R.id.toolbarTitleTextView);
        toolbarTitleTextView.setText("Menu Główne");
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);

        card_btn_dinner = (CardView) rootView.findViewById(R.id.card_btn_dinner);
        card_btn_dinner.setOnClickListener(this);

        card_btn_history = (CardView) rootView.findViewById(R.id.card_btn_history);
        card_btn_history.setOnClickListener(this);

        card_btn_receipt = (CardView) rootView.findViewById(R.id.card_btn_liked);
        card_btn_receipt.setOnClickListener(this);

        card_btn_addMeal = (CardView) rootView.findViewById(R.id.card_btn_addMeal);
        card_btn_addMeal.setOnClickListener(this);

        card_btn_settings = (CardView) rootView.findViewById(R.id.card_btn_settings);
        card_btn_settings.setOnClickListener(this);

        card_btn_logout = (CardView) rootView.findViewById(R.id.card_btn_logout);
        card_btn_logout.setOnClickListener(this);

        setHasOptionsMenu(true);

        SetComponentsInLayout();

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        Query query = rootRef.child("Users").orderByChild("uid").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    userOnline = ds.getKey();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(),
                        "Problem z połączeniem, sprawdzi czy jesteś wciąż zalogowany", Toast.LENGTH_LONG).show();
            }
        };

        query.addListenerForSingleValueEvent(valueEventListener);

        return rootView;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.nav_search).setVisible(false);
        menu.findItem(R.id.my_Messages).setVisible(true);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.card_btn_dinner:
                fragment = new DinnerFragment();
                loadFragment(fragment);
                break;
            case R.id.card_btn_history:

                break;
            case R.id.card_btn_liked:
                fragment = new LikedFragment(userOnline);
                loadFragment(fragment);
                break;
            case R.id.card_btn_addMeal:

                break;
            case R.id.card_btn_settings:

                break;
            case R.id.card_btn_logout:
                signOut();
                break;
        }
    }

    public void signOut() {
        CustomAlertDialog cdd = new CustomAlertDialog(getActivity(), R.string.msg_question_logout);
        cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        cdd.show();
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_left);
        fragmentTransaction.replace(R.id.fragmentMenu, fragment).commit();
        fragmentTransaction.addToBackStack(null);
    }

    private void SetComponentsInLayout() {
        card_btn_dinner.setTranslationX(-700);
        card_btn_history.setTranslationX(700);
        card_btn_receipt.setTranslationX(-700);
        card_btn_addMeal.setTranslationX(700);
        card_btn_settings.setTranslationX(-700);
        card_btn_logout.setTranslationX(700);

        card_btn_dinner.setAlpha(v);
        card_btn_history.setAlpha(v);
        card_btn_receipt.setAlpha(v);
        card_btn_addMeal.setAlpha(v);
        card_btn_settings.setAlpha(v);
        card_btn_logout.setAlpha(v);

        card_btn_dinner.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        card_btn_history.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        card_btn_receipt.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(500).start();
        card_btn_addMeal.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(500).start();
        card_btn_settings.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(600).start();
        card_btn_logout.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(600).start();
    }

/*    public String getConsignorName() {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        Query query = rootRef.child("Users").orderByChild("uid").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid().toString());

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    userOnline = ds.getKey();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(),
                        "Problem z połączeniem, sprawdzi czy jesteś wciąż zalogowany", Toast.LENGTH_LONG).show();
            }
        };
        query.addListenerForSingleValueEvent(valueEventListener);
        return userOnline;
    }*/
}
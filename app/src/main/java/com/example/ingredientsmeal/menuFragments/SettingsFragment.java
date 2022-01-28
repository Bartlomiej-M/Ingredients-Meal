package com.example.ingredientsmeal.menuFragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.ingredientsmeal.R;
import com.example.ingredientsmeal.menuFragments.settings.ChangePasswordFragment;
import com.example.ingredientsmeal.models.UserModel;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SettingsFragment extends Fragment implements View.OnClickListener{

    public String userOnline;
    public String emailUser;

    private TextView username, email, number;
    private Button changePasswordText;
    private FrameLayout frameSettings;

    private UserModel userModel;

    public SettingsFragment(String userOnline) {
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
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView toolbarTitleTextView = (TextView) toolbar.findViewById(R.id.toolbarTitleTextView);
        toolbarTitleTextView.setText("Ustawienia");
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);

        username = rootView.findViewById(R.id.username);
        email = rootView.findViewById(R.id.email);
        number = rootView.findViewById(R.id.number);

        changePasswordText = rootView.findViewById(R.id.changePasswordText);
        changePasswordText.setOnClickListener(this);

        frameSettings = rootView.findViewById(R.id.frameSettings);
        Log.d("TAG", String.valueOf(userOnline));
        Log.d("TAG", String.valueOf(email));
        Log.d("TAG", String.valueOf(emailUser));
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference db  = database.getReference().child("Users").child(userOnline).child("Settings");
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            public void onDataChange(DataSnapshot data) {
                email.setText(data.child("email").getValue(String.class));

                setEmailUser(email.getText().toString().trim());
                Log.d("TAG", String.valueOf(email));
                Log.d("TAG", String.valueOf(emailUser));
                username.setText(data.child("login").getValue(String.class));

                if(data.child("number").getValue(String.class) == null
                        || data.child("number").getValue(String.class) == ""
                        || data.child("number").getValue(String.class).isEmpty()){

                    number.setText(data.child("number").getValue(String.class));
                }else{
                    number.setText("Brak numeru telefonu");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });


        return rootView;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        Fragment fragment = null;

        switch (v.getId()) {
            case R.id.changePasswordText:
                fragment = new ChangePasswordFragment(userOnline, getEmailUser());
                loadFragment(fragment);
                break;
        }
    }

    public void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
        fragmentTransaction.replace(R.id.frameSettings, fragment).commit();
        fragmentTransaction.addToBackStack(null);
    }
}
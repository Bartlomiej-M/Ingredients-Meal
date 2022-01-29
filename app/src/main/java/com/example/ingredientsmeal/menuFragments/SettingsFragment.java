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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ingredientsmeal.R;
import com.example.ingredientsmeal.dialog.CustomToastDialog;
import com.example.ingredientsmeal.menuFragments.settings.ChangePasswordFragment;
import com.example.ingredientsmeal.models.HistoryModel;
import com.example.ingredientsmeal.models.SettingModel;
import com.example.ingredientsmeal.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
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

    //BMP MODULE
    private TextView userHightTextView, userWeightTextView, bmiTextView;

    private EditText activity_main_heightcm, activity_main_weightkgs; // edit text odpowiadajacy za wczytanie wagi i wzrostu uzytkownika

    private Button btnBMTSend;// przycisk do wysyłania tego do bazy danych
    private static final int CENTIMETERS_IN_METER = 100;

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

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference db  = database.getReference().child("Users").child(userOnline).child("Settings");

        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            public void onDataChange(DataSnapshot data) {
                email.setText(data.child("email").getValue(String.class));

                setEmailUser(email.getText().toString().trim());

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
                new CustomToastDialog(getContext(), R.string.msg_toast_internet_problem, R.id.custom_toast_message, R.layout.toast_warning).show();
            }

        });

        // Bmi module
        //textview
        userHightTextView = rootView.findViewById(R.id.userHightTextView);
        userWeightTextView = rootView.findViewById(R.id.userWeightTextView);
        bmiTextView = rootView.findViewById(R.id.bmiTextView);
        //edittext
        activity_main_heightcm = rootView.findViewById(R.id.activity_main_heightcm);
        activity_main_weightkgs = rootView.findViewById(R.id.activity_main_weightkgs);
        //button
        btnBMTSend = rootView.findViewById(R.id.btnBMTSend);
        btnBMTSend.setOnClickListener(this);

        setHasOptionsMenu(true);
        bmigetBuilder();
        return rootView;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
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
        Fragment fragment = null;

        switch (v.getId()) {
            case R.id.changePasswordText:
                fragment = new ChangePasswordFragment(userOnline, getEmailUser());
                loadFragment(fragment);
                break;
            case R.id.btnBMTSend:
                if(activity_main_heightcm.getText().toString().trim().isEmpty() || activity_main_weightkgs.getText().toString().trim().isEmpty()){
                    new CustomToastDialog(getContext(), R.string.msg_toast_err_empty_records, R.id.custom_toast_message, R.layout.toast_warning).show();
                }else{
                    bmisetBuilder();
                }
                break;
        }
    }

    public void bmisetBuilder(){

        float heightValue = Float.parseFloat(activity_main_heightcm.getText().toString().trim());
        float weightValu = Float.parseFloat(activity_main_weightkgs.getText().toString().trim());
        heightValue = heightValue / 100;
        float bmp = (weightValu / (heightValue*heightValue));

        SettingModel settingModel = new SettingModel(String.valueOf(heightValue), String.valueOf(weightValu), String.valueOf(bmp));

        FirebaseDatabase.getInstance().getReference("Users")
                .child(userOnline).child("Settings").child("bmi")
                .setValue(settingModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    new CustomToastDialog(getContext(), R.string.msg_toast_succ_bmi, R.id.custom_toast_message, R.layout.toast_success).show();
                    bmigetBuilder();
                } else {
                    new CustomToastDialog(getContext(), R.string.msg_toast_err_bmi, R.id.custom_toast_message, R.layout.toast_warning).show();
                }
            }
        });
    };

    public void bmigetBuilder(){

        DatabaseReference root = FirebaseDatabase.getInstance().getReference();
        DatabaseReference users = root.child("Users").child(userOnline).child("Settings");

        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.child("bmi").exists()) {
                    //jezeli folder bmi istnieje bo uzytkownik sprawdzil swoje bmi
                    bmisetView();

                }else{
                    userHightTextView.setText("Nie obliczyłeś swojego bmi");
                    userWeightTextView.setText("Nie obliczyłeś swojego bmi");
                    bmiTextView.setText("Nie obliczyłeś swojego bmi");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                new CustomToastDialog(getContext(), R.string.msg_toast_internet_problem, R.id.custom_toast_message, R.layout.toast_warning).show();
            }
        });
    }

    public void bmisetView(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference db  = database.getReference().child("Users").child(userOnline).child("Settings").child("bmi");

        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            public void onDataChange(DataSnapshot data) {
                userHightTextView.setText(data.child("hightUser").getValue(String.class) + "m");
                userWeightTextView.setText(data.child("weightUser").getValue(String.class) + "kg");
                bmiTextView.setText(data.child("bmp").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                new CustomToastDialog(getContext(), R.string.msg_toast_internet_problem, R.id.custom_toast_message, R.layout.toast_warning).show();
            }

        });
    }

    public void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
        fragmentTransaction.replace(R.id.frameSettings, fragment).commit();
        fragmentTransaction.addToBackStack(null);
    }
}
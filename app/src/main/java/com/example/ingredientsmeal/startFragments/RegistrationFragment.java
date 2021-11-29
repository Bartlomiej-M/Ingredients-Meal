package com.example.ingredientsmeal.startFragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ingredientsmeal.R;
import com.example.ingredientsmeal.dialog.CustomToastDialog;
import com.example.ingredientsmeal.dialog.LoadingDialog;
import com.example.ingredientsmeal.menu.MenuListMeals;
import com.example.ingredientsmeal.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegistrationFragment extends Fragment implements View.OnClickListener {

    private Button btnArrowBackRegistration, btnRegistration;
    private TextView titleLogin;
    private ImageView RgImageView;
    private TextInputEditText LoginInputTextReg, EmailInputTextReg, PasswordInputTextReg, NumberInputTextReg;
    private TextInputLayout textInputLayoutLg, textInputLayoutEm, textInputLayoutPass, textInputLayoutNumber;
    private FirebaseAuth mAuth;
    private float v = 0;

    public RegistrationFragment() {
        // Required empty public constructor
    }

    public static RegistrationFragment newInstance(String param1, String param2) {
        RegistrationFragment fragment = new RegistrationFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_registration, container, false);

        mAuth = FirebaseAuth.getInstance();

        titleLogin = (TextView) rootView.findViewById(R.id.titleLogin);//przycisk wstecz

        btnArrowBackRegistration = (Button) rootView.findViewById(R.id.btnBackRegistration);//przycisk wstecz
        btnArrowBackRegistration.setOnClickListener(this);

        btnRegistration = (Button) rootView.findViewById(R.id.btnRegistration);//przycisk rejestracji
        btnRegistration.setOnClickListener(this);

        LoginInputTextReg = (TextInputEditText) rootView.findViewById(R.id.LoginInputTextReg);//edit text login
        EmailInputTextReg = (TextInputEditText) rootView.findViewById(R.id.EmailInputTextReg);//edit text mail
        PasswordInputTextReg = (TextInputEditText) rootView.findViewById(R.id.PasswordInputTextReg);//edit text haslo
        NumberInputTextReg = (TextInputEditText) rootView.findViewById(R.id.NumberInputTextReg);//edit text number

        textInputLayoutLg = (TextInputLayout) rootView.findViewById(R.id.textInputLayoutLg);//edit text login
        textInputLayoutEm = (TextInputLayout) rootView.findViewById(R.id.textInputLayoutEm);//edit text login
        textInputLayoutPass = (TextInputLayout) rootView.findViewById(R.id.textInputLayoutPass);//edit text login
        textInputLayoutNumber = (TextInputLayout) rootView.findViewById(R.id.textInputLayoutNumber);//edit text login

        RgImageView = (ImageView) rootView.findViewById(R.id.RgImageView);//edit text login

        SetComponentsInLayout();

        return rootView;
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = null;

        switch (v.getId()) {
            case R.id.btnBackRegistration:
                fragment = new WelcomeFragment();
                loadFragment(fragment);
                break;
            case R.id.btnRegistration:
                regUser();
                break;
        }
    }

    private void regUser() {
        String login = LoginInputTextReg.getText().toString().trim();
        String email = EmailInputTextReg.getText().toString().trim();
        String password = PasswordInputTextReg.getText().toString().trim();
        String number = NumberInputTextReg.getText().toString().trim();

        if (login.isEmpty()) {
            LoginInputTextReg.setError("Login nie może być pusty.");
            LoginInputTextReg.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            EmailInputTextReg.setError("Email nie może być pusty.");
            EmailInputTextReg.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            PasswordInputTextReg.setError("Password nie może być pusty.");
            PasswordInputTextReg.requestFocus();
            return;
        }
        if (!isValidPassword(password)) {
            PasswordInputTextReg.setError("Hasło nie spełnia wymagań.");
            PasswordInputTextReg.requestFocus();
            return;
        }//Password second validation check

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            EmailInputTextReg.setError("E-Mail jest niepoprawny.");
            EmailInputTextReg.requestFocus();
            return;
        }

        final LoadingDialog loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.startLoadingDialog();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    UserModel userModel = new UserModel(login, email, number);

                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                loadingDialog.dismissDialog();
                                new CustomToastDialog(getContext(), R.string.msg_toast_succ_reg, R.id.custom_toast_message, R.layout.toast_success).show();
                                Fragment fragment = null;
                                fragment = new LoginFragment();
                                loadFragment(fragment);
                            } else {
                                loadingDialog.dismissDialog();
                                new CustomToastDialog(getContext(), R.string.msg_toast_error_again, R.id.custom_toast_message, R.layout.toast_warning).show();
                            }
                        }
                    });
                } else {
                    loadingDialog.dismissDialog();
                    new CustomToastDialog(getContext(), R.string.msg_toast_error_reg, R.id.custom_toast_message, R.layout.toast_warning).show();
                }
            }
        });
    }

    private static boolean isValidPassword(String password) {
        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";

        Pattern p = Pattern.compile(regex);

        if (password == null) {
            return false;
        }
        Matcher m = p.matcher(password);
        return m.matches();
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment).commit();
        fragmentTransaction.addToBackStack(null);
    }

    private void SetComponentsInLayout() {

        titleLogin.setTranslationY(-700);
        btnArrowBackRegistration.setTranslationY(-700);
        textInputLayoutLg.setTranslationX(700);
        textInputLayoutEm.setTranslationX(700);
        textInputLayoutPass.setTranslationX(700);
        textInputLayoutNumber.setTranslationX(700);
        btnRegistration.setTranslationY(700);
        RgImageView.setTranslationY(700);

        titleLogin.setAlpha(v);
        btnArrowBackRegistration.setAlpha(v);
        textInputLayoutLg.setAlpha(v);
        textInputLayoutEm.setAlpha(v);
        textInputLayoutPass.setAlpha(v);
        textInputLayoutNumber.setAlpha(v);
        btnRegistration.setAlpha(v);
        RgImageView.setAlpha(v);

        titleLogin.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(450).start();
        btnArrowBackRegistration.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        textInputLayoutLg.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(500).start();
        textInputLayoutEm.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(550).start();
        textInputLayoutPass.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(650).start();
        textInputLayoutNumber.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(700).start();
        btnRegistration.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(600).start();
        RgImageView.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(700).start();

    }
}
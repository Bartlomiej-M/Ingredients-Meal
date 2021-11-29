package com.example.ingredientsmeal.startFragments;

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

import com.example.ingredientsmeal.MainActivity;
import com.example.ingredientsmeal.R;
import com.example.ingredientsmeal.dialog.CustomToastDialog;
import com.example.ingredientsmeal.dialog.LoadingDialog;
import com.example.ingredientsmeal.menu.MenuListMeals;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginFragment extends Fragment implements View.OnClickListener {

    private Button btnArrowBackLogin, btnLoginIn;
    private TextView titleLogin, btnForgotPassword;
    private TextInputEditText EmailInputTextLog, PasswordInputTextLog;
    private TextInputLayout textInputLayoutPass, textInputLayoutLg;
    private ImageView LoginImageView;
    private FirebaseAuth mAuth;
    private float v = 0;

    public LoginFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        mAuth = FirebaseAuth.getInstance();

        btnArrowBackLogin = (Button) rootView.findViewById(R.id.btnArrowBackLogin);
        btnArrowBackLogin.setOnClickListener(this);

        titleLogin = (TextView) rootView.findViewById(R.id.titleLogin);

        btnForgotPassword = (TextView) rootView.findViewById(R.id.btnForgotPassword);
        btnForgotPassword.setOnClickListener(this);

        btnLoginIn = (Button) rootView.findViewById(R.id.btnLoginIn);
        btnLoginIn.setOnClickListener(this);

        EmailInputTextLog = (TextInputEditText) rootView.findViewById(R.id.EmailInputTextLog);
        PasswordInputTextLog = (TextInputEditText) rootView.findViewById(R.id.PasswordInputTextLog);

        LoginImageView = (ImageView) rootView.findViewById(R.id.LoginImageView);

        textInputLayoutLg = rootView.findViewById(R.id.textInputLayoutLg);
        textInputLayoutPass = rootView.findViewById(R.id.textInputLayoutPass);

        SetComponentsInLayout();//Ustawienie Komponentów

        return rootView;
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = null;

        switch (v.getId()) {
            case R.id.btnArrowBackLogin:
                fragment = new WelcomeFragment();
                loadFragment(fragment);
                break;
            case R.id.btnForgotPassword:
                fragment = new ForgotPassword();
                loadFragment(fragment);
                break;
            case R.id.btnLoginIn:
                loginInApp();
                break;
        }
    }

    private void loginInApp() {
        String email = EmailInputTextLog.getText().toString().trim();
        String password = PasswordInputTextLog.getText().toString().trim();

        if (email.isEmpty()) {
            EmailInputTextLog.setError("Email nie może być pusty.");
            EmailInputTextLog.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            PasswordInputTextLog.setError("Hasło nie może być puste.");
            PasswordInputTextLog.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            EmailInputTextLog.setError("E-Mail jest niepoprawny.");
            EmailInputTextLog.requestFocus();
            return;
        }
        final LoadingDialog loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.startLoadingDialog();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    loadingDialog.dismissDialog();
                    startActivity(new Intent(getContext(), MenuListMeals.class));
                    getActivity().overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_left);
                } else {
                    loadingDialog.dismissDialog();
                    new CustomToastDialog(getActivity(), R.string.msg_toast_error_lg, R.id.custom_toast_message, R.layout.toast_warning).show();
                }
            }
        });

    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_left);
        fragmentTransaction.replace(R.id.fragmentContainer, fragment).commit();
        fragmentTransaction.addToBackStack(null);
    }

    private void SetComponentsInLayout() {
        btnArrowBackLogin.setTranslationY(-700);
        titleLogin.setTranslationY(-700);
        textInputLayoutLg.setTranslationX(700);
        textInputLayoutPass.setTranslationX(700);
        btnForgotPassword.setTranslationX(700);
        LoginImageView.setTranslationY(700);
        btnLoginIn.setTranslationY(700);

        btnArrowBackLogin.setAlpha(v);
        titleLogin.setAlpha(v);
        textInputLayoutLg.setAlpha(v);
        textInputLayoutPass.setAlpha(v);
        btnForgotPassword.setAlpha(v);
        LoginImageView.setAlpha(v);
        btnLoginIn.setAlpha(v);

        btnArrowBackLogin.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(500).start();
        titleLogin.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(600).start();
        textInputLayoutLg.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(600).start();
        textInputLayoutPass.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(700).start();
        btnForgotPassword.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(800).start();
        LoginImageView.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(500).start();
        btnLoginIn.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(600).start();
    }
}

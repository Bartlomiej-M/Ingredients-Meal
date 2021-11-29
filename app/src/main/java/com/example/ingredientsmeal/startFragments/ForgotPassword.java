package com.example.ingredientsmeal.startFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;


public class ForgotPassword extends Fragment implements View.OnClickListener{

    private Button btnArrowBackForgot, btnPasswordReset;
    private TextView titleLogin;
    private TextInputEditText EmailInputTextForgot;
    private TextInputLayout textInputLayoutEm;
    private ImageView ForgotImageView;
    public FirebaseAuth auth;
    private float v = 0;

    public ForgotPassword() {
        // Required empty public constructor
    }

    public static ForgotPassword newInstance(String param1, String param2) {
        ForgotPassword fragment = new ForgotPassword();
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
        View rootView = inflater.inflate(R.layout.fragment_forgot_password, container, false);

        auth = FirebaseAuth.getInstance();

        titleLogin = (TextView) rootView.findViewById(R.id.titleLogin);

        btnArrowBackForgot = (Button) rootView.findViewById(R.id.btnArrowBackForgot);
        btnArrowBackForgot.setOnClickListener(this);

        btnPasswordReset = (Button) rootView.findViewById(R.id.btnPasswordReset);
        btnPasswordReset.setOnClickListener(this);

        EmailInputTextForgot = (TextInputEditText) rootView.findViewById(R.id.EmailInputTextForgot);

        textInputLayoutEm= (TextInputLayout) rootView.findViewById(R.id.textInputLayoutEm);

        ForgotImageView = (ImageView) rootView.findViewById(R.id.ForgotImageView);

        SetComponentsInLayout();

        return rootView;
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = null;

        switch (v.getId()) {
            case R.id.btnArrowBackForgot:
                int count = getFragmentManager().getBackStackEntryCount();

                if (count == 0) {
                    getActivity().onBackPressed();
                    //additional code
                } else {
                    getFragmentManager().popBackStack();
                }
                break;
            case R.id.btnPasswordReset:
                passwordReset();
                break;
        }
    }

    private void passwordReset() {
        String email = EmailInputTextForgot.getText().toString().trim();

        if (email.isEmpty()) {
            EmailInputTextForgot.setError("Email nie może być pusty.");
            EmailInputTextForgot.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            EmailInputTextForgot.setError("E-Mail jest niepoprawny.");
            EmailInputTextForgot.requestFocus();
            return;
        }

        final LoadingDialog loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.startLoadingDialog();

        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    loadingDialog.dismissDialog();
                    new CustomToastDialog(getActivity(), R.string.msg_toast_succ_forget, R.id.custom_toast_message, R.layout.toast_success).show();
                } else {
                    loadingDialog.dismissDialog();
                    new CustomToastDialog(getActivity(), R.string.msg_toast_error_forget, R.id.custom_toast_message, R.layout.toast_warning).show();
                }
            }
        });
    }

    private void SetComponentsInLayout() {

        titleLogin.setTranslationY(-700);
        btnArrowBackForgot.setTranslationY(-700);
        textInputLayoutEm.setTranslationX(700);
        btnPasswordReset.setTranslationX(-700);
        ForgotImageView.setTranslationY(700);

        titleLogin.setAlpha(v);
        btnArrowBackForgot.setAlpha(v);
        textInputLayoutEm.setAlpha(v);
        btnPasswordReset.setAlpha(v);
        ForgotImageView.setAlpha(v);

        titleLogin.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(600).start();
        btnArrowBackForgot.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(500).start();
        textInputLayoutEm.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(700).start();
        btnPasswordReset.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(800).start();
        ForgotImageView.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(500).start();
    }
}
package com.example.ingredientsmeal.menuFragments.settings;

import android.os.Bundle;

import androidx.annotation.NonNull;
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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ingredientsmeal.R;
import com.example.ingredientsmeal.menuFragments.DinnerFragment;
import com.example.ingredientsmeal.menuFragments.SettingsFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ChangePasswordFragment extends Fragment implements View.OnClickListener {

    private String userOnline, emailUser;
    private ImageButton buttonBackChangePassword;
    private Button btnLoginIn;
    private TextInputEditText OldPasswordInputText, NewPasswordPasswordInputText, RepeatNewPasswordInputText;
    private TextInputLayout textInputLayoutOldPassword, textInputLayoutONewPassword, textInputLayoutORepeatNewPassword;

    public ChangePasswordFragment(String userOnline, String emailUser) {
        this.userOnline = userOnline;
        this.emailUser = emailUser;
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
        View rootView = inflater.inflate(R.layout.fragment_change_password, container, false);

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView toolbarTitleTextView = (TextView) toolbar.findViewById(R.id.toolbarTitleTextView);
        toolbarTitleTextView.setText("Zmiena hasła");
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);

        btnLoginIn = rootView.findViewById(R.id.btnLoginIn);
        btnLoginIn.setOnClickListener(this);

        OldPasswordInputText = rootView.findViewById(R.id.OldPasswordInputText);
        NewPasswordPasswordInputText = rootView.findViewById(R.id.NewPasswordPasswordInputText);

        textInputLayoutOldPassword = rootView.findViewById(R.id.textInputLayoutONewPassword);
        textInputLayoutONewPassword = rootView.findViewById(R.id.textInputLayoutONewPassword);
        textInputLayoutORepeatNewPassword = rootView.findViewById(R.id.textInputLayoutORepeatNewPassword);

        RepeatNewPasswordInputText = rootView.findViewById(R.id.RepeatNewPasswordInputText);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
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

    @Override
    public void onClick(View v) {

        Fragment fragment = null;

        switch (v.getId()) {
            case R.id.btnLoginIn:

                changePassword();

                break;
        }
    }

    public void changePassword() {

        String oldPasswordInputText = OldPasswordInputText.getText().toString().trim();
        String newPasswordPasswordInputText = NewPasswordPasswordInputText.getText().toString().trim();
        String repeatNewPasswordInputText = RepeatNewPasswordInputText.getText().toString().trim();

        if (oldPasswordInputText.isEmpty()) {
            textInputLayoutOldPassword.setError("Stare hasło nie może być pusty.");
            textInputLayoutOldPassword.requestFocus();
            return;
        }
        if (newPasswordPasswordInputText.isEmpty()) {
            textInputLayoutONewPassword.setError("Nowe hasło nie może być pusty.");
            textInputLayoutONewPassword.requestFocus();
            return;
        }
        if (repeatNewPasswordInputText.isEmpty()) {
            textInputLayoutORepeatNewPassword.setError("Powtórzone hasło nie może być pusty.");
            textInputLayoutORepeatNewPassword.requestFocus();
            return;
        }

        if (!isValidPassword(newPasswordPasswordInputText)) {
            textInputLayoutONewPassword.setError("Hasło nie spełnia wymagań.");
            textInputLayoutONewPassword.requestFocus();
            return;
        }
        if (oldPasswordInputText == newPasswordPasswordInputText) {
            Toast.makeText(getContext(), "Wprowadzone nowe hasło nie może być takie same jak stare", Toast.LENGTH_LONG).show();
        } else if (!newPasswordPasswordInputText.equals(repeatNewPasswordInputText)) {
            Toast.makeText(getContext(), "Hasła nie są takie same", Toast.LENGTH_LONG).show();
        } else {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        Log.d("TAG1", emailUser);
        Log.d("TAG2", oldPasswordInputText);

        AuthCredential credential = EmailAuthProvider
                .getCredential(emailUser, oldPasswordInputText);

            assert user != null;
            user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            user.updatePassword(newPasswordPasswordInputText).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getContext(), "Udało się zmienić hasło", Toast.LENGTH_LONG).show();

                                    } else {
                                        Toast.makeText(getContext(), "Nie udało się zmienić hasło", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(getContext(), "Błąd połączenia z bazą danych", Toast.LENGTH_LONG).show();
                        }
                    }
                });
        }
    }

    public void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_left);
        fragmentTransaction.replace(R.id.frameSettings, fragment).commit();
        fragmentTransaction.addToBackStack(null);
    }


}
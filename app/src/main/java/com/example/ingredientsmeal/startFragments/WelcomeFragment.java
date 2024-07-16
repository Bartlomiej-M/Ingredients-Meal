package com.example.ingredientsmeal.startFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ingredientsmeal.R;


public class WelcomeFragment extends Fragment implements View.OnClickListener {

    private Button btnLogin, btnRegistration;
    private TextView titleLogin;
    private ImageView MainActivityImageView;
    private FrameLayout fragmentContainer;
    private float v = 0;

    public WelcomeFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static WelcomeFragment newInstance(String param1, String param2) {
        WelcomeFragment fragment = new WelcomeFragment();
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

        View rootView = inflater.inflate(R.layout.fragment_welcome, container, false);

        titleLogin = (TextView) rootView.findViewById(R.id.titleLogin);

        MainActivityImageView = (ImageView) rootView.findViewById(R.id.MainActivityImageView);

        btnLogin = (Button) rootView.findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        btnRegistration = (Button) rootView.findViewById(R.id.btnRegistration);
        btnRegistration.setOnClickListener(this);

        fragmentContainer = (FrameLayout) rootView.findViewById(R.id.fragmentContainer);

        SetComponentsInLayout();

        return rootView;
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = null;

        switch (v.getId()) {
            case R.id.btnLogin:
                fragment = new LoginFragment();
                loadFragment(fragment);
                break;
            case R.id.btnRegistration:
                fragment = new RegistrationFragment();
                loadFragment(fragment);
                break;
        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
        fragmentTransaction.replace(R.id.fragmentContainer, fragment).commit();
        fragmentTransaction.addToBackStack(null);
    }

    private void SetComponentsInLayout() {

        titleLogin.setTranslationY(-700);
        MainActivityImageView.setTranslationY(-700);
        btnLogin.setTranslationX(-700);
        btnRegistration.setTranslationX(700);

        titleLogin.setAlpha(v);
        MainActivityImageView.setAlpha(v);
        btnLogin.setAlpha(v);
        btnRegistration.setAlpha(v);

        titleLogin.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(500).start();
        MainActivityImageView.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(600).start();
        btnLogin.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(700).start();
        btnRegistration.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(700).start();

    }
}
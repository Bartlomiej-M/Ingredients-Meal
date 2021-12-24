package com.example.ingredientsmeal.menuFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ingredientsmeal.R;
import com.example.ingredientsmeal.menuFragments.viewpages.IngredientsFragment;
import com.example.ingredientsmeal.menuFragments.viewpages.RecipeFragment;
import com.example.ingredientsmeal.menuFragments.viewpages.ViewDetailsPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class DetailsFragment extends Fragment {

    public String FirebaseFirstStepDinner, FirebaseFirstSecondDinner, FirebaseFirstthirdDinner, FirebaseFirstfourthDinner;
    public ViewPager2 viewPager2;

    public DetailsFragment() {
        // Required empty public constructor
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
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);

        FirebaseFirstStepDinner = getArguments().getString("FirebaseFirstStepDinner");
        FirebaseFirstSecondDinner = getArguments().getString("FirebaseFirstSecondDinner");
        FirebaseFirstthirdDinner = getArguments().getString("FirebaseFirstthirdDinner");
        FirebaseFirstfourthDinner = getArguments().getString("FirebaseFirstfourthDinner");

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView toolbarTitleTextView = (TextView) toolbar.findViewById(R.id.toolbarTitleTextView);
        toolbarTitleTextView.setText(FirebaseFirstfourthDinner);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);

        TabLayout tabLayout = rootView.findViewById(R.id.tabLayout);
        viewPager2 = rootView.findViewById(R.id.view_pager);

        Bundle arguments = new Bundle();
        arguments.putSerializable("FirebaseFirstStepDinner", FirebaseFirstStepDinner);
        arguments.putSerializable("FirebaseFirstSecondDinner", FirebaseFirstSecondDinner);
        arguments.putSerializable("FirebaseFirstthirdDinner", FirebaseFirstthirdDinner);
        arguments.putSerializable("FirebaseFirstfourthDinner", FirebaseFirstfourthDinner);

        ViewDetailsPagerAdapter adapter = new ViewDetailsPagerAdapter(getActivity(), arguments);
        viewPager2.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager2,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

                        if (position == 0) {
                            tab.setText("Przygotowanie");
                            IngredientsFragment ingredientsFragment = new IngredientsFragment();
                            ingredientsFragment.setArguments(arguments);

                        } else {
                            tab.setText("Składniki");
                            RecipeFragment recipeFragment = new RecipeFragment();
                            recipeFragment.setArguments(arguments);
                        }
                    }
                }).attach();

        return rootView;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.nav_search).setVisible(false);
        menu.findItem(R.id.nav_settings).setVisible(true);
    }

    public void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_left);
        fragmentTransaction.replace(R.id.fragmentMenu, fragment).commit();
        fragmentTransaction.addToBackStack(null);
    }

}
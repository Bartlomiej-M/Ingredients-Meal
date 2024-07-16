package com.example.ingredientsmeal.menuFragments.viewpages;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.ingredientsmeal.R;

import java.io.Serializable;

public class ViewDetailsPagerAdapter extends FragmentStateAdapter implements Serializable {
    Bundle arguments;

    public ViewDetailsPagerAdapter(@NonNull FragmentActivity fragmentActivity, Bundle arguments) {
        super(fragmentActivity);
        this.arguments = arguments;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        if (position == 0) {
            IngredientFragment ingredientsFragment = new IngredientFragment();
            ingredientsFragment.setArguments(arguments);
            return ingredientsFragment;

        } else if (position == 1) {
            RecipeFragment recipeFragment = new RecipeFragment();
            recipeFragment.setArguments(arguments);
            return recipeFragment;
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
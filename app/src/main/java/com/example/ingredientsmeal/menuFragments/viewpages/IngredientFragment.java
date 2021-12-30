package com.example.ingredientsmeal.menuFragments.viewpages;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.ingredientsmeal.R;
import com.example.ingredientsmeal.dialog.CustomShareIngredientsDialog;
import com.example.ingredientsmeal.menuFragments.adapters.IngredientsRecyclerViewAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;


public class IngredientFragment extends Fragment implements View.OnClickListener {

    public String FirebaseFirstStepDinner, FirebaseFirstSecondDinner, FirebaseFirstthirdDinner, FirebaseFirstfourthDinner;
    private Button btnSendIngredients;
    private Bundle bundle;
    public ArrayList<String> ingredientsArrayList = new ArrayList<>();
    public ArrayList<String> ingredientskeyArrayList = new ArrayList<>();
    private RecyclerView ingredientsRecyclerView;
    public IngredientsRecyclerViewAdapter adapter;

    public IngredientFragment() {
        // Required empty public constructor
    }

    public static IngredientFragment newInstance(int param1, String param2) {
        IngredientFragment fragment = new IngredientFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            bundle = getArguments();
            FirebaseFirstStepDinner = (String) bundle.getSerializable("FirebaseFirstStepDinner");
            FirebaseFirstSecondDinner = (String) bundle.getSerializable("FirebaseFirstSecondDinner");
            FirebaseFirstthirdDinner = (String) bundle.getSerializable("FirebaseFirstthirdDinner");
            FirebaseFirstfourthDinner = (String) bundle.getSerializable("FirebaseFirstfourthDinner");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ingredients, container, false);

        btnSendIngredients = (Button) rootView.findViewById(R.id.btnSendIngredients);
        btnSendIngredients.setOnClickListener(this);

        ingredientsRecyclerView = (RecyclerView) rootView.findViewById(R.id.ingredientsListView);
        openViewIngredients();

        return rootView;
    }


    public void openViewIngredients() {
        ingredientsArrayList.clear();

        DatabaseReference rootRef2 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference hotelRef2 = rootRef2.child(FirebaseFirstStepDinner.toString())
                .child(FirebaseFirstSecondDinner)
                .child(FirebaseFirstthirdDinner)
                .child(FirebaseFirstfourthDinner)
                .child("Składniki");

        ValueEventListener eventListener2 = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    String key = childSnapshot.getKey();
                    String value = (String) childSnapshot.getValue();

                    ingredientskeyArrayList.add(key);
                    ingredientsArrayList.add(value);

                    adapter = new IngredientsRecyclerViewAdapter(ingredientskeyArrayList, ingredientsArrayList);
                    ingredientsRecyclerView.setHasFixedSize(true);
                    ingredientsRecyclerView.setAdapter(adapter);

                    LinearLayoutManager llm = new LinearLayoutManager(getContext());
                    llm.setOrientation(LinearLayoutManager.VERTICAL);
                    ingredientsRecyclerView.setLayoutManager(llm);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //"Tu nalezy wstawic komunikat o bledzie albo informacje w freagmencie ze jest null error base"
            }
        };
        hotelRef2.addListenerForSingleValueEvent(eventListener2);
    }

    public void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_left);
        fragmentTransaction.replace(R.id.fragmentMenu, fragment).commit();
        fragmentTransaction.addToBackStack(null);
    }

    public void getData(){
        ingredientsArrayList = (ArrayList<String>) adapter.listofselectedactivites();
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = null;

        switch (v.getId()) {
            case R.id.btnSendIngredients:
                getData();
                if(ingredientsArrayList == null || ingredientsArrayList.isEmpty()){
                    Toast.makeText(getContext(), "Nie wybrałeś żadnych składników", Toast.LENGTH_LONG).show();
                }else{
                    infoAboutShare(ingredientsArrayList);
                }

                break;

        }
    }

    public void infoAboutShare(ArrayList<String> ingredientsArrayList) {
        CustomShareIngredientsDialog customShareIngredientsDialog =
                new CustomShareIngredientsDialog(getActivity(), R.string.msg_question_logout, ingredientsArrayList);

        customShareIngredientsDialog.getWindow().
                setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        customShareIngredientsDialog.show();
    }
}
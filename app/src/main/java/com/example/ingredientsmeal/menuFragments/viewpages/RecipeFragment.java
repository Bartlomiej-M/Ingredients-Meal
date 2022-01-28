package com.example.ingredientsmeal.menuFragments.viewpages;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ingredientsmeal.R;
import com.example.ingredientsmeal.dialog.CustomToastDialog;
import com.example.ingredientsmeal.menu.MainMenu;
import com.example.ingredientsmeal.menuFragments.DinnerFragment;
import com.example.ingredientsmeal.menuFragments.adapters.RecipeRecyclerViewAdapter;
import com.example.ingredientsmeal.menuFragments.adapters.TypeRecyclerViewAdapter;
import com.example.ingredientsmeal.models.HistoryModel;
import com.example.ingredientsmeal.models.SendMessageModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class RecipeFragment extends Fragment implements View.OnClickListener {

    public static String FirebaseFirstStepDinner, FirebaseFirstSecondDinner, FirebaseFirstthirdDinner, FirebaseFirstfourthDinner;

    private Bundle bundle;
    public ArrayList<String> recipeValueArrayList = new ArrayList<>();
    public ArrayList<String> recipeKeyArrayList = new ArrayList<>();
    private RecyclerView recipeRecyclerView;

    public String userOnline;
    private Button btnCompleted;

    public RecipeFragment() {
        // Required empty public constructor
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

    public static String getFirebaseFirstStepDinner() {
        return FirebaseFirstStepDinner;
    }

    public static String getFirebaseFirstSecondDinner() {
        return FirebaseFirstSecondDinner;
    }

    public static String getFirebaseFirstthirdDinner() {
        return FirebaseFirstthirdDinner;
    }

    public static String getFirebaseFirstfourthDinner() {
        return FirebaseFirstfourthDinner;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe, container, false);

        recipeRecyclerView = (RecyclerView) rootView.findViewById(R.id.recipeRecyclerView);

        btnCompleted = rootView.findViewById(R.id.btnCompleted);
        btnCompleted.setOnClickListener(this);
        openViewRecipe();
        getConsignorName();
        return rootView;
    }

    public void openViewRecipe() {
        recipeValueArrayList.clear();

        DatabaseReference rootRef2 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference hotelRef2 = rootRef2.child(FirebaseFirstStepDinner)
                .child(FirebaseFirstSecondDinner)
                .child(FirebaseFirstthirdDinner)
                .child(FirebaseFirstfourthDinner)
                .child("Przygotowanie");

        ValueEventListener eventListener2 = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    String key = childSnapshot.getKey();
                    String value = (String) childSnapshot.getValue();

                    recipeValueArrayList.add(value);
                    recipeKeyArrayList.add(key);

                    RecipeRecyclerViewAdapter adapter = new RecipeRecyclerViewAdapter(recipeKeyArrayList, recipeValueArrayList);

                    recipeRecyclerView.setHasFixedSize(true);
                    recipeRecyclerView.setAdapter(adapter);

                    LinearLayoutManager llm = new LinearLayoutManager(getContext());
                    llm.setOrientation(LinearLayoutManager.VERTICAL);
                    recipeRecyclerView.setLayoutManager(llm);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCompleted:
                getConsignorName();
                sentViewRecipe();
                break;
        }
    }
    public void sentViewRecipe() {
        String historyTypeMeal, historyPreferenceMeal, historyCurrentDate, historyCurrentTime;

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        userOnline = getUserOnline();
        //Przykład
        Log.d("Tag2: ", FirebaseFirstSecondDinner);//Bez glutenu
        Log.d("Tag3: ", FirebaseFirstthirdDinner);//Mięsne
        Log.d("Tag4: ", FirebaseFirstfourthDinner);//Kurczak po tajsku
        Log.d("Tag5: ", currentDate.toString());//28-01-2022
        Log.d("Tag6: ", currentTime.toString());//12:33:40
        Log.d("Tag7", String.valueOf(userOnline));//bartek1

        historyTypeMeal = FirebaseFirstSecondDinner.toString();
        historyPreferenceMeal = FirebaseFirstthirdDinner.toString();
        historyCurrentDate =  currentDate.toString();
        historyCurrentTime = currentTime.toString();

        HistoryModel historyModel = new HistoryModel(historyTypeMeal, historyPreferenceMeal, historyCurrentDate, historyCurrentTime);

        FirebaseDatabase.getInstance().getReference("Users")
                .child(userOnline).child("History").child(FirebaseFirstfourthDinner)
                .setValue(historyModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    new CustomToastDialog(getContext(), R.string.msg_toast_succ_history, R.id.custom_toast_message, R.layout.toast_success).show();
                } else {
                    new CustomToastDialog(getContext(), R.string.msg_toast_err_history, R.id.custom_toast_message, R.layout.toast_warning).show();
                }
            }
        });
    }

    public void getConsignorName() {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        Query query = rootRef.child("Users").orderByChild("uid").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid().toString());

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    userOnline = ds.getKey();
                    setUserOnline(userOnline);
                    userOnline.equals(ds.getKey());
                }

                setUserOnline(userOnline);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(),
                        "Problem z połączeniem, sprawdzi czy jesteś wciąż zalogowany", Toast.LENGTH_LONG).show();
            }
        };
        query.addListenerForSingleValueEvent(valueEventListener);
    }
    public String getUserOnline() {
        return userOnline;
    }

    public void setUserOnline(String userOnline) {
        this.userOnline = userOnline;
    }
}
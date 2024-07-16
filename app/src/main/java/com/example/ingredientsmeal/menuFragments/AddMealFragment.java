package com.example.ingredientsmeal.menuFragments;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ingredientsmeal.R;
import com.example.ingredientsmeal.dialog.CustomToastDialog;
import com.example.ingredientsmeal.menuFragments.menuModels.DishModel;
import com.example.ingredientsmeal.models.AddModel;
import com.example.ingredientsmeal.startFragments.WelcomeFragment;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AddMealFragment extends Fragment implements View.OnClickListener {

    private String userOnline, meal, duration, portion, level, energy, foto;
    private TextInputEditText mealNameTextReg, durationTextReg, portionTextReg, levelTextReg, energyInPortionsTextReg;
    private Button btnFirstStepAddMeal, btnGoToAddedMeals;
    public ImageView IVPreviewImage;
    public String imageURI;
    Bitmap bitmap;
    // constant to compare
    // the activity result code
    int SELECT_PICTURE = 200;
    private static int RESULT_LOAD_IMG = 1;
    public AddMealFragment(String userOnline) {
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
        View rootView = inflater.inflate(R.layout.fragment_add_meal, container, false);

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView toolbarTitleTextView = (TextView) toolbar.findViewById(R.id.toolbarTitleTextView);
        toolbarTitleTextView.setText("Dodaj własną potrawe");
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        setHasOptionsMenu(true);

        mealNameTextReg = (TextInputEditText) rootView.findViewById(R.id.MealNameTextReg);
        durationTextReg = (TextInputEditText) rootView.findViewById(R.id.DurationTextReg);
        portionTextReg = (TextInputEditText) rootView.findViewById(R.id.PortionTextReg);
        levelTextReg = (TextInputEditText) rootView.findViewById(R.id.LevelTextReg);
        energyInPortionsTextReg = (TextInputEditText) rootView.findViewById(R.id.EnergyInPortionsTextReg);

        btnFirstStepAddMeal = (Button) rootView.findViewById(R.id.btnFirstStepAddMeal);
        btnFirstStepAddMeal.setOnClickListener(this);

        btnGoToAddedMeals = (Button) rootView.findViewById(R.id.btnGoToAddedMeals);
        btnGoToAddedMeals.setOnClickListener(this);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        IVPreviewImage = (ImageView) rootView.findViewById(R.id.imageView_Meal);
        IVPreviewImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                imageChooser();
            }
        });
        return rootView;
    }

    void imageChooser() {

        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    imageURI = String.valueOf(selectedImageUri);
                    IVPreviewImage.setImageURI(selectedImageUri);

                    BitmapDrawable drawable = (BitmapDrawable) IVPreviewImage.getDrawable();
                    bitmap = drawable.getBitmap();

                    uploadImage(bitmap);



                }
            }
        }
    }

    public void uploadImage(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        final int min = 2000;
        final int max = 8000;
        final int random = new Random().nextInt((max - min) + 1) + min;

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://shoppingeating-f8189.appspot.com");
        StorageReference imagesRef = storageRef.child(random + ".jpg");

        UploadTask uploadTask = imagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                imagesRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        String profileImageUrl=task.getResult().toString();
                        foto = task.getResult().toString(); 
                        setFoto(profileImageUrl);
                    }
                });
            }
        });
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.nav_search).setVisible(false);
        menu.findItem(R.id.my_Messages).setVisible(true);
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getFoto() {
        return foto;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        Fragment fragment = null;

        switch (v.getId()) {
            case R.id.btnFirstStepAddMeal:
                meal = mealNameTextReg.getText().toString().trim();
                duration = durationTextReg.getText().toString().trim();
                portion = portionTextReg.getText().toString().trim();
                level = levelTextReg.getText().toString().trim();
                energy = energyInPortionsTextReg.getText().toString().trim();
                foto = getFoto();

                addToMeal(userOnline, meal, duration, portion, level, energy, foto);
                break;
            case R.id.btnGoToAddedMeals:
                fragment = new MyAddedMealsFragment(userOnline);
                loadFragment(fragment);
                break;
        }
    }

    public void addToMeal(String userOnline, String meal, String duration, String portion, String level, String energy, String foto){



        final Map addMealHash = new HashMap();
        addMealHash.put("CzasTrwania", duration);
        addMealHash.put("EnergiaWporcji", energy);
        addMealHash.put("Porcja", portion);
        addMealHash.put("Poziom", level);
        addMealHash.put("Zdjecie", foto);

        FirebaseDatabase.getInstance().getReference("Users")
                .child(userOnline).child("Dinner").child(meal)
                .setValue(addMealHash).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    new CustomToastDialog(getContext(), R.string.msg_toast_succ_add_meal_all, R.id.custom_toast_message, R.layout.toast_success).show();
                    Fragment fragment = new AddMeal2Fragment(userOnline, meal, duration, portion, level, energy);
                    loadFragment(fragment);
                } else {
                    new CustomToastDialog(getContext(), R.string.msg_toast_err_message, R.id.custom_toast_message, R.layout.toast_warning).show();
                }
            }
        });
    }

    public void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentMenu, fragment).commit();
        fragmentTransaction.addToBackStack(null);
    }
}
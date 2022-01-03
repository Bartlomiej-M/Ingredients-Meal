package com.example.ingredientsmeal.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.ingredientsmeal.MainActivity;
import com.example.ingredientsmeal.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class CustomShareIngredientsDialog extends Dialog implements
        android.view.View.OnClickListener {

    public Activity activity;
    public Button btnSendInApp, btnSendOtherApp;
    public ArrayList<String> ingredientsArrayList;
    int message;

    public CustomShareIngredientsDialog(@NonNull Activity activity, int message, ArrayList<String> ingredientsArrayList) {
        super(activity);
        this.activity = activity;
        this.message = message;
        this.ingredientsArrayList = ingredientsArrayList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_share);

        btnSendInApp = (Button) findViewById(R.id.btnSendInApp);
        btnSendOtherApp = (Button) findViewById(R.id.btnSendOtherApp);

        btnSendInApp.setOnClickListener(this);
        btnSendOtherApp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSendInApp:
                dismiss();
                sendIngredients(ingredientsArrayList);
                break;
            case R.id.btnSendOtherApp:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, ingredientsArrayList.toString());
                intent.setType("text/plain");

                if (intent.resolveActivity(activity.getPackageManager()) != null) {
                    getContext().startActivity(intent);
                }
                dismiss();
                break;
        }
        dismiss();
    }

    public void sendIngredients(ArrayList<String> ingredientsArrayList) {
        CustomSendIngredientsDialog customSendIngredientsDialog =
                new CustomSendIngredientsDialog(activity, R.string.msg_question_logout, ingredientsArrayList);

        customSendIngredientsDialog.getWindow().
                setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        customSendIngredientsDialog.show();
    }
}

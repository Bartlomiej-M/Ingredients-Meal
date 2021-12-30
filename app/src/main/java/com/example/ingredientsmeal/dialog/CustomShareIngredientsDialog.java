package com.example.ingredientsmeal.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
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

    public Activity c;
    public Button btnSendInApp, btnSendOtherApp;
    public ArrayList <String> ingredientsArrayList;
    int message;

    public CustomShareIngredientsDialog(@NonNull Activity a, int message, ArrayList<String> ingredientsArrayList) {
        super(a);
        this.c = a;
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
                break;
            case R.id.btnSendOtherApp:
                Log.d("CUSTOM2: ", String.valueOf(ingredientsArrayList.toArray()));
                Log.d("CUSTOM4: ", ingredientsArrayList.toString());

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, ingredientsArrayList.toString());
                intent.setType("text/plain");

                if(intent.resolveActivity(c.getPackageManager()) != null){
                    getContext().startActivity(intent);
                }
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}

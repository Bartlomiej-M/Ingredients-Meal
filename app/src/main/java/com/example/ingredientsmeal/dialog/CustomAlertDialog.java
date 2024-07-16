package com.example.ingredientsmeal.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.ingredientsmeal.MainActivity;
import com.example.ingredientsmeal.R;
import com.google.firebase.auth.FirebaseAuth;

public class CustomAlertDialog extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button yes, no;
    int message;

    public CustomAlertDialog(@NonNull Activity a, int message) {
        super(a);
        this.c = a;
        this.message = message;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_alert);
        TextView txt_message = findViewById(R.id.txt_alert_dialog);
        yes = (Button) findViewById(R.id.btn_yes);
        no = (Button) findViewById(R.id.btn_no);
        txt_message.setText(message);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                FirebaseAuth.getInstance().signOut();
                new CustomToastDialog(getContext(), R.string.msg_correct_logout, R.id.custom_toast_message, R.layout.toast_success).show();
                Intent myIntent = new Intent(getContext(), MainActivity.class);
                c.startActivity(myIntent);
                dismiss();
                break;
            case R.id.btn_no:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}

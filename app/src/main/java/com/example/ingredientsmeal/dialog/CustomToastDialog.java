package com.example.ingredientsmeal.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class CustomToastDialog extends Toast {
    public Context context;
    public int message, toastCustom;

    public CustomToastDialog(Context context, int message, int txt_message,
                             int toastCustom) {
        super(context);
        this.context = context;
        this.message = message;
        this.toastCustom = toastCustom;


        View view = LayoutInflater.from(context).inflate(toastCustom, null);
        TextView txtMsg = view.findViewById(txt_message);
        setGravity(Gravity.CENTER, 0, 300);
        txtMsg.setText(message);
        setDuration(Toast.LENGTH_LONG);
        setView(view);

    }
}
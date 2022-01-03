package com.example.ingredientsmeal.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.ingredientsmeal.R;
import com.example.ingredientsmeal.models.SendMessageModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CustomSendIngredientsDialog extends Dialog implements
        android.view.View.OnClickListener {

    private Activity activity;
    private TextInputEditText recipientField, contentsField;
    private Button btnSendInAppAprove;
    private ArrayList<String> ingredientsArrayList;
    int message;
    public String consignorField, consignor;


    public CustomSendIngredientsDialog(@NonNull Activity activity, int message, ArrayList<String> ingredientsArrayList) {
        super(activity);
        this.activity = activity;
        this.message = message;
        this.ingredientsArrayList = ingredientsArrayList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_send_message);

        btnSendInAppAprove = (Button) findViewById(R.id.btnSendInAppAprove);
        btnSendInAppAprove.setOnClickListener(this);

        contentsField = (TextInputEditText) findViewById(R.id.contentsField);
        contentsField.setText(ingredientsArrayList.toString());

        recipientField = (TextInputEditText) findViewById(R.id.recipientField);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        getConsignorName();

    }

    public String getConsignorName() {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        Query query = rootRef.child("Users").orderByChild("uid").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    consignorField = ds.getKey();
                    consignor = consignorField;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(activity,
                        "Problem z połączeniem, sprawdzi czy jesteś wciąż zalogowany", Toast.LENGTH_LONG).show();
            }
        };
        query.addListenerForSingleValueEvent(valueEventListener);
        return consignorField;
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void sendMessage() {
        String recipient = recipientField.getText().toString().trim();
        String contents = contentsField.getText().toString().trim();

        Date currentTime = Calendar.getInstance().getTime();
        String data = String.valueOf(currentTime);

        Log.d("No to zobaczmy", recipient + " " + contents + " " + consignor + " " + data);

        SendMessageModel sendMessageModel = new SendMessageModel(consignor, contents);

        FirebaseDatabase.getInstance().getReference("Users")
                .child(recipient).child("Messages").child(data)
                .setValue(sendMessageModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    dismiss();
                    new CustomToastDialog(getContext(), R.string.msg_toast_succ_message, R.id.custom_toast_message, R.layout.toast_success).show();

                } else {
                    new CustomToastDialog(getContext(), R.string.msg_toast_err_message, R.id.custom_toast_message, R.layout.toast_warning).show();
                }
            }
        });
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSendInAppAprove:
                sendMessage();
/*                clubkey = String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getUid());
                Log.d("SDSDSDDS", clubkey);


                Toast.makeText(activity, clubkey, Toast.LENGTH_LONG).show();*/
                break;
        }
    }
}
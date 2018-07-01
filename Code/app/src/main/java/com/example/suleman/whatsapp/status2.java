package com.example.suleman.whatsapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class status2 extends AppCompatActivity {

    EditText e;
    Button b1;
    Button b2;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status2);

        userId= FirebaseAuth.getInstance().getCurrentUser().getUid();


        e=(EditText)findViewById(R.id.editText);
        b1=(Button)findViewById(R.id.button);
        b2=(Button)findViewById(R.id.button2);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(e.getText().toString()))
                {
                    FirebaseDatabase.getInstance().getReference("Users").
                            child(userId).child("status").setValue(e.getText().toString());

                }
            }
        });

    }
}

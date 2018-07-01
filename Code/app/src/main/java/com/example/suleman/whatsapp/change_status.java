package com.example.suleman.whatsapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.FirebaseDatabase;

public class change_status extends AppCompatActivity {

    EditText sign;
    Button b1;
    Button b2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_status);

        sign=(EditText) findViewById(R.id.textView7);
        b1=(Button)findViewById(R.id.button3);
        b2=(Button)findViewById(R.id.button6);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(sign.getText().toString()))
                {
                    FirebaseDatabase.getInstance().getReference("Users").
                            child("qUaq5cLEBCRFik5dtnfN6qaQdCo1").child("status").setValue(sign.getText().toString());
                }
            }
        });
    }
}

package com.example.suleman.whatsapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.FirebaseDatabase;

public class changeUName extends AppCompatActivity {

    EditText e;
    Button b1;
    Button b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_uname);

        e=(EditText)findViewById(R.id.editText);
        b1=(Button)findViewById(R.id.button);
        b2=(Button)findViewById(R.id.button2);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(e.getText().toString()))
                {
                    FirebaseDatabase.getInstance().getReference("Users").
                            child("qUaq5cLEBCRFik5dtnfN6qaQdCo1").child("name").setValue(e.getText().toString());

                }
            }
        });


    }
}

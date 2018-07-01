package com.example.suleman.whatsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class setting extends AppCompatActivity {

    private TextView sign;
    private TextView inviteFried;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        sign=(TextView) findViewById(R.id.tv_profile_features);
        sign.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        try{
                            //Intent intent =new Intent("com.example.awais.myapplication.gallery");
                            //startActivity(intent);

                            Intent intent = new Intent("com.example.suleman.whatsapp.updateProfileF");
                            startActivity(intent);
                        }
                        catch (Exception e){
                            Toast.makeText(setting.this,"Exception in intent",Toast.LENGTH_SHORT).show();
                        }

                    }

                });

        inviteFried = (TextView)findViewById(R.id.tv_invite_friend);

        inviteFried.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(setting.this, InviteFriends.class));
                finish();

            }
        });
    }
}

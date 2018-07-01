    package com.example.suleman.whatsapp;

import android.app.ActionBar;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;

import static java.lang.Thread.sleep;


    public class LoginActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button btnSignup, btnLogin, btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);


        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {


            //FirebaseAuth firauth = FirebaseAuth.getInstance();
            Log.e("UID", auth.getCurrentUser().getUid().toString());
            DatabaseReference refDoctor = FirebaseDatabase.getInstance().getReference().getRoot().child("Doctors");
            DatabaseReference refPatient = FirebaseDatabase.getInstance().getReference().getRoot().child("Patients");
            DatabaseReference refStudent = FirebaseDatabase.getInstance().getReference().getRoot().child("Students");

            if(refDoctor.child(auth.getCurrentUser().getUid()) != null) {
                refDoctor.child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()) {
                            //  String type = dataSnapshot.child("type").getValue().toString();
                            //Log.e("type",type);

                            Log.e("login","Doctor");


                            Intent intent = new Intent(LoginActivity.this, mainPageDoctor.class);
                            startActivity(intent);
                            finish();


                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

            if(refPatient.child(auth.getCurrentUser().getUid()) != null) {
                Log.e("loginListener : ","Patient");

                refPatient.child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {



                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()) {
                            //  String type = dataSnapshot.child("type").getValue().toString();
                            //Log.e("type",type);

                            Log.e("login","Patient");

                            Intent intent = new Intent(LoginActivity.this, mainPage.class);
                            startActivity(intent);
                            finish();


                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

            if(refStudent.child(auth.getCurrentUser().getUid()) != null) {
                refStudent.child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()) {
                            //  String type = dataSnapshot.child("type").getValue().toString();
                            Log.e("login","Student");

                            Intent intent = new Intent(LoginActivity.this, mainPageStudent.class);
                            startActivity(intent);
                            finish();


                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }


        }
        else
        {
            Log.e("UID", "No user present");


       // else
        //{

        /*for(int i=0; i<40 ;i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
            // set the view now
            setContentView(R.layout.activity_login);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            //     setSupportActionBar(toolbar);

            inputEmail = (EditText) findViewById(R.id.email);
            inputPassword = (EditText) findViewById(R.id.password);
            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            btnSignup = (Button) findViewById(R.id.btn_signup);
            btnLogin = (Button) findViewById(R.id.btn_login);
            //btnReset = (Button) findViewById(R.id.btn_reset_password);

            //Get Firebase auth instance
            auth = FirebaseAuth.getInstance();

            btnSignup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
            });

        /*btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
             }
        });*/

            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String email = inputEmail.getText().toString();
                    final String password = inputPassword.getText().toString();

                    if (TextUtils.isEmpty(email)) {
                        Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (TextUtils.isEmpty(password)) {
                        Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    progressBar.setVisibility(View.VISIBLE);

                    //authenticate user
                    auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.
                                    progressBar.setVisibility(View.GONE);
                                    if (!task.isSuccessful()) {
                                        // there was an error
                                        if (password.length() < 6) {
                                            inputPassword.setError(getString(R.string.minimum_password));
                                        } else {
                                            Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                        }
                                    } else {

                                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().getRoot().child("Users");
                                        FirebaseAuth firauth = FirebaseAuth.getInstance();

                                        DatabaseReference refDoctor = FirebaseDatabase.getInstance().getReference().getRoot().child("Doctors");
                                        DatabaseReference refPatient = FirebaseDatabase.getInstance().getReference().getRoot().child("Patients");
                                        DatabaseReference refStudent = FirebaseDatabase.getInstance().getReference().getRoot().child("Students");


                                        if (refDoctor.child(firauth.getCurrentUser().getUid()) != null) {
                                            refDoctor.child(firauth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {

                                                    if (dataSnapshot.exists()) {
                                                        //  String type = dataSnapshot.child("type").getValue().toString();
                                                        //Log.e("type",type);

                                                        Log.e("login", "Doctor");


                                                        Intent intent = new Intent(LoginActivity.this, mainPageDoctor.class);
                                                        startActivity(intent);
                                                        finish();


                                                    }
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });

                                        }

                                        if (refPatient.child(firauth.getCurrentUser().getUid()) != null) {
                                            refPatient.child(firauth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {

                                                    if (dataSnapshot.exists()) {
                                                        //  String type = dataSnapshot.child("type").getValue().toString();
                                                        //Log.e("type",type);

                                                        Log.e("login", "Patient");

                                                        Intent intent = new Intent(LoginActivity.this, mainPage.class);
                                                        startActivity(intent);
                                                        finish();


                                                    }
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });

                                        }

                                        if (refStudent.child(firauth.getCurrentUser().getUid()) != null) {
                                            refStudent.child(firauth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {

                                                    if (dataSnapshot.exists()) {
                                                        //  String type = dataSnapshot.child("type").getValue().toString();
                                                        Log.e("login", "Student");

                                                        Intent intent = new Intent(LoginActivity.this, mainPageStudent.class);
                                                        startActivity(intent);
                                                        finish();


                                                    }
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });

                                        }


                                    }
                                }
                            });
                }
            });

        }
   }
}



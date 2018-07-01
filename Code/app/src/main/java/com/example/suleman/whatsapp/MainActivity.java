package com.example.suleman.whatsapp;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.LoginFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    //private static final String TAG ="PhoneAuth";
    /*
    private EditText phoneText;
    private EditText codeText;
    private Button verifyButton;
    private Button sendButton;
    private Button resendButton;
    private Button signoutButton;
    private TextView statusText;
    private static Button sign;
*/

    private EditText inputEmail, inputPassword, name, qualification;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private Spinner type;
    private ProgressBar progressBar;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);



        setContentView(R.layout.activity_main);


        FirebaseApp.initializeApp(this);
        //startActivity(new Intent(MainActivity.this, LoginActivity.class));



        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();


        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);


        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        name = (EditText) findViewById(R.id.name);
        qualification = (EditText) findViewById(R.id.qualification);
        type = (Spinner) findViewById(R.id.spinnerAccountType);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        //btnResetPassword = (Button) findViewById(R.id.btn_reset_password);

        /*btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(SignupActivity.this, ResetPasswordActivity.class));
            }
        });*/

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                final String nameString = name.getText().toString();
                final String qualificationString = qualification.getText().toString();
                final String typeString = type.getSelectedItem().toString();


                //Log.e("type", typeString+"");

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(MainActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {

                                    if(typeString.equals("Patient")) {
                                        DatabaseReference db_ref = FirebaseDatabase.getInstance().getReference().getRoot().child("Patients");
                                        FirebaseAuth firauth = FirebaseAuth.getInstance();

                                        db_ref.child(firauth.getCurrentUser().getUid()).child("name").setValue(nameString);
                                        db_ref.child(firauth.getCurrentUser().getUid()).child("qualification").setValue(qualificationString);
                                        db_ref.child(firauth.getCurrentUser().getUid()).child("type").setValue(typeString);

                                        db_ref.child(firauth.getCurrentUser().getUid()).child("phone").setValue("03210000003");

                                        startActivity(new Intent(MainActivity.this, mainPage.class));
                                        finish();

                                    }
                                    else if(typeString.equals("Doctor"))
                                    {
                                        DatabaseReference db_ref = FirebaseDatabase.getInstance().getReference().getRoot().child("Doctors");
                                        FirebaseAuth firauth = FirebaseAuth.getInstance();

                                        db_ref.child(firauth.getCurrentUser().getUid()).child("name").setValue(nameString);
                                        db_ref.child(firauth.getCurrentUser().getUid()).child("qualification").setValue(qualificationString);
                                        db_ref.child(firauth.getCurrentUser().getUid()).child("type").setValue(typeString);

                                        db_ref.child(firauth.getCurrentUser().getUid()).child("phone").setValue("03210000003");

                                        startActivity(new Intent(MainActivity.this, mainPageDoctor.class));
                                        finish();

                                    }
                                    else if(typeString.equals("Student"))
                                    {

                                        DatabaseReference db_ref = FirebaseDatabase.getInstance().getReference().getRoot().child("Students");
                                        FirebaseAuth firauth = FirebaseAuth.getInstance();

                                        db_ref.child(firauth.getCurrentUser().getUid()).child("name").setValue(nameString);
                                        db_ref.child(firauth.getCurrentUser().getUid()).child("qualification").setValue(qualificationString);
                                        db_ref.child(firauth.getCurrentUser().getUid()).child("type").setValue(typeString);

                                        db_ref.child(firauth.getCurrentUser().getUid()).child("phone").setValue("03210000003");

                                        startActivity(new Intent(MainActivity.this, mainPageStudent.class));
                                        finish();

                                    }

                                }
                            }
                        });

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //progressBar.setVisibility(View.GONE);
    }









        //aage purana


        //
            //Suleman normal routine
            //Intent intent = new Intent("com.example.suleman.whatsapp.mainPage");
            //startActivity(intent);

       /* phoneText=(EditText) findViewById(R.id.phoneText);
        codeText=(EditText) findViewById(R.id.codeText);
        verifyButton=(Button) findViewById(R.id.verifyButton);
        sendButton=(Button) findViewById(R.id.sendButton);
        resendButton=(Button) findViewById(R.id.resendButton);*/


       //suleman  signoutButton=(Button)findViewById(R.id.signoutButton);
        //=(TextView)findViewById(R.id.statusText);

        //verifyButton.setEnabled(false);
        //resendButton.setEnabled(false);

       /* Suleman signoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.example.suleman.whatsapp.mainPage");

                startActivity(intent);
            }
        });*/
        //statusText.setText("Signed Out");








//
//        sign=(Button) findViewById(R.id.next);
//        sign.setOnClickListener(
//                new View.OnClickListener() {
//
//                    @Override
//                    public void onClick(View view) {
//
//                        try{
//                            Intent intent = new Intent("com.example.awais.test.mainPage");
//                            startActivity(intent);
//                        }
//                        catch (Exception e){
//                            Toast.makeText(MainActivity.this,"Exception in intent",Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
//
//        });

    }

   /* public void sendCode(View view){
        String phoneNumber =phoneText.getText().toString();
        setUpVerificationalCallback();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verif
                60,// Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                verificationCallbacks);        // OnVerificationStateChangedCallbacks
    }

    private void setUpVerificationalCallback(){

        verificationCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {


            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                signoutButton.setEnabled(true);
                statusText.setText("Signed In");
                resendButton.setEnabled(false);
                verifyButton.setEnabled(false);
                codeText.setText("");
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                if(e instanceof FirebaseAuthInvalidCredentialsException){
                    Log.d(TAG,"Invalid Credential: "
                            +e.getLocalizedMessage());
                }
                else
                if(e instanceof FirebaseTooManyRequestsException){
                    Log.d(TAG,"SMS Quota exceeded");
                }
            }

            @Override
            public void onCodeSent(String verificationId,PhoneAuthProvider.ForceResendingToken token){
                phoneVerificationId=verificationId;
                resendingToken=token;

                verifyButton.setEnabled(true);
                sendButton.setEnabled(false);
                resendButton.setEnabled(true);
            }
        };
    }

    public void verifyCode(View view){
        String code=codeText.getText().toString();
        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(phoneVerificationId,code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            signoutButton.setEnabled(true);
                            codeText.setText("");
                            statusText.setText("Signed In");
                            Toast.makeText(MainActivity.this,"Authenticaton successfully done",Toast.LENGTH_SHORT).show();
                            resendButton.setEnabled(false);
                            verifyButton.setEnabled(false);
                            FirebaseUser user=task.getResult().getUser();

                        }
                        else
                        if(task.getException() instanceof FirebaseAuthInvalidCredentialsException){
                            //the verifiction code entered was invalid
                        }
                    }
                });
    }

    public void resendCode(View view) {

        String phoneNumber = phoneText.getText().toString();
        setUpVerificationalCallback();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verif
                60,// Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                verificationCallbacks,
                resendingToken);        // OnVerificationStateChangedCallbacks

    }


    public void signOut(View view){
        //FirebaseAuth.getInstance().signOut();

        String curr=FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference("Users").child(curr).child("phone").setValue(phoneText.getText().toString());

        statusText.setText("Signed Out");

        signoutButton.setEnabled(false);
        sendButton.setEnabled(true);
        try{
            //  setContentView(R.layout.profile);

            Intent intent = new Intent("com.example.awais.test.mainPage");

            startActivity(intent);

        }
        catch (Exception e){
            Toast.makeText(MainActivity.this,"Exception in intent",Toast.LENGTH_SHORT).show();
        }

    }



    public void chatPage(View view){
        try{


            Intent intent = new Intent("com.example.awais.test.mainPage");
            startActivity(intent);
        }
        catch (Exception e){
            Toast.makeText(MainActivity.this,"Exception in intent",Toast.LENGTH_SHORT).show();
        }

    }*/

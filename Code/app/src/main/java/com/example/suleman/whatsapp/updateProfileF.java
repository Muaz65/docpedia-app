package com.example.suleman.whatsapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class updateProfileF extends AppCompatActivity {

    private TextView sign,sign2;
    private ImageView v;

    private StorageReference mImageStorage;

    private static final int Gallery_Pick=1;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile_f);
        mImageStorage=FirebaseStorage.getInstance().getReference();
        v=(ImageView)findViewById(R.id.image);
        userId= FirebaseAuth.getInstance().getCurrentUser().getUid();


        sign=(TextView) findViewById(R.id.name);
        sign.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        try{
                            Intent intent = new Intent("com.example.suleman.whatsapp.changeUName");
                            startActivity(intent);
                        }
                        catch (Exception e){
                            Toast.makeText(updateProfileF.this,"Exception in intent",Toast.LENGTH_SHORT).show();
                        }

                    }

                });

        sign2=(TextView) findViewById(R.id.status);
        sign2.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        try{
                            Intent intent = new Intent("com.example.suleman.whatsapp.change_status");
                            startActivity(intent);
                        }
                        catch (Exception e){
                            Toast.makeText(updateProfileF.this,"Exception in intent",Toast.LENGTH_SHORT).show();
                        }

                    }

                });

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent=new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(galleryIntent,"Select Image"),Gallery_Pick);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode ==Gallery_Pick && resultCode== RESULT_OK)
        {

            Uri img=data.getData();


            StorageReference filepath=mImageStorage.child("profile_image").child(userId+".jpg");

            filepath.putFile(img).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                    String url=task.getResult().getDownloadUrl().toString();

                    FirebaseDatabase.getInstance().getReference("Users").child(userId).child("image").setValue(url);

                            Picasso.with(updateProfileF.this).load(url).into(v);

                }

            });
        }
    }
}

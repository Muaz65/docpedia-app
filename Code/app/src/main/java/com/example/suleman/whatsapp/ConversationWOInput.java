package com.example.suleman.whatsapp;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Suleman on 1/6/2018.
 */

public class ConversationWOInput extends BaseActivity implements HolderMe.ClickListener, HolderYou.ClickListener {

    private RecyclerView mRecyclerView;
    private ConversationRecyclerView mAdapter;
    private EditText text;
    private Button send;
    private DatabaseReference mRootRef;
    private TextView tv_selection;
    private  KamChalau chatUser;
    private Calendar c;
    private String curr;
    //private ImageButton addPic;
    private StorageReference mImage;
    private String user;
    private Button submit;
    int count = 0;
    LinearLayout submitUi;
    boolean end = false;

    //private Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+5:00"));

    private List<ChatData> messageList=new ArrayList<>();

    private static final int Gallery_Pick=1;
    Integer REQUEST_CAMERA=2;
    Integer Select_File=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);


        setContentView(R.layout.activity_conversation_woinput);

        user= FirebaseAuth.getInstance().getCurrentUser().getUid();
        submitUi = (LinearLayout) findViewById(R.id.confirm);
        submit = (Button) findViewById(R.id.btn_ok);
        //  addPic=(ImageButton)findViewById(R.id.bt_attachment);
        chatUser=(KamChalau) getIntent().getSerializableExtra("object");
        mRootRef= FirebaseDatabase.getInstance().getReference();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ConversationRecyclerView(getBaseContext(),messageList,this,this);
        mRecyclerView.setAdapter(mAdapter);

        mImage= FirebaseStorage.getInstance().getReference();

        loadMessage();
        /*mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.smoothScrollToPosition(mRecyclerView.getAdapter().getItemCount() - 1);
            }
        }, 1000);*/

        // addPic.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
//
        //              /*Intent galleryIntent=new Intent();
        //            galleryIntent.setType("image/*");
        //         galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

        //        startActivityForResult(Intent.createChooser(galleryIntent,"Select Image"),Gallery_Pick);*/
        //      selectImage();
        //   }
        // });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*Intent galleryIntent=new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(galleryIntent,"Select Image"),Gallery_Pick);*/
                //selectImage();


                finish();


            }
        });


        tv_selection=(TextView)findViewById(R.id.tv_selection1);

        text = (EditText) findViewById(R.id.et_message);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerView.smoothScrollToPosition(mRecyclerView.getAdapter().getItemCount() - 1);
                    }
                }, 500);
            }
        });
        send = (Button) findViewById(R.id.bt_send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();

            }
        });



        //setData();
    }

    public void selectImage()
    {
        Log.e("function call", "true");
        final CharSequence[] options={"Gallery","Cancel"};

        AlertDialog.Builder alert=new AlertDialog.Builder(ConversationWOInput.this);
        alert.setTitle("Add Image");
        alert.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(options[i].equals("Gallery"))
                {
                    Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    // galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

                    startActivityForResult(Intent.createChooser(intent,"Select Image"),Select_File);
                    // startActivityForResult();
                }
                else if(options[i].equals("Cancel"))
                {
                    dialogInterface.dismiss();
                }
            }
        });
        alert.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("before upload", "true");

        if((requestCode == Gallery_Pick)&&(resultCode == RESULT_OK))
        {
            Uri imageUri=data.getData();

           /* File thumbnail=new File(imageUri.getPath());
            Bitmap bm = null;
            try {
                 bm= new Compressor(this).setMaxWidth(500).setMaxHeight(500).setQuality(80).compressToBitmap(thumbnail);
            } catch (IOException e) {
                e.printStackTrace();
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
           final  byte[] thumb_byte  = baos.toByteArray();*/

            //Log.e("why",requestCode+"");
            Log.e("upload", "working");

            final String curr_user_ref="messages/"+user+"/"+chatUser.getChatId();
            final String chat_user_ref="messages/"+chatUser.getChatId()+"/"+user;

            DatabaseReference user_message_push=mRootRef.child("messages")
                    .child(user).child(chatUser.getChatId()).push();

            final String push_id=user_message_push.getKey();

            StorageReference file=mImage.child("message_images").child(push_id+".jpg");

            file.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if(task.isSuccessful())
                    {
                        String url=  new String("asd");//task.getResult().getDownloadUrl().toString();

                        Map messageMap=new HashMap();
                        messageMap.put("message",url);
                        messageMap.put("type","image");
                        messageMap.put("time", "5:00");
                        messageMap.put("from",user);

                        Map messageUserMap =new HashMap();
                        messageUserMap.put(curr_user_ref+"/"+push_id,messageMap);
                        messageUserMap.put(chat_user_ref+"/"+push_id,messageMap);

                        mRootRef.updateChildren(messageUserMap, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                if(databaseError!=null)
                                {
                                    Log.d("Chat_log",databaseError.getMessage().toString());
                                }
                            }
                        });

                    }
                }
            });

        }
    }

    private void loadMessage() {

        //Log.e("ID",""+chatUser.getChatId());
        // mRootRef.child("messages").child("qUaq5cLEBCRFik5dtnfN6qaQdCo1").keepSynced(true);
        mRootRef.child("messages").child(user).child(chatUser.getChatId()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChatData message= new ChatData();
                //message =    (ChatData)(dataSnapshot.getValue(ChatData.class));

                //ChatData newmessage = ChatData();
                //dataSnapshot.child("message").getValue().toString();

                Log.e("text1",dataSnapshot.child("message").getValue().toString()+"");
                //Log.e("text1",message.getText()+"?");
                message.setFrom(dataSnapshot.child("from").getValue().toString()+"");
                message.setText(dataSnapshot.child("message").getValue().toString()+"");
                message.setTime(dataSnapshot.child("time").getValue().toString()+"");
                message.setType(dataSnapshot.child("type").getValue().toString()+"");

                messageList.add(message);
                // Toast.makeText(getBaseContext(),""+message.getText(),Toast.LENGTH_SHORT).show();

                mAdapter.notifyDataSetChanged();
                // Toast.makeText(getBaseContext(),"Gud",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                ChatData message=   new ChatData();// dataSnapshot.getValue(ChatData.class);

                message.setFrom(dataSnapshot.child("from").getValue().toString()+"");
                message.setText(dataSnapshot.child("message").getValue().toString()+"");
                message.setTime(dataSnapshot.child("time").getValue().toString()+"");
                message.setType(dataSnapshot.child("type").getValue().toString()+"");

                Log.e("text2",message.getText()+"");
                messageList.add(message);
                // Toast.makeText(getBaseContext(),""+message.getText(),Toast.LENGTH_SHORT).show();

                mAdapter.notifyDataSetChanged();
                // Toast.makeText(getBaseContext(),"Gud",Toast.LENGTH_SHORT).show();



            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //FirebaseDatabase.getInstance().getReference().keepSynced(true);
    }

    private void sendMessage() {
        if (!end) {
            final String message = text.getText().toString();



        /*Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("h:mm a");
        date.setTimeZone(TimeZone.getTimeZone("GMT+5:00"));
        final String localTime = date.format(currentLocalTime);*/


            if (!TextUtils.isEmpty(message)) {
                // c=Calendar.;
                //SimpleDateFormat df = new SimpleDateFormat("h:mm a");
                String curr_user_ref = "messages/" + user + "/" + chatUser.getChatId();
                String chat_user_ref = "messages/" + chatUser.getChatId() + "/" + user;

                DatabaseReference user_message_push = mRootRef.child("messages")
                        .child(user).child(chatUser.getChatId()).push();

                String push_id = user_message_push.getKey();
                // curr=df.format(c.getTime());

                Map messageMap = new HashMap();
                messageMap.put("message", message);
                messageMap.put("type", "text");
                messageMap.put("time", "5:00");
                messageMap.put("from", user);

                Map messageUserMap = new HashMap();
                messageUserMap.put(curr_user_ref + "/" + push_id, messageMap);
                messageUserMap.put(chat_user_ref + "/" + push_id, messageMap);
                text.setText("");


                mRootRef.updateChildren(messageUserMap, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if (databaseError != null) {
                            Log.d("Chat_log", databaseError.getMessage().toString());
                        }
                    }
                });


                Map chatInfoMap = new HashMap();
                chatInfoMap.put("name", chatUser.getName());
                chatInfoMap.put("time", "5:00");
                chatInfoMap.put("lastmessage", message);


                Map chatUserMap = new HashMap();
                chatUserMap.put("ChatTab/" + user + "/" + chatUser.getChatId(), chatInfoMap);
                chatUserMap.put("ChatTab/" + chatUser.getChatId() + "/" + user, chatInfoMap);

                mRootRef.updateChildren(chatUserMap, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if (databaseError != null) {
                            Log.d("Chat_log", databaseError.getMessage().toString());
                        }
                    }
                });

                // mRootRef.child("ChatTab").child("qUaq5cLEBCRFik5dtnfN6qaQdCo1").keepSynced(true);
/*
            mRootRef.child("ChatTab").child(user).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
*/

            }
        }
    }
    public List<ChatData> setData(){
        List<ChatData> data = new ArrayList<>();

        String text[] = {"15 September","Hi, Julia! How are you?", "Hi, Joe, Vikings is coming on 29 November! :) ", "I'm not fine. Wanna go out somewhere?", "Yes! Fast Happy maybe?", "Great idea! You can come 9:00 pm? :)))", "Ok!", "Have you seen Haziq", "This is urgent.", "No","He will be at hostel","Ok","See you tomorrow","OK,Bye"};
        String time[] = {"", "5:30pm", "5:35pm", "5:36pm", "5:40pm", "5:41pm", "5:42pm", "5:40pm", "5:41pm", "5:42pm","5:42pm","5:43pm","5:43pm","5:50pm"};
        String type[] = {"0", "2", "1", "1", "2", "1", "2", "2", "2", "1","1","2","2","1"};

        for (int i=0; i<text.length; i++){
            ChatData item = new ChatData();
            item.setType(type[i]);
            item.setText(text[i]);
            data.add(item);
        }

        return data;
    }





    private void toggleSelection(int position) {
        mAdapter.toggleSelection (position);
        if (mAdapter.getSelectedItemCount()>0){
            tv_selection.setVisibility(View.VISIBLE);
        }else
            tv_selection.setVisibility(View.GONE);


        this.runOnUiThread(new Runnable() {
            public void run()
            {
                tv_selection.setText("Delete ("+mAdapter.getSelectedItemCount()+")");
            }
        });

    }

    @Override
    public void onItemClicked(int position) {

    }

    @Override
    public boolean onItemLongClicked(int position) {
        toggleSelection(position);
        return true;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_userphoto, menu);
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}

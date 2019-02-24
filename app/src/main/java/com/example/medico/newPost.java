package com.example.medico;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;

import com.example.medico.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import id.zelory.compressor.Compressor;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;


public class newPost extends AppCompatActivity {
    private static final int Max_Length = 200 ;
    Toolbar newPostToolbar;
     ImageView postImage;
     EditText postTitle;
     EditText postSubject;
     FloatingActionButton floatingPost;
     Uri postImageUri=null;
     String user_id;
     boolean isChanged = false;
     ProgressBar progressBarImage;
     StorageReference storageReference;
     FirebaseAuth mAuth;
     Bitmap compressedImageFile;
    DatabaseReference databasePosts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        newPostToolbar=findViewById(R.id.newPostToolbar);
        setSupportActionBar(newPostToolbar);
        getSupportActionBar().setTitle(R.string.createnewpost);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        User user=new User();
        storageReference= FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        user_id= mAuth.getCurrentUser().getUid();
        databasePosts = FirebaseDatabase.getInstance().getReference("Posts");


        postTitle = findViewById(R.id.postTitle);
        postSubject= findViewById(R.id.postSubject);
        floatingPost = findViewById(R.id.floatingPost);
        postImage= findViewById(R.id.postImage);
        progressBarImage=findViewById(R.id.progressBarImage);

        postImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

                    if(ContextCompat.checkSelfPermission(newPost.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                        Toast.makeText(newPost.this, "Permission Denied", Toast.LENGTH_LONG).show();
                        ActivityCompat.requestPermissions(newPost.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

                    } else {
                         BringImagePicker();

                    }

                } else {

                    BringImagePicker();

                }

            }

        });
            floatingPost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final String title = postTitle.getText().toString();
                    final String subject = postSubject.getText().toString();
                    if (!TextUtils.isEmpty(title) && postImageUri != null && !TextUtils.isEmpty(subject)) {

                        progressBarImage.setVisibility(View.VISIBLE);
                        final String randomName = UUID.randomUUID().toString();
                        StorageReference filePath = storageReference.child("postImages").child(randomName + ".jpg");
                        filePath.putFile(postImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                if (task.isSuccessful()) {
                                    File imageFile = new File(postImageUri.getPath());
                                    try {

                                        compressedImageFile = new Compressor(newPost.this)
                                                .setMaxHeight(720)
                                                .setMaxWidth(720)
                                                .setQuality(50)
                                                .compressToBitmap(imageFile);

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                    compressedImageFile.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                                    byte[] thumbData = baos.toByteArray();

                                    UploadTask uploadTask = storageReference.child("postImages/thumbs")
                                            .child(randomName + ".jpg").putBytes(thumbData);

                                    uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                        }
                                    });
                                    DatabaseReference currentUserPost = databasePosts.child(user_id);
                                    String downloadUri = task.getResult().getMetadata().getReference().getDownloadUrl().toString();
                              /*  Map<String, Object> postMap = new HashMap<>();
                                postMap.put("image_url", downloadUri);
                                postMap.put("image_thumb", downloadUri);
                                postMap.put("desc", postTitle);
                                postMap.put("user_id", user_id);
                                postMap.put("timestamp", FieldValue.serverTimestamp());*/
                                    currentUserPost.child("image_url").setValue(downloadUri);
                                    currentUserPost.child("image_thumb").setValue(downloadUri);
                                    currentUserPost.child("postTitle").setValue(title);
                                    currentUserPost.child("postTitle").setValue(subject);
                                    currentUserPost.child("id").setValue(user_id);
                                    currentUserPost.child("timestamp").setValue(randomName).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {

                                                Toast.makeText(newPost.this, "Post was added", Toast.LENGTH_LONG).show();
                                                Intent mainIntent = new Intent(newPost.this, HomeActivity.class);
                                                startActivity(mainIntent);
                                                finish();
                                            } else {
                                                progressBarImage.setVisibility(View.INVISIBLE);
                                            }
                                        }
                                    });

                                /*    @Override
                                    public void onComplete(@NonNull Task<DatabaseReference> task) {
                                        if(task.isSuccessful())
                                        {

                                            Toast.makeText(newPost.this, "Post was added", Toast.LENGTH_LONG).show();
                                            Intent mainIntent = new Intent(newPost.this, HomeActivity.class);
                                            startActivity(mainIntent);
                                            finish();
                                        }
                                        else{
                                            progressBarImage.setVisibility(View.INVISIBLE);
                                        }
                                    }
                                });*/
                                } else {
                                    progressBarImage.setVisibility(View.INVISIBLE);
                                }
                            }
                        });
                    }
                }
            });

            }
    private void BringImagePicker (){

        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMinCropResultSize(512,512)
                .setAspectRatio(1, 1)
                .start(newPost.this);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                postImageUri = result.getUri();
                postImage.setImageURI(postImageUri);
                isChanged = true;

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError();

            }
        }

    }
    public static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(Max_Length);
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }

}

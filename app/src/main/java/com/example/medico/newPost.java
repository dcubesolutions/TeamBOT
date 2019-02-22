package com.example.medico;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;

import com.example.medico.Model.User;
import com.google.android.gms.common.data.DataBufferObserverSet;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ServerTimestamp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.medico.Model.*;
import com.google.firebase.auth.FirebaseAuth;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;


public class newPost extends AppCompatActivity {
    private static final int Max_Length = 200;
    Toolbar newPostToolbar;
    ImageView postImage;
    EditText postTitle;
    EditText postSubject;
    FloatingActionButton floatingPost;
    Uri postImageUri = null;
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
        newPostToolbar = findViewById(R.id.newPostToolbar);
        setSupportActionBar(newPostToolbar);
        getSupportActionBar().setTitle("Create new Post");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        User user = new User();
        storageReference = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        user_id = mAuth.getCurrentUser().getUid();
        databasePosts = FirebaseDatabase.getInstance().getReference("Posts");


        postTitle = findViewById(R.id.postTitle);
        postSubject = findViewById(R.id.postSubject);
        floatingPost = findViewById(R.id.floatingPost);
        postImage = findViewById(R.id.postImage);
        progressBarImage = findViewById(R.id.progressBarImage);

        postImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (ContextCompat.checkSelfPermission(newPost.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                        Toast.makeText(newPost.this, "Permission Denied", Toast.LENGTH_LONG).show();
                        ActivityCompat.requestPermissions(newPost.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

                    } else {
                        BringImagePicker();

                    }

                } else {

                    BringImagePicker();

                }

            }

            private void BringImagePicker() {

                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setMinCropResultSize(512, 512)
                        .setAspectRatio(1, 1)
                        .start(newPost.this);

            }

        });
        floatingPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String title = postTitle.getText().toString();
                final String subject = postTitle.getText().toString();

                if (!TextUtils.isEmpty(title) && postImageUri != null && !TextUtils.isEmpty(subject)) {

                    progressBarImage.setVisibility(View.VISIBLE);

                    final String randomName = UUID.randomUUID().toString();

                    // PHOTO UPLOAD
                    File newImageFile = new File(postImageUri.getPath());
                    try {

                        compressedImageFile = new Compressor(newPost.this)
                                .setMaxHeight(720)
                                .setMaxWidth(720)
                                .setQuality(50)
                                .compressToBitmap(newImageFile);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    compressedImageFile.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] imageData = baos.toByteArray();

                    final StorageReference filePath1 = storageReference.child("post_images").child(randomName + ".jpg");
                    UploadTask filePath = storageReference.child("post_images").child(randomName + ".jpg").putBytes(imageData);
                    filePath.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull final Task<UploadTask.TaskSnapshot> task) {

                            //final String downloadUri = task.getResult().getDownloadUrl().toString();

                            if (task.isSuccessful()) {

                                File newThumbFile = new File(postImageUri.getPath());
                                try {

                                    compressedImageFile = new Compressor(newPost.this)
                                            .setMaxHeight(100)
                                            .setMaxWidth(100)
                                            .setQuality(1)
                                            .compressToBitmap(newThumbFile);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                compressedImageFile.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                                byte[] thumbData = baos.toByteArray();

                                UploadTask uploadTask = storageReference.child("post_images/thumbs")
                                        .child(randomName + ".jpg").putBytes(thumbData);

                                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                        String downloadUri = filePath1.getDownloadUrl().toString();

                                        /*Map<String, Object> postMap = new HashMap<>();
                                        postMap.put("image_url", downloadUri);
                                        postMap.put("image_thumb", downloadUri);
                                        postMap.put("desc", subject);
                                        postMap.put("user_id",user_id );
                                        postMap.put("timestamp", FieldValue.serverTimestamp());*/
                                        DatabaseReference currentUserPost = databasePosts.child(user_id);
                                        //currentUserPost.child("image_url").setValue(downloadUri);
                                        currentUserPost.child("postTitle").setValue(title);
                                        currentUserPost.child("postSubject").setValue(subject);
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


                                                }

                                                progressBarImage.setVisibility(View.INVISIBLE);

                                            }
                                        });

                                    }
                                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        String downloadUri= taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                                        databasePosts.child(user_id).child("image_url").setValue(downloadUri);
                                    }
                                });

                            } else {

                                progressBarImage.setVisibility(View.INVISIBLE);

                            }

                        }
                    });


                }


            }

        });

    }  @Override
    protected void onActivityResult ( int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                postImageUri = result.getUri();
                postImage.setImageURI(postImageUri);
                isChanged=true;
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError();

            }
        }

    }

}






       /* public static String random () {
            Random generator = new Random();
            StringBuilder randomStringBuilder = new StringBuilder();
            int randomLength = generator.nextInt(Max_Length);
            char tempChar;
            for (int i = 0; i < randomLength; i++) {
                tempChar = (char) (generator.nextInt(96) + 32);
                randomStringBuilder.append(tempChar);
            }
            return randomStringBuilder.toString();
        }

    }
}
*/
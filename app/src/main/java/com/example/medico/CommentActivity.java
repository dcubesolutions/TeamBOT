package com.example.medico;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class CommentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        getIncomingIntent();
    }
    public void getIncomingIntent(){

        if(getIntent().hasExtra("image_url"))
        {
            String imageUrl = getIntent().getStringExtra("image_url");
            setImage(imageUrl);
        }
    }
    public void setImage(String imageUrl)
    {

        ImageView image = findViewById(R.id.commentImage);
        Glide.with(this).load(imageUrl).into(image);
    }
}

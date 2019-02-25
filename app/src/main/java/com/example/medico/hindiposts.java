package com.example.medico;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.medico.Adapter.hindiPostsAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import android.os.StrictMode;
import android.os.Build.VERSION;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class hindiposts extends AppCompatActivity {

    private static final String URL = "https://api.cognitive.microsofttranslator.com/translate?api-version=3.0&to=hi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hindiposts);

        final RecyclerView hindiPosts = findViewById(R.id.hindiPosts);
        hindiPosts.setLayoutManager(new LinearLayoutManager(this));

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
//        final FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference ref = database.getReference("Posts");
//        final ArrayList<String> democontent= new ArrayList<>();
//
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                BlogPost post = dataSnapshot.getValue(BlogPost.class);
//                System.out.println(post);
//                String Title = post.getTitle();
//                democontent.add(Title);
//
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                System.out.println("The read failed: " + databaseError.getCode());
//            }
//        });
        ArrayList<String> democontent = new ArrayList<String>();
        democontent.add("Hello");
        democontent.add("Hey");
        democontent.add("bye");
        hindiPosts.setAdapter(new hindiPostsAdapter(democontent));





    }
}

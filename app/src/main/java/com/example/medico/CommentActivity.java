package com.example.medico;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.medico.Adapter.BlogRecyclerAdapter;
import com.example.medico.Adapter.CommentRecyclerAdapter;
import com.example.medico.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class CommentActivity extends AppCompatActivity {
    Button commentSendBtn;
    EditText commentText;
    private RecyclerView commentRecyclerView;
    private List<Comment> commentList;
    private CommentRecyclerAdapter commentRecyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        showComment();
        getIncomingIntent();
        commentSendBtn = findViewById(R.id.commentSendBtn);
        commentText = findViewById(R.id.commentText);
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();

        commentSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final BlogPostId blogPostId = new BlogPostId();
                final String textComent = commentText.getText().toString();
                if (!textComent.isEmpty()) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    final DatabaseReference myRef = database.getReference("Posts");
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot postchild : dataSnapshot.getChildren()) {
                                String blogPostId = postchild.getKey();
                                //String commentkey = postchild.child(commentId).child("Comments").getKey();
                                Comment comment = new Comment(mAuth.getCurrentUser().getUid(), textComent.toString());
                                String key =  myRef.child(blogPostId).getKey();
                                addComment(comment,key);
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                        public void addComment(Comment comment,String key)
                        {
                            myRef.child(key).child("Comments").push().setValue(comment).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(CommentActivity.this,"comment posted",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                    });

                }
            }
        });


    }

    public void showComment()
    {
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        commentRecyclerView = findViewById(R.id.commentRecyclerView);
        commentRecyclerView.setLayoutManager(new LinearLayoutManager(CommentActivity.this));
        commentRecyclerView.setHasFixedSize(true);
        if(mAuth.getCurrentUser()!=null)
        {
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference("Posts");

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    commentList = new ArrayList<>();
                    for(DataSnapshot comment : dataSnapshot.getChildren()){

                        for(DataSnapshot comment1 : comment.child("Comments").getChildren())
                        {
                            //String commentKey = comment1.getKey();
                            Comment commentPost = comment1.getValue(Comment.class);
                            commentList.add(commentPost);
                        }
                    }
                    commentRecyclerAdapter = new CommentRecyclerAdapter(getApplicationContext(), commentList);
                    commentRecyclerView.setAdapter(commentRecyclerAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

    }

    public void getIncomingIntent() {

        if (getIntent().hasExtra("image_url") && getIntent().hasExtra("title") && getIntent().hasExtra("desc")) {
            String imageUrl = getIntent().getStringExtra("image_url");
            String title = getIntent().getStringExtra("title");
            String desc = getIntent().getStringExtra("desc");
            setImage(imageUrl, title, desc);

        }
    }

    public void setImage(String imageUrl, String title, String desc) {

        ImageView image = findViewById(R.id.commentImage);
        Glide.with(this).load(imageUrl).into(image);
        TextView titleSet = findViewById(R.id.commentTitle);
        titleSet.setText(title);
        TextView descSet = findViewById(R.id.commentSubject);
        descSet.setText(desc);
    }

}

package com.example.medico.Adapter;


import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.example.medico.CommentActivity;
import com.example.medico.R;
import com.example.medico.UploadPosts;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class BlogRecyclerAdapter extends RecyclerView.Adapter<BlogRecyclerAdapter.MyViewHolder> {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    //FirebaseDatabase firebaseLike;
    FirebaseAuth mAuth;
    private Context mContext;
    private List<UploadPosts> blogList;
    private boolean mProcess = false;
    private boolean mProcess1 = false;

    public BlogRecyclerAdapter(Context mContext, List<UploadPosts> blogList) {
        this.mContext = mContext;
        this.blogList = blogList;
    }


   /* public BlogRecyclerAdapter(FragmentActivity activity, List<UploadPosts> bloglist) {
    }*/

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.blog_list_item, parent, false);
        //mContext = parent.getContext();
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Posts");
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final BlogRecyclerAdapter.MyViewHolder holder, final int position) {
        holder.setIsRecyclable(false);
        final String blogPostId = blogList.get(position).BlogPostId;
        holder.postTitle.setText(blogList.get(position).getUploadTitle());
        Glide.with(mContext).load(blogList.get(position).getUploadImageUrl()).into(holder.imagePost);
        final UploadPosts uploadPosts=new UploadPosts();
        holder.imagePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, CommentActivity.class);
                intent.putExtra("image_url",blogList.get(position).getUploadImageUrl());
                mContext.startActivity(intent);
            }
        });
        //Glide.with(mContext).load(blogList.get(position).getUserPhoto()).into(holder.imgPostProfile);
        //long millisecond = (long)blogList.get(position).getTimeStamp();
        //long d = new Date(millisecond).getTime();
        // String dateString = DateFormat.format("MM/dd/yyyy",new Date()).toString();
        // holder.postDate.setText(dateString);
        holder.setBlogLikeBtn(blogPostId);
        holder.setBlogLikeCount(blogPostId);
        holder.blogLikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String currentUserId = mAuth.getCurrentUser().getUid();

               mProcess=true;
                databaseReference.child(blogPostId).child("Likes").child(currentUserId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (mProcess) {
                            if (dataSnapshot.exists()) {
                                databaseReference.child(blogPostId).child("Likes").child(currentUserId).removeValue();
                                mProcess = false;
                            } else {
                                final HashMap<String, Object> hashmap = new HashMap<>();
                                hashmap.put("sender", ServerValue.TIMESTAMP);
                                databaseReference.child(blogPostId).child("Likes").child(currentUserId).setValue(hashmap);
                                //holder.blogLikeBtn.setImageDrawable(mContext.getDrawable(R.mipmap.action_like_accent));
                                mProcess = false;

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return blogList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView postTitle;
        ImageButton imagePost;
        TextView postDate;
        private ImageView blogLikeBtn;
        private TextView blogLikeCount;
        ConstraintLayout parent;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            postTitle = itemView.findViewById(R.id.blogTitle);
            imagePost = itemView.findViewById(R.id.blogImage);
            //postDate = itemView.findViewById(R.id.blogdate);
            blogLikeBtn = itemView.findViewById(R.id.blogLikeBtn);
            blogLikeCount = itemView.findViewById(R.id.blogLikeCount);
            mAuth=FirebaseAuth.getInstance();


        }

        void setBlogLikeBtn(String blogPostId){
            databaseReference.child(blogPostId).child("Likes").child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               if(dataSnapshot.exists()){
                   blogLikeBtn.setImageDrawable(mContext.getDrawable(R.drawable.upvoteiconwhite1));
               }
               else{
                  blogLikeBtn.setImageDrawable(mContext.getDrawable(R.drawable.upvoteiconwhite0));
               }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        void setBlogLikeCount(String blogPostId){

            databaseReference.child(blogPostId).child("Likes").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long count=dataSnapshot.getChildrenCount();
                blogLikeCount.setText(count+" UpVotes");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }
}


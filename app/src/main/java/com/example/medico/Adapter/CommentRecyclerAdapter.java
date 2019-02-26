package com.example.medico.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.medico.Comment;
import com.example.medico.R;
import com.example.medico.UploadPosts;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CommentRecyclerAdapter extends RecyclerView.Adapter<CommentRecyclerAdapter.MyViewHolder> {

   private FirebaseDatabase firebaseDatabase;
   private DatabaseReference databaseReference;
   private Context mContext;
   private FirebaseAuth mAuth;
   private List<Comment> commentList;

   public CommentRecyclerAdapter(Context mContext, List<Comment> commentList)
   {
       this.commentList=commentList;
       this.mContext = mContext;
   }
    @NonNull
    @Override
    public CommentRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_list_item, parent, false);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Posts");
        return new CommentRecyclerAdapter.MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CommentRecyclerAdapter.MyViewHolder holder, int position) {

        holder.commentUser.setText(commentList.get(position).getComment());
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView commentUser;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            commentUser = itemView.findViewById(R.id.commentUser);
        }
    }

}

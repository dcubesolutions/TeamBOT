package com.example.medico.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.medico.BlogPost;
import com.example.medico.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class hindiPostsAdapter extends RecyclerView.Adapter<hindiPostsAdapter.HpostsViewHolder> {
    public ArrayList<String> titles;
    public hindiPostsAdapter(ArrayList<String> data){
        this.titles = data;
    }



    @NonNull
    @Override
    public HpostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.blog_list_item, parent, false);

        return new HpostsViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull HpostsViewHolder holder, int position) {
        String title = this.titles.get(position);
        holder.text_title.setText(title);

    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class HpostsViewHolder extends RecyclerView.ViewHolder{
        private ImageView image_post;
        private TextView text_title;
        public HpostsViewHolder(View itemView){
            super(itemView);
            image_post = itemView.findViewById(R.id.blog_image);
            text_title = itemView.findViewById(R.id.blogTitle);
        }
    }

}

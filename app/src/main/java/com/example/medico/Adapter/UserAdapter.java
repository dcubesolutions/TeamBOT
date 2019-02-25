package com.example.medico.Adapter;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.medico.MessageActivity;
import com.example.medico.Model.Chat;
import com.example.medico.Model.User;
import com.example.medico.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context mContext;
    private List<User> mUser;
    private boolean isChat;

    String theLastMessage;

    public UserAdapter(Context mContext, List<User> mUser,boolean isChat){
        this.mUser= mUser;
        this.mContext=mContext;
        this.isChat=isChat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.user_item,viewGroup,false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        final User user=mUser.get(i);
        viewHolder.fName.setText(user.getfName());
       if (user.getImageUrl().equals("default")){
            viewHolder.profile_image.setImageResource(R.mipmap.ic_launcher);
        }else{
            Glide.with(mContext).load(user.getImageUrl()).into(viewHolder.profile_image);
        }

        if (isChat){
            lastMessage(user.getId(),viewHolder.last_msg);
        }else{
            viewHolder.last_msg.setVisibility(View.GONE);
        }

        viewHolder.profile_image.setImageResource(R.mipmap.ic_launcher);

        if(isChat) {
            if (user.getStatus().equals("online")) {
                viewHolder.img_on.setVisibility(View.VISIBLE);
                viewHolder.img_off.setVisibility(View.GONE);
            } else {
                viewHolder.img_on.setVisibility(View.GONE);
                viewHolder.img_off.setVisibility(View.VISIBLE);
            }
        }else{
            viewHolder.img_on.setVisibility(View.GONE);
            viewHolder.img_off.setVisibility(View.GONE);
        }



        viewHolder.profile_image.setImageResource(R.mipmap.ic_launcher);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, MessageActivity.class);
                intent.putExtra("id", user.getId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUser.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

            public TextView fName;
            public ImageView profile_image;
            private ImageView img_on;
            private ImageView img_off;
            private TextView last_msg;
            public ViewHolder(View itemView)
            {
                super(itemView);
                fName=itemView.findViewById(R.id.username);
                profile_image=itemView.findViewById(R.id.profile_image);
                img_on=itemView.findViewById(R.id.img_on);
                img_off=itemView.findViewById(R.id.img_off);
                last_msg=itemView.findViewById(R.id.last_msg);

            }
    }

    private void lastMessage(final String userid, final TextView last_msg){
        theLastMessage="default";
        final FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Chat chat=snapshot.getValue(Chat.class);
                    if(chat.getReceiver().equals(firebaseUser.getUid()) && chat.getSender().equals(userid) ||
                            chat.getReceiver().equals(userid) && chat.getSender().equals(firebaseUser.getUid())){

                        theLastMessage=chat.getMessage();
                    }
                }

                switch (theLastMessage){
                    case "default":
                        last_msg.setText("No Message");
                        break;

                        default:
                            last_msg.setText(theLastMessage);
                            break;
                }

                theLastMessage="default";
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}

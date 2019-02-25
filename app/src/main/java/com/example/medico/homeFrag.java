
package com.example.medico;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.medico.Adapter.BlogRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;


/**
 * A simple {@link Fragment} subclass.
 */
public class homeFrag extends Fragment {

    private RecyclerView blogRecyclerView;
    private List<UploadPosts> bloglist;
    private BlogRecyclerAdapter blogRecyclerAdapter;
    private FirebaseAuth mAuth;

    public homeFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate((R.layout.fragment_home), container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        ((HomeActivity) getActivity()).setActionBarTitle("Post");
        mAuth=FirebaseAuth.getInstance();
        blogRecyclerView = view.findViewById(R.id.blogRecyclerView);
        blogRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        blogRecyclerView.setHasFixedSize(true);

        if(mAuth.getCurrentUser()!=null) {

            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            //Query firstQuery = firebaseDatabase.getReference("Posts").orderByChild("timeStamp");
            DatabaseReference databaseReference = firebaseDatabase.getReference("Posts");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    bloglist = new ArrayList<>();
                    for (DataSnapshot postsnap : dataSnapshot.getChildren()) {

                        String blogPostId = postsnap.getKey();
                        UploadPosts blogPost = postsnap.getValue(UploadPosts.class).withId(blogPostId);
                        bloglist.add(blogPost);
                    }
                    blogRecyclerAdapter = new BlogRecyclerAdapter(getActivity(), bloglist);
                    blogRecyclerView.setAdapter(blogRecyclerAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}

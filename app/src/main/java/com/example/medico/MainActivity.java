package com.example.medico;

import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Handler h= new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i= new Intent(MainActivity.this,SignUp.class);
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser user= mAuth.getCurrentUser();
                if(user!=null)
                {
                    startActivity(new Intent(MainActivity.this,HomeActivity.class));
                    finish();
                }
                else {
                    startActivity(i);
                }
            }
        },3000);

    }
}

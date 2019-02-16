package com.example.medico;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
                startActivity(new Intent(MainActivity.this,SignUp.class));
               /* if(user!=null)
                {
                    finish();
                    startActivity(new Intent(MainActivity.this,HomeActivity.class));
                }
                else {
                    startActivity(i);
                }*/
            }
        },3000);

    }
}

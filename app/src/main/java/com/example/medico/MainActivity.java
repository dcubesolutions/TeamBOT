package com.example.medico;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Handler h= new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i= new Intent(MainActivity.this,SignUp.class);
                startActivity(i);
            }
        },3000);
    }
}

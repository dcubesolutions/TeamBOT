package com.example.medico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class PreliminariesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preliminaries);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(PreliminariesActivity.this,HomeActivity.class));

    }
}

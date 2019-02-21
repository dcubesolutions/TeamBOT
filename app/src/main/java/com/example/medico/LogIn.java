package com.example.medico;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogIn extends AppCompatActivity {
    Button BtnSiSignIn;
    EditText SiEmail, SiPassword;
    TextView TvRegister;
    TextView forgotpass;
    ProgressBar progressBar2;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        BtnSiSignIn = (Button) findViewById(R.id.BtnSiSignIn);
        SiEmail = (EditText) findViewById(R.id.SiEmail);
        SiPassword = (EditText) findViewById(R.id.SiPassword);
        TvRegister= (TextView) findViewById(R.id.TvRegister);
        forgotpass = (TextView) findViewById(R.id.forgotpass);
        progressBar2=(ProgressBar)findViewById(R.id.progressBar2);
        mAuth = FirebaseAuth.getInstance();

        BtnSiSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SiEmail.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Email Required..!!", Toast.LENGTH_SHORT).show();
                    SiEmail.setError("Email Required");
                    return;
                } else if (!Patterns.EMAIL_ADDRESS.matcher(SiEmail.getText().toString().trim()).matches()) {
                    Toast.makeText(getApplicationContext(), "Email Invaild..!!", Toast.LENGTH_SHORT).show();
                    SiEmail.setError("Email Invalid");
                    return;
                } else if (SiPassword.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Password Invalid..!!", Toast.LENGTH_SHORT).show();
                    SiPassword.setError("Password Invalid");
                    return;
                } else {
                   /* ArrayList<HashMap<String, String>> ARRUSER = new ArrayList<>();
                    ARRUSER = new DBHandler(getApplicationContext()).UserDataReadFunction
                            ("Select * from UserData Where UEmail='" + SiEmail.getText().toString().trim() + "'" +
                                    "and UPassword='" + SiPassword.getText().toString().trim() + "'");
                    if (ARRUSER.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Invalid Username or Password :(", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Logged In :)", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), About_Context_SingnUp_SignIn.class));

                    }
                    */
                    String email=SiEmail.getText().toString().trim();
                    String password=SiPassword.getText().toString().trim();
                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                BtnSiSignIn.setEnabled(false);
                                Toast.makeText(LogIn.this, "Logged In :)",
                                        Toast.LENGTH_SHORT).show();
                                progressBar2.setVisibility(View.VISIBLE);
                                startActivity(new Intent(LogIn.this,HomeActivity.class));
                            }
                            else
                            {
                                Toast.makeText(LogIn.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        TvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LogIn.this,SignUp.class));
            }
        });
        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LogIn.this,ForgotPassword.class));
            }
        });

    }
}
package com.example.medico;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ProgressBar;

import java.util.Locale;


import com.example.medico.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {
    Button BtnSuSignUp;
    EditText FName, LName,ContactNo, Password, Email, CPassword;
    TextView TvLogin, SignUp;
    Spinner category,language;
    boolean twice;
    Spinner language,category;
    String spinnerValue;
    int cat = 0;
    Locale myLocale;
    String currentLanguage, currentLang;
    final String TAG=getClass().getName();
    private FirebaseAuth mAuth;
    private String Degree, ClinicNo;
    //private FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseUser;
    ProgressBar progressBar2;
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        BtnSuSignUp = (Button) findViewById(R.id.BtnSuSignUp);
        FName = (EditText) findViewById(R.id.FName);
        LName = (EditText) findViewById(R.id.LName);
        CPassword = (EditText) findViewById(R.id.CPassword);
        ContactNo = (EditText) findViewById(R.id.ContactNo);
        Password = (EditText) findViewById(R.id.Password);
        Email = (EditText) findViewById(R.id.Email);
        TvLogin= (TextView) findViewById(R.id.TvLogin);
        mAuth = FirebaseAuth.getInstance();
        category=(Spinner)findViewById(R.id.category);
        language=(Spinner)findViewById(R.id.language);

        ArrayAdapter<String> newadapter = new ArrayAdapter<String>(
                SignUp.this, R.layout.spinner_layout_test, getResources().getStringArray(R.array.names)
        );
        category.setAdapter(newadapter);

        ArrayAdapter<String> newadapter2 = new ArrayAdapter<String>(
                SignUp.this, R.layout.spinner_layout_test, getResources().getStringArray(R.array.lang)
        );
        language.setAdapter(newadapter2);
        databaseUser = FirebaseDatabase.getInstance().getReference("user_data");
        ArrayAdapter<String> newadapter2 = new ArrayAdapter<String>(
                SignUp.this, R.layout.spinner_layout_test, getResources().getStringArray(R.array.category));
        category.setAdapter(newadapter2);

       // databaseUser = FirebaseDatabase.getInstance().getReference("user_data");


        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                spinnerValue = adapterView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        BtnSuSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    dialog=new ProgressDialog(SignUp.this);
                    dialog.setMessage("Loading");
                    dialog.show();
                if (FName.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "FirstName Required..!!", Toast.LENGTH_SHORT).show();
                    FName.setError("FirstName Required");
                    dialog.dismiss();
                    return;
                } else if (LName.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "LastName Required..!!", Toast.LENGTH_SHORT).show();
                    LName.setError("LastName Required");
                    dialog.dismiss();
                    return;
                }
                else if (ContactNo.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Contact Required..!!", Toast.LENGTH_SHORT).show();
                    ContactNo.setError("Contact Required");
                    dialog.dismiss();

                    return;
                } /*else if (!isContactNoValid(ContactNo.getText().toString().trim())) {
                    Toast.makeText(getApplicationContext(), "ContactNo invalid..!!", Toast.LENGTH_SHORT).show();
                    ContactNo.setError("Contact Invalid");
                    dialog.dismiss();
                    return;
                } else if (Email.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Email Required..!!", Toast.LENGTH_SHORT).show();
                    progressBar2.setVisibility(View.INVISIBLE);
                    Email.setError("Email Required");
                    dialog.dismiss();
                    return;
                } else if (!Patterns.EMAIL_ADDRESS.matcher(Email.getText().toString().trim()).matches()) {
                    Toast.makeText(getApplicationContext(), "Email Invaild..!!", Toast.LENGTH_SHORT).show();
                    progressBar2.setVisibility(View.INVISIBLE);
                    Email.setError("Email Invalid");
                    dialog.dismiss();
                    return;
                } else if (Password.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Password Required..!!", Toast.LENGTH_SHORT).show();
                    Password.setError("Password Required");
                    dialog.dismiss();
                    return;
                }
                else if (CPassword.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Password Required..!!", Toast.LENGTH_SHORT).show();
                    Password.setError("Password Required");
                    dialog.dismiss();
                    return;
                }
                else if (!(Password.getText().toString()).equals(CPassword.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Password not match", Toast.LENGTH_SHORT).show();
                    CPassword.setError("Password not match");
                    dialog.dismiss();
                    return;
                }
                else{

//                    progressBar2.setVisibility(View.VISIBLE);
                   final String email=Email.getText().toString().trim();
                   final String password=Password.getText().toString().trim();
                   final String fName= FName.getText().toString().trim();
                   final String lName = LName.getText().toString().trim();
                   final String mid = ContactNo.getText().toString();
                   final String status="offline";
                   final String imageURL="default";
                    mAuth = FirebaseAuth.getInstance();

                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        //Sign in success, update UI with the signed-in user's information
                                        BtnSuSignUp.setEnabled(false);
                                        Log.d(String.valueOf(SignUp.this), "createUserWithEmail:success");
                                                String id= mAuth.getCurrentUser().getUid();
                                                databaseUser = FirebaseDatabase.getInstance().getReference("user_data");
                                                if(spinnerValue.equals("Patient")){

                                                        DatabaseReference mRef= databaseUser.child(id);
                                                        User user_db = new User(fName,lName,mid,email,id,"default","offline",Degree, ClinicNo);
                                                        mRef.setValue(user_db);
                                                        Toast.makeText(SignUp.this, "Patient Successfully Registered", Toast.LENGTH_SHORT).show();
                                                        Intent intent;
                                                        intent = new Intent(SignUp.this,verifyotp.class);
                                                        intent.putExtra("phoneNumber",mid);
                                                         startActivity(intent);
                                                }
                                                else if(spinnerValue.equals("Doctor")){
                                                    Toast.makeText(SignUp.this, "Doctor Successfully Registered", Toast.LENGTH_SHORT).show();
                                                    Intent intent;
                                                    intent = new Intent(SignUp.this,doctorDetails.class);
                                                    intent.putExtra("phoneNumber",mid);
                                                    intent.putExtra("fName",fName);
                                                    intent.putExtra("lname",lName);
                                                    intent.putExtra("email",email);
                                                    intent.putExtra("id",id);
                                                    intent.putExtra("imageURL",imageURL);
                                                    intent.putExtra("status",status);
                                                    startActivity(intent);
                                                }
                                                else if(spinnerValue.equals("Select Category")){
                                                    Toast.makeText(SignUp.this, "Please Select a Category!", Toast.LENGTH_SHORT).show();
                                                }
                               //         insert_db(fName,lName,mid,email);
                                      /*  Intent intent=new Intent(SignUp.this,verifyotp.class);
                                        intent.putExtra("phoneNumber",mid);
                                        startActivity(intent);*/
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(String.valueOf(SignUp.this), "createUserWithEmail:failure");
                                        Toast.makeText(SignUp.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                        //updateUI(null);
                                    }

                                    // ...
                                }


                            });

                }
            }
        });
        TvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUp.this,LogIn.class));
            }
        });

       /* BtnSuCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CleanEditText();
            }
        });*/
    }

        language.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        setLocale("en");
                        break;
                    case 2:
                        setLocale("hi");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    public void setLocale(String localeName) {
        if (!localeName.equals(currentLanguage)) {
            myLocale = new Locale(localeName);
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = myLocale;
            res.updateConfiguration(conf, dm);
            Intent refresh = new Intent(this, SignUp.class);
            refresh.putExtra(currentLang, localeName);
            startActivity(refresh);
        } else {
            Toast.makeText(SignUp.this, "Language already selected!", Toast.LENGTH_SHORT).show();
        }
    }
    //String user= mAuth.getCurrentUser().getUid();
    //String user= mAuth.getCurrentUser().getUid();
  /*  public void insert_db(String fName,String lName,String mid,String email){
        String id= mAuth.getCurrentUser().getUid();
        User user_db = new User(fName,lName,mid,email,id,"default","offline");
        databaseUser.child(id).setValue(user_db);
    }*/

  /*  public static boolean isContactNoValid(String ConnNo)
    {
        String regExpn="\\d{12}";//regEx for contact no.

        CharSequence inputStr=ConnNo;//to convert string into character sequence.
        Pattern pattern = Pattern.compile(regExpn,Pattern.CASE_INSENSITIVE);
        Matcher matcher= pattern.matcher(inputStr);
        if(matcher.matches())

            return true;

        else
            return false;
    }*/
    public void CleanEditText()
    {
        FName.setText("");
        LName.setText("");
        ContactNo.setText("");
        Email.setText("");
        Password.setText("");
    }
   /* @Override
    protected void onStart(){

        SignUp.super.onStart();
        databaseUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()){
//                    System.out.println(ds);
//                    System.out.println(ds.getValue());

                    ds.getValue();
                    try {
                        JSONObject reader = new JSONObject(ds.getValue().toString());
                        System.out.println(reader.getString("mid"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }*/

    @Override
    public void onBackPressed() {
        Log.d(TAG,"click");
        if(twice==true){
            Intent i=new Intent(Intent.ACTION_MAIN);
            i.addCategory(Intent.CATEGORY_HOME);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
            System.exit(0);
        }
        //super.onBackPressed();
        twice=true;
        Log.d(TAG,"twice:"+twice);
        Toast.makeText(SignUp.this,"Press Back Again to Exit.",Toast.LENGTH_SHORT).show();
        Handler h= new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                twice=false;
                Log.d(TAG,"twice:"+ twice);
            }
        },3000);
    }

}
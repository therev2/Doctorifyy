package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    TextView are_doc;

//    Button login_btn;

    TextView signup;

    EditText loginEmail, loginPassword;
    Button loginButton;

//    CheckBox rememberMeCheckbox;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

//        linking all activities

        are_doc = findViewById(R.id.are_you_doc);
        are_doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DocLoginActivity.class);
                startActivity(intent);
            }
        });

        signup = findViewById(R.id.O);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, patient_register.class);
                startActivity(intent);
            }
        });




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;


        });


        loginEmail = findViewById(R.id.loginemail);
        loginPassword = findViewById(R.id.loginpass);
        loginButton = findViewById(R.id.klop);
//        rememberMeCheckbox = findViewById(R.id.rememberbox);

        // Check if Remember Me is selected
//        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
//        boolean rememberMe = prefs.getBoolean("rememberMe", false);
//        if (rememberMe) {
//            String savedEmail = prefs.getString("email", "");
//            String savedPassword = prefs.getString("password", "");
//            if (!savedEmail.isEmpty() && !savedPassword.isEmpty()) {
//                loginEmail.setText(savedEmail);
//                loginPassword.setText(savedPassword);
//                // Automatically attempt login
//                attemptLogin(savedEmail, savedPassword);
//            }
//        }


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateEmail() | !validatePassword()){

                } else {
                    checkEmail();
                }
            }
        });



    }


    boolean isAlphanumeric(final int codePoint) {
        return (codePoint >= 64 && codePoint <= 90) ||
                (codePoint >= 97 && codePoint <= 122) ||
                (codePoint >= 32 && codePoint <= 57);
    }


    public Boolean validateEmail() {
        String val = loginEmail.getText().toString();
        if (val.isEmpty()){
            loginEmail.setError("Email cannot be empty");
            return false;
        }else {
            loginEmail.setError(null);
            boolean result = true;
            for (int i = 0; i < val.length(); i++) {
                int codePoint = val.codePointAt(i);
                if (!isAlphanumeric(codePoint)) {
                    result = false;
                    break;
                }
            }
            if (result){
                return true;
            } else {
                loginEmail.setError("Email can only be alphanumberic");
                return false;
            }
        }



    }

    public Boolean validatePassword() {
        String val = loginPassword.getText().toString();
        if (val.isEmpty()){
            loginPassword.setError("Password cannot be empty");
            return false;
        }else {
            loginPassword.setError(null);
            if (val.isEmpty()){
                loginPassword.setError("Password cannot be empty");
                return false;
            }else {
                loginPassword.setError(null);
                boolean result = true;
                for (int i = 0; i < val.length(); i++) {
                    int codePoint = val.codePointAt(i);
                    if (!isAlphanumeric(codePoint)) {
                        result = false;
                        break;
                    }
                }
                if (result){
                    return true;
                } else {
                    loginPassword.setError("Password can only be alphanumberic");
                    return false;
                }
            }


        }

    }

    public void checkEmail(){
        String userUseremail = loginEmail.getText().toString().trim();
        String userPassword = loginPassword.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("patient");
        Query checkUserDatabase = reference.orderByChild("email").equalTo(userUseremail);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()){
                    loginEmail.setError(null);
                    String passwordFromDB = snapshot.child(userUseremail.replace(".",",")).child("password").getValue(String.class);

                    if(passwordFromDB.equals(userPassword)){
                        loginEmail.setError(null);
                        Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, HomePage.class);
                        startActivity(intent);
                    }else {
                        loginPassword.setError("invalid Credentials");
                        loginPassword.requestFocus();
                    }
                }else {
                    loginEmail.setError("User does not exist");
                    loginEmail.requestFocus();

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

//    public void attemptLogin(String saved_email,String saved_password){
//        String userUseremail = saved_email.toString().trim();
//        String userPassword = saved_password.toString().trim();
//
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("patient");
//        Query checkUserDatabase = reference.orderByChild("email").equalTo(userUseremail);
//
//        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                if (snapshot.exists()){
//                    loginEmail.setError(null);
//                    String passwordFromDB = snapshot.child(userUseremail.replace(".",",")).child("password").getValue(String.class);
//
//                    if(passwordFromDB.equals(userPassword)){
//                        loginEmail.setError(null);
//                        Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(MainActivity.this, HomePage.class);
//                        startActivity(intent);
//                    }else {
//                        loginPassword.setError("invalid Credentials");
//                        loginPassword.requestFocus();
//                    }
//                }else {
//                    loginEmail.setError("User does not exist");
//                    loginEmail.requestFocus();
//
//                }
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//    }
//
//



}
package com.example.r3cykl;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.support.design.widget.Snackbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    EditText userEmail;
    EditText userPassword;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        findViewById(R.id.btn_register).setOnClickListener(this);
        findViewById(R.id.text_login).setOnClickListener(this);

        userEmail = findViewById(R.id.tb_email);
        userPassword = findViewById(R.id.tb_password);
        progressBar = findViewById(R.id.progressBar);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

    }

    public void goToLoginPage() {
        Intent intent = new Intent(RegisterActivity.this, scannerActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        return;


    }

    private void registerUser(){
        String email = userEmail.getText().toString().trim();
        String password = userPassword.getText().toString().trim();

        if(email.isEmpty()){
            Toast toast = Toast.makeText(getApplicationContext(), "Email required", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP,0,500);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast toast = Toast.makeText(getApplicationContext(), "Enter a valid Email", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP,0,500);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        if(password.isEmpty()){
            Toast toast = Toast.makeText(getApplicationContext(), "Enter your password", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP,0,500);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        if(password.length()<6){
            Toast toast = Toast.makeText(getApplicationContext(), "Password is too short", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP,0,500);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.show();
            return;
        }

            progressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progressBar.setVisibility(View.GONE);
                        Toast toast = Toast.makeText(getApplicationContext(), "Success! You're now a friend of the universe", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP,0,500);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.show();
                        Intent intent = new Intent(RegisterActivity.this, scannerActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        return;

                    }
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast toast = Toast.makeText(getApplicationContext(), "Looks like you have an account already. Please log in", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP,0,500);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.show();
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        toast.show();
                        return;
                    }
                    else{
                        Toast toast = Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP,0,500);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.show();
                        return;
                    }
                }
            });

        }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                registerUser();
                break;
            case R.id.text_login:
                goToLoginPage();
                break;
        }

//    public void registerUser(View view) {
//    }
    }
}

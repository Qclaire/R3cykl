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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText userEmail;
    EditText userPassword;
    ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.text_register_here).setOnClickListener(this);
        findViewById(R.id.btn_login).setOnClickListener(this);

        userEmail = findViewById(R.id.tb_email);
        userPassword = findViewById(R.id.tb_password);
        progressBar = findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.text_register_here:
                goToRegisterPage();
                break;
            case R.id.btn_login:
                userLogin();
                break;
        }
    }

    private void goToRegisterPage() {

        startActivity(new Intent(this, RegisterActivity.class));
    }

    private void userLogin() {
        String email = userEmail.getText().toString().trim();
        String password = userPassword.getText().toString().trim();
        if(email.isEmpty()) {
            Toast toast = Toast.makeText(getApplicationContext(), "Email required", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP,0,500);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        if(password.isEmpty()){
            Toast toast = Toast.makeText(getApplicationContext(), "Enter your Password", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP,0,500);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        if(password.length()<6){
            Toast toast = Toast.makeText(getApplicationContext(), "Password too short", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP,0,500);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast toast = Toast.makeText(getApplicationContext(), "Invalid Email. Enter a valid one", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP,0,500);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    Intent intent = new Intent(MainActivity.this, scannerActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    return;
                }else {
                    Toast toast = Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP,0,500);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }
            }
        });



    }
}

package com.example.r3cykl;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class scannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler, View.OnClickListener {

    private ZXingScannerView zXingScannerView;
    FloatingActionButton newScan;
    Button result_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        findViewById(R.id.btn_scan).setOnClickListener(this);
        result_btn = findViewById(R.id.btn_result);
        zXingScannerView = new ZXingScannerView(getApplicationContext());


    }

    public void scan() {
        setContentView(zXingScannerView);
        zXingScannerView.setResultHandler(this);
        zXingScannerView.startCamera();

    }
    @Override
    protected  void onPause(){
        super.onPause();
        zXingScannerView.stopCamera();

    }


    @Override
    public void handleResult(Result result) {
        Toast toast = (Toast) Toast.makeText(getApplicationContext(), result.getText(),  Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP, 0, 500);
        toast.show();
        result_btn.setText(result.getText());
        zXingScannerView.stopCamera();
        startActivity(new Intent(this, scannerActivity.class));
        result_btn.setText(result.getText());


    }


    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.btn_scan){
            scan();
            return;
        }

    }
}

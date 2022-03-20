package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import java.util.logging.LogRecord;

public class Start_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_);

        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                Intent i = new Intent(Start_Activity.this, signin_activity.class);
                startActivity(i);
                finish(); } }, 1000);
    }
    }

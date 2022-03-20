package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class QR_Activity extends AppCompatActivity {
    Button done , change ;
    String x = "" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_r_);

        done = findViewById(R.id.done) ;
        change = findViewById(R.id.change) ;

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(QR_Activity.this , Addproduct_Activity.class) ;
                i.putExtra("qr" , x) ;
                startActivity(i);

            }
        });
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                IntentIntegrator intentIntegrator = new IntentIntegrator(QR_Activity.this) ;
                intentIntegrator.setPrompt("For flash use volume up key") ;
                intentIntegrator.setBeepEnabled(true) ;
                intentIntegrator.setOrientationLocked(true) ;
                intentIntegrator.setCaptureActivity(Capture.class) ;
                intentIntegrator.initiateScan();
            }
        });

        IntentIntegrator intentIntegrator = new IntentIntegrator(QR_Activity.this) ;
        intentIntegrator.setPrompt("For flash use volume up key") ;
        intentIntegrator.setBeepEnabled(true) ;
        intentIntegrator.setOrientationLocked(true) ;
        intentIntegrator.setCaptureActivity(Capture.class) ;
        intentIntegrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode , resultCode , data) ;
        if (intentResult.getContents() != null )
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(QR_Activity.this) ;
            builder.setTitle("Result") ;
            builder.setMessage(intentResult.getContents()) ;

            x = String.valueOf(intentResult.getContents());

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
        } else {
            Toast.makeText(getApplicationContext(), "OOPS... you did not scan anything", Toast.LENGTH_SHORT).show();
        }
    }
}
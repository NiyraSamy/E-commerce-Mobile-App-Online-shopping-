package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class searchQR_Activity extends AppCompatActivity {
    Button search ;
    String x = " " ;
    DBHelper dbh ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_q_r_);
        dbh = new DBHelper(this) ;
        search = findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = dbh.get_qr(x);
                String name = cursor.getString(1);
                Intent intent = new Intent(searchQR_Activity.this , ViewProduct_Activity.class) ;
                intent.putExtra("item" , name ) ;
                startActivity(intent);
                //Toast.makeText(searchQR_Activity.this , name , Toast.LENGTH_SHORT).show();
            }
        });

        IntentIntegrator intentIntegrator = new IntentIntegrator(searchQR_Activity.this) ;
        intentIntegrator.setPrompt("For flash use volume up key") ;
        intentIntegrator.setBeepEnabled(true) ;
        intentIntegrator.setOrientationLocked(true) ;
        intentIntegrator.setCaptureActivity(Capture.class) ;
        intentIntegrator.initiateScan();
    }

    /**
     * Dispatch incoming result to the correct fragment.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult intentResult = IntentIntegrator.parseActivityResult( requestCode , resultCode , data ) ;
        if (intentResult.getContents() != null ) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this) ;
            builder.setTitle("result") ;
            builder.setMessage( intentResult.getContents() ) ;
            x = String.valueOf(intentResult.getContents());
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show() ;
        } else {
            Toast.makeText(getApplicationContext() , "OOPS... you did not scan anything" , Toast.LENGTH_LONG).show();
        }
    }
}
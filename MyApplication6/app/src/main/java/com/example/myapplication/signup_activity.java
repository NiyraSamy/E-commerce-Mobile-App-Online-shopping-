package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class signup_activity extends AppCompatActivity {

    Button signup ;
    EditText username , email , password , B_day , jop ;
    Spinner gender;
    Database db;
    DatePickerDialog.OnDateSetListener d ;
    private static final String TAG = "Home_Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_activity);

        username=findViewById(R.id.username_e);
        email=findViewById(R.id.email_edit);
        password=findViewById(R.id.password_e);
        jop=findViewById(R.id.job_edit);
        gender=findViewById(R.id.spinner);
        db=new Database(this);

        B_day=findViewById(R.id.day_edit);
        B_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                final int Year = c.get(c.YEAR);
                final int Month = c.get(c.MONTH);
                final int Day = c.get(c.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(signup_activity.this , android.R.style.Theme_Holo_Light_Dialog_NoActionBar , d , Year , Month , Day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        d = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1 ;
                Log.d(TAG , "onDateSet: mm/dd/yyy: " + month + "/" + dayOfMonth + "/" + year );
                String date = month + "/" + dayOfMonth + "/" + year ;
                B_day.setText(date);
            }
        };

        signup  = findViewById(R.id.signup_button) ;
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i = new Intent(signup_activity.this , signin_activity.class) ;
                //startActivity(i);
                signUp();
            }
        });
    }
    protected void signUp() {
        String name = username.getText().toString().trim();
        String mail = email.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String gen = gender.getSelectedItem().toString();
        String birthdate = TAG ;
        String joptitle=jop.getText().toString();

        if (name.equals("") || mail.equals("") || pass.equals("")|| gen.equals("")|| birthdate.equals("")|| joptitle.equals(""))
            Toast.makeText(this, "Some fields not entered", Toast.LENGTH_SHORT).show();
        else {
            Customer_Class c=new Customer_Class(name,mail,pass,gen,joptitle,birthdate);
            db.AddCustomer(c);
            Toast.makeText(this, "Successfully registered ", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(signup_activity.this, signin_activity.class);
            intent.putExtra("email" , mail) ;
            startActivity(intent);
            finish();
        }

    }
}
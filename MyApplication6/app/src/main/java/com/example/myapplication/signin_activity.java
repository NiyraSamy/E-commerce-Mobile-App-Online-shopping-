package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class signin_activity extends AppCompatActivity {

    Button signin ;
    TextView signup ;
    EditText email_edit , password_edit;
    Database db;
    CheckBox remember_chek;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    boolean login_state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_activity);

        email_edit=findViewById(R.id.username_edit);
        password_edit=findViewById(R.id.password_edit);
        remember_chek=findViewById(R.id.remember_chekbox);
        sharedPreferences=getSharedPreferences("shared_file",MODE_PRIVATE);
        db=new Database(this);

        signup = findViewById(R.id.signup_txt) ;
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(signin_activity.this , signup_activity.class) ;
                startActivity(ii);
            }
        });

        signin = findViewById(R.id.signin_button) ;
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i = new Intent(signin_activity.this , Home_Activity.class) ;
                //startActivity(i);
                login();
            }
        });
        checkLogin();
    }

    protected void checkLogin(){
        login_state= sharedPreferences.getBoolean("login",false);
        if(login_state){
            Intent i=new Intent(signin_activity.this, Home_Activity.class);
            startActivity(i);
            finish(); }
    }
    protected void login() {
        String email = email_edit.getText().toString();
        String password = password_edit.getText().toString();
        Cursor cursor = db.Login(email, password);

        if (email.equals("admin") && password.equals("admin")) {
            Intent intent = new Intent(signin_activity.this, Addproduct_Activity.class);
            startActivity(intent);
            finish();
        }
        else {
            if (cursor.getCount() <= 0)
                Toast.makeText(this, "Please Check username and password", Toast.LENGTH_SHORT).show();
            else {

                if (remember_chek.isChecked())
                    keepLogin(email, password);

                Toast.makeText(this, "Successfully login", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(signin_activity.this, Home_Activity.class);
                intent.putExtra("publicMail" , email ) ;
                startActivity(intent);
                finish();
            }}}
    protected void keepLogin (String email, String password){
        editor = sharedPreferences.edit();
        editor.putString("email", email);
        editor.putString("password", password);
        editor.putBoolean("login", true);
        editor.apply();
    }
}
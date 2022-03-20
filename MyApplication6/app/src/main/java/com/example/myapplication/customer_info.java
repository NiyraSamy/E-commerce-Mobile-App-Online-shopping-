package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

public class customer_info extends AppCompatActivity implements LocationListener {
    EditText location , mail_txt , fn , ln , addr , city , phone;
    TextView total ;
    Button btn ;
    LocationManager locationManager ;
    String l = "location detected" ;

    String subject = "Purchase Order" ;
    String message = "Hi thanks for your purchase from our site . we will notify you when it ships . your total price is " ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_info);

        location = findViewById(R.id.location);
        mail_txt = findViewById(R.id.email);
        fn = findViewById(R.id.fn);
        ln = findViewById(R.id.ln);
        addr = findViewById(R.id.addr);
        city = findViewById(R.id.city);
        phone = findViewById(R.id.phone);

        total = findViewById(R.id.total);
        btn = findViewById(R.id.btn_checkout) ;

        Intent intent = getIntent() ;
        String price = intent.getStringExtra("total");
        total.setText(price);

        if (ContextCompat.checkSelfPermission(customer_info.this , Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(customer_info.this , new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            } , 100 );
        }

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getLocation();
                location.setText(l);
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ( (fn.getText().toString()).equals("") && (ln.getText().toString()).equals("") && (addr.getText().toString()).equals("") && (city.getText().toString()).equals("") && (phone.getText().toString()).equals("") )
                {
                    Toast.makeText(customer_info.this, "Plese complete all data !!", Toast.LENGTH_SHORT).show();
                } else if ( (fn.getText().toString()).equals("") || (ln.getText().toString()).equals("") || (addr.getText().toString()).equals("") || (city.getText().toString()).equals("") || (phone.getText().toString()).equals("") )
                {
                    Toast.makeText(customer_info.this, "Plese complete all data !!", Toast.LENGTH_SHORT).show();

                } else
                {
                    sendMail(price);
                }
            }
        });

    }

    private void sendMail( String total) {
        String email = mail_txt.getText().toString() ;
        String m = message + total + "L.E" ;
        JavaMailAPI javaMailAPI = new JavaMailAPI(this , email , subject , m ) ;
        javaMailAPI.execute() ;
    }


    @SuppressLint("MissingPermission")
    private void getLocation() {
        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE) ;
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER , 500 , 5 , customer_info.this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        Toast.makeText(this , "" + location.getLatitude() + "," + location.getLongitude() , Toast.LENGTH_SHORT).show();
        try {
            Geocoder geocoder = new Geocoder(customer_info.this , Locale.getDefault() ) ;
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude() , location.getLongitude() , 1 ) ;
            String address = addresses.get(0).getAddressLine(0) ;
            l = address ;
            Toast.makeText(customer_info.this , "ADDRESS :"+" " + address , Toast.LENGTH_LONG).show();


        } catch ( Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }
}
package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.zxing.integration.android.IntentIntegrator;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class Addproduct_Activity extends AppCompatActivity {

    ImageView product_image , add_qr_image ;
    EditText product_name, product_price, product_quantity , qr_code;
    Spinner spinner;
    ArrayAdapter adapter;
    Button Add_btn;
    TextView  show ;
    Database db;
    DBHelper dbh ;
    final static int GALLERY_REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addproduct);

        qr_code = findViewById(R.id.qr_edittext) ;

        product_image = findViewById(R.id.product_image);
        add_qr_image = findViewById(R.id.add_qr_image);
        product_name = findViewById(R.id.product_name);
        product_price = findViewById(R.id.product_price);
        product_quantity = findViewById(R.id.product_quantity);
        spinner = findViewById(R.id.category);
        Add_btn = findViewById(R.id.Add_Product);
        show = findViewById(R.id.show) ;
        db = new Database(this);
        dbh = new DBHelper(this) ;

        Intent intent = getIntent() ;
        String qr = intent.getStringExtra("qr");
        qr_code.setText(qr);

        add_qr_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Addproduct_Activity.this , QR_Activity.class) ;
                startActivity(intent);
            }
        });

        Add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadProduct();
            }
        });

        product_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Addproduct_Activity.this , MainActivity.class);
                startActivity(i);
            }
        });
    }

    protected static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
    protected void uploadProduct(){

        String name=product_name.getText().toString().trim();
        String price=product_price.getText().toString().trim();
        String quan=product_quantity.getText().toString().trim();
        String catid_str = spinner.getSelectedItem().toString() ;
        String qr_str = qr_code.getText().toString().trim() ;

        int catid = 0 ;
        if(catid_str.equals("Shirts"))
        {
            catid = 1 ;
        } else if(catid_str.equals("Jackets"))
        {
            catid = 2 ;
        }else if(catid_str.equals("Pants"))
        {
            catid = 3 ;
        }else if(catid_str.equals("Blouses"))
        {
            catid = 4 ;
        }
        byte [] image=imageViewToByte(product_image);

        if(!name.equals("")||!price.equals("")||!quan.equals("")||!qr_str.equals(""))
        {
            Product_Class p = new Product_Class(Integer.parseInt(quan), catid,name,image,Double.parseDouble(price));
            db.AddProduct(p);
            boolean check = dbh.insert_qr(name , qr_str) ;
            if (check) { Toast.makeText(this, "done", Toast.LENGTH_SHORT).show(); }
            else { Toast.makeText(this, "not", Toast.LENGTH_SHORT).show(); }

            product_image.setImageResource(R.drawable.download);
            product_name.setText("");
            product_price.setText("");
            product_quantity.setText("");
            qr_code.setText("");
            Toast.makeText(this, "product added", Toast.LENGTH_SHORT).show();


        }
        else
        {
            Toast.makeText(this, "Check data again", Toast.LENGTH_SHORT).show();
        }
    }
    protected void chooseImage() {
        //Create an Intent with action as ACTION_PICK
        Intent intent = new Intent(Intent.ACTION_PICK);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        // Launching the Intent
        startActivityForResult(intent, GALLERY_REQUEST_CODE);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {

            Uri uri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                product_image.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
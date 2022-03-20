package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewProduct_Activity extends AppCompatActivity {

    ImageView imageView , cart_image ;
    TextView name , price , publicmail ;
    Button add ;
    Database database ;
    private SearchView searchView ;

    DrawerLayout drawerLayout ;
    List<String> listGroup;
    ExpandableListView expandableListView ;
    HashMap<String , List<String>> listItem ;
    MainAdapter adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product_);

        publicmail = findViewById(R.id.public_email) ;
        Intent intent = getIntent() ;
        String mail = intent.getStringExtra("publicMail");
        publicmail.setText(mail);
        String s = intent.getStringExtra("item");

        imageView = findViewById(R.id.product_image_v);
        cart_image = findViewById(R.id.cart_image);
        name = findViewById(R.id.product_name_v);
        price = findViewById(R.id.product_price_v);
        add = findViewById(R.id.Add_Product_cart);
        database  = new Database(getApplicationContext());


        Cursor cursor = database.get(s);
        if (cursor.getCount() == 0 )
        {
            Toast.makeText(ViewProduct_Activity.this , "NO DATA FOUND !!" , Toast.LENGTH_LONG).show();
        }
        else {
            name.setText(cursor.getString(1));
            price.setText(cursor.getString(3));

            byte[] image_byte1 = cursor.getBlob(2);
            Bitmap bmp = BitmapFactory.decodeByteArray(image_byte1, 0, image_byte1.length);
            imageView.setImageBitmap(bmp);
        }

        drawerLayout = findViewById(R.id.drawer_layout);
        expandableListView = findViewById(R.id.expandable_listview);
        listGroup = new ArrayList<>();
        listItem = new HashMap<>();
        adapter = new MainAdapter(ViewProduct_Activity.this , listGroup , listItem );
        expandableListView.setAdapter(adapter);
        initListData();

        int id  = (int) expandableListView.getSelectedId();
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if(id == 0)
                {
                    Intent i = new Intent(ViewProduct_Activity.this , Category1_Activity.class);
                    startActivity(i);}
                if(id == 1)
                {
                    Intent i = new Intent(ViewProduct_Activity.this , Category2_Activity.class);
                    startActivity(i);
                }
                if(id == 2)
                {
                    Intent i = new Intent(ViewProduct_Activity.this , Category3_Activity.class);
                    startActivity(i);
                }
                if(id == 3)
                {
                    Intent i = new Intent(ViewProduct_Activity.this , Category4_Activity.class);
                    startActivity(i);
                }

                return false;
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewProduct_Activity.this , Cart_Activity.class);
                i.putExtra("item" , name.getText().toString() );
                startActivity(i);
            }
        });
        cart_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewProduct_Activity.this , out_cart.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_right , R.anim.slide_to_left);
            }
        });
    }

    public void ClickMenu(View view)
    {
        openDrawer(drawerLayout);
    }
    private static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }
    public void ClickLogo(View view)
    {
        closeDrawer(drawerLayout);
    }
    private static void closeDrawer(DrawerLayout drawerLayout) {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void ClickHome(View view)
    {
        redirectActivity(this , Home_Activity.class);
    }
    public  void ClickLogout(View view)
    {
        logout(this);
    }
    private static void logout(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout ?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.finishAffinity();
                System.exit(0);
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
    private static void redirectActivity(Activity activity , Class aclass) {
        Intent intent = new Intent(activity , aclass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }
    private void initListData() {
        listGroup.add(getString(R.string.group1));
        String[] array;

        List<String> list1 = new ArrayList<>();
        array = getResources().getStringArray(R.array.group1);
        for(String item : array)
        {
            list1.add(item);
        }
        listItem.put(listGroup.get(0),list1);
        adapter.notifyDataSetChanged();
    }
}
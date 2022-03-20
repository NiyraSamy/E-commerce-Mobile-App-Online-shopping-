package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Cart_Activity extends AppCompatActivity {
    Button btn , inc , dec ;
    TextView price , count_txt ;
    Database d_cart ;
    private ArrayList<ExampleItem1> mExampleList_cart ;
    private RecyclerView mRecyclerView_cart ;
    private ExampleAdapter1 mAdapter_cart ;
    private RecyclerView.LayoutManager mLayoutManager_cart ;
    DBHelper db ;
    int total = 0 ;
    ImageView close_image ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_);
        price = findViewById(R.id.price) ;
        close_image = findViewById(R.id.close_image) ;

        count_txt = findViewById(R.id.count) ;
        inc = findViewById(R.id.increase);
        dec = findViewById(R.id.decrease);


        close_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent() ;
        String s = intent.getStringExtra("item");

        d_cart = new Database(this);
        db = new DBHelper(this) ;
        Cursor sor =  db.getdata() ;

        boolean check = true ;
        if ( db.getdata().getCount() == 0 ) { db.insert(s) ; check = false ;}
        else
            {
                for (int i = 1 ; i <= sor.getCount() ; i++)
                {
                    Cursor cur = db.fetch(i) ;
                    String str = cur.getString(1) ;
                    if ( s.equals(str) ) {  check = false ;}
                }
            }
        if (check) { db.insert(s) ; }


        Cursor c = d_cart.getid(1);
        create(c.getBlob(2) ,"name" , "price");
        build();
        removeItem(0);

        Cursor r =  db.getdata() ;
        for (int i = 1 ; i <= r.getCount() ; i++)
        {
            Cursor rr = db.fetch(i) ;
            Cursor c_cart = d_cart.get(rr.getString(1));
            insertItem(0, c_cart.getBlob(2) , c_cart.getString(1), c_cart.getString(3));

            total = total + c_cart.getInt(3) ;
            price.setText(String.valueOf(total));
        }

        btn = findViewById(R.id.button_cart);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //db.clear();
                Intent i = new Intent(Cart_Activity.this ,customer_info.class);
                i.putExtra("total" , String.valueOf(total) ) ;
                startActivity(i);
            }
        });
    }

    public String getitem_name (int position  ) {
        String s = String.valueOf(mExampleList_cart.get(position).getmText1());
        return s ;
    }
    public String getitem_price (int position  ) {
        String s = String.valueOf(mExampleList_cart.get(position).getmText2());
        return s ;
    }
    public void create( byte[] image , String name ,  String price ) {
        mExampleList_cart = new ArrayList<>();
        mExampleList_cart.add(new ExampleItem1( image , name , price));
    }
    public void build(){
        mRecyclerView_cart = findViewById(R.id.recyclerView_cart);
        mRecyclerView_cart.setHasFixedSize(true);
        mLayoutManager_cart = new LinearLayoutManager(this);
        mAdapter_cart = new ExampleAdapter1(mExampleList_cart);
        mRecyclerView_cart.setLayoutManager(mLayoutManager_cart);
        mRecyclerView_cart.setAdapter(mAdapter_cart);

        mAdapter_cart.setOnItemClickListner(new ExampleAdapter1.OnItemClickListner() {
            @Override
            public void onItemClick(int position) {
            }

            @Override
            public void onDeleteClick(int position) {
                String name_rec = getitem_name(position) ;
                String price_rec = getitem_price(position) ;
                total = total - Integer.parseInt(price_rec) ;
                price.setText(String.valueOf(total));
                removeItem(position);
            }

            @Override
            public void onIncreaseClick(int position) {
                String price_rec = getitem_price(position) ;
                total = total + Integer.parseInt(price_rec) ;
                price.setText(String.valueOf(total));
            }

            @Override
            public void onDecreaseClick(int position) {
                String price_rec = getitem_price(position) ;
                if ( total == 0 ) { price.setText("0"); }
                else {
                    total = total - Integer.parseInt(price_rec) ;
                    price.setText(String.valueOf(total));
                }
            }
        });
    }
    public void insertItem ( int position , byte[] image , String name  , String price ) {

        mExampleList_cart.add(position , new ExampleItem1( image , name , price));
        mAdapter_cart.notifyItemInserted(position);
    }
    public void removeItem (int position) {
        mExampleList_cart.remove(position);
        mAdapter_cart.notifyItemRemoved(position);
    }
}
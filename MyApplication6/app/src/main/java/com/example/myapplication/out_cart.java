package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class out_cart extends AppCompatActivity {
    Button btn  ;
    TextView outprice  ;
    Database d_outcart ;
    private ArrayList<ExampleItem1> mExampleList_outcart ;
    private RecyclerView mRecyclerView_outcart ;
    private ExampleAdapter1 mAdapter_outcart ;
    private RecyclerView.LayoutManager mLayoutManager_outcart ;
    DBHelper outdb ;
    int total_out = 0 ;
    ImageView close_image_out ;
    ArrayList<Integer> arr  ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out_cart);

        outprice = findViewById(R.id.outprice) ;
        arr = new ArrayList<>() ;
        d_outcart = new Database(this);
        outdb = new DBHelper(this) ;

        Cursor c = d_outcart.getid(1) ;
        create(c.getBlob(2) ,"name" , "price");
        build();
        removeItem(0);


        Cursor r =  outdb.getdata() ;
        for (int i = 1 ; i <= r.getCount() ; i++)
        {
            Cursor rr = outdb.fetch(i) ;
            Cursor c_cart = d_outcart.get(rr.getString(1));
            insertItem(0, c_cart.getBlob(2) , c_cart.getString(1), c_cart.getString(3));
            total_out = total_out + c_cart.getInt(3) ;
            outprice.setText(String.valueOf(total_out));
        }

        btn = findViewById(R.id.button_out);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //outdb.clear();
                Intent i = new Intent(out_cart.this ,customer_info.class);
                i.putExtra("total" , outprice.toString()) ;
                startActivity(i);

            }
        });

        close_image_out = findViewById(R.id.close_image_out) ;

        close_image_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    public String getitem_name (int position  ) {
        String s = String.valueOf(mExampleList_outcart.get(position).getmText1());
        return s ;
    }
    public String getitem_price (int position  ) {
        String s = String.valueOf(mExampleList_outcart.get(position).getmText2());
        return s ;
    }
    public void create( byte[] image , String name ,  String price ) {
        mExampleList_outcart = new ArrayList<>();
        mExampleList_outcart.add(new ExampleItem1( image , name , price));
    }
    public void build(){
        mRecyclerView_outcart = findViewById(R.id.recyclerView_outcart);
        mRecyclerView_outcart.setHasFixedSize(true);
        mLayoutManager_outcart = new LinearLayoutManager(this);
        mAdapter_outcart = new ExampleAdapter1(mExampleList_outcart);
        mRecyclerView_outcart.setLayoutManager(mLayoutManager_outcart);
        mRecyclerView_outcart.setAdapter(mAdapter_outcart);

        mAdapter_outcart.setOnItemClickListner(new ExampleAdapter1.OnItemClickListner() {
            @Override
            public void onItemClick(int position) {
            }

            @Override
            public void onDeleteClick(int position) {
                String price_rec = getitem_price(position) ;
                total_out = total_out - Integer.parseInt(price_rec) ;
                outprice.setText(String.valueOf(total_out));
                removeItem(position);
            }
            @Override
            public void onIncreaseClick(int position) {
                String price_rec = getitem_price(position) ;
                total_out = total_out + Integer.parseInt(price_rec) ;
                outprice.setText(String.valueOf(total_out));
            }
            @Override
            public void onDecreaseClick(int position) {
                String price_rec = getitem_price(position) ;
                if ( total_out == 0 ) { outprice.setText("0"); }
                else {
                    total_out = total_out - Integer.parseInt(price_rec) ;
                    outprice.setText(String.valueOf(total_out));
                }
            }
        });
    }
    public void insertItem ( int position , byte[] image , String name  , String price ) {

        mExampleList_outcart.add(position , new ExampleItem1( image , name , price));
        mAdapter_outcart.notifyItemInserted(position);

    }
    public void removeItem (int position) {
        mExampleList_outcart.remove(position);
        mAdapter_outcart.notifyItemRemoved(position);
    }
}
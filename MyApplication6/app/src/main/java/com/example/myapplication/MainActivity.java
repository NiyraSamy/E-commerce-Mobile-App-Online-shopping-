package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static com.example.myapplication.R.drawable.download;


public class MainActivity extends AppCompatActivity {
    ImageView cart_image ,  voice_image , qr_image ;
    Database database ;
    private ArrayList<ExampleItem> mExampleList ;
    private RecyclerView mRecyclerView ;
    private ExampleAdapter mAdapter ;
    private RecyclerView.LayoutManager mLayoutManager ;
    private SearchView searchView ;
    private static final int REQUEST_CODE_SPEECH_INPUT = 1000 ;
    TextView  publicmail ;

    DrawerLayout drawerLayout ;
    List<String> listGroup;
    ExpandableListView expandableListView ;
    HashMap<String , List<String>> listItem ;
    MainAdapter adapter ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        publicmail = findViewById(R.id.public_email) ;
        Intent intent = getIntent() ;
        String mail = intent.getStringExtra("publicMail");
        publicmail.setText(mail);

        cart_image = findViewById(R.id.cart_image);
        qr_image = findViewById(R.id.qr_image) ;
        qr_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this , searchQR_Activity.class) ;
                startActivity(i);
            }
        });

        voice_image = findViewById(R.id.voice_image);
        voice_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak() ;
            }
        });

        searchView = findViewById(R.id.searchview) ;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(MainActivity.this , ViewProduct_Activity.class);
                String item = searchView.getQuery().toString() ;
                intent.putExtra("item" , item ) ;
                intent.putExtra("publicMail" , mail ) ;
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        cart_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this , out_cart.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_right , R.anim.slide_to_left);
            }
        });

        database = new Database(this);
        Cursor c = database.FetchtProducts();
        int count = c.getCount();
        String count_str = String.valueOf(count);

        create(c.getBlob(2) ,"name" , "price");
        build();
        removeItem(0);
        int i;
        for(i = 1 ; i<=count; i++)
        {
            if(i==11 ){i = i + 1 ;}
            if(i==21 ){i = i + 1 ;}
            if(i==22 ){i = i + 1 ;}
            if(i==48 ){i = i + 1 ;}
            if(i==52 ){i = i + 1 ;}
            Cursor cc = database.getid(i);
            insertItem(0, cc.getBlob(2) , cc.getString(1), cc.getString(3));

        }
        drawerLayout = findViewById(R.id.drawer_layout);

        expandableListView = findViewById(R.id.expandable_listview);
        listGroup = new ArrayList<>();
        listItem = new HashMap<>();
        adapter = new MainAdapter(MainActivity.this , listGroup , listItem );
        expandableListView.setAdapter(adapter);
        initListData();

        int id  = (int) expandableListView.getSelectedId();
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if(id == 0)
                {
                    Intent i = new Intent(MainActivity.this , Category1_Activity.class);
                    i.putExtra("publicMail" , mail) ;
                    startActivity(i);}
                if(id == 1)
                {
                    Intent i = new Intent(MainActivity.this , Category2_Activity.class);
                    i.putExtra("publicMail" , mail) ;
                    startActivity(i);
                }
                if(id == 2)
                {
                    Intent i = new Intent(MainActivity.this , Category3_Activity.class);
                    i.putExtra("publicMail" , mail) ;
                    startActivity(i);
                }
                if(id == 3)
                {
                    Intent i = new Intent(MainActivity.this , Category4_Activity.class);
                    i.putExtra("publicMail" , mail) ;
                    startActivity(i);
                }

                return false;
            }
        });

    }
    public void create( byte[] image , String name ,  String price ) {
        mExampleList = new ArrayList<>();
        mExampleList.add(new ExampleItem( image , name , price));
    }

    public void insertItem ( int position , byte[] image , String name  , String price ) {

        mExampleList.add(position , new ExampleItem( image , name , price));
        mAdapter.notifyItemInserted(position);
    }
    public void removeItem (int position) {
        mExampleList.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    public String getitem (int position  ) {
        String s = String.valueOf(mExampleList.get(position).getmText1());
        return s ;
    }

    public void build(){
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ExampleAdapter(mExampleList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListner(new ExampleAdapter.OnItemClickListner() {
            @Override
            public void onItemClick(int position) {
                String item = getitem( position );
                Intent i = new Intent(MainActivity.this , ViewProduct_Activity.class);
                i.putExtra("item" , item ) ;
                startActivity(i);

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
    private void  speak() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH) ;
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL , RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE , Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT , "Hi Speak To Search");
        try {
            startActivityForResult(intent , REQUEST_CODE_SPEECH_INPUT);
        }
        catch (Exception e)
        {
            Toast.makeText(this , ""+e.getMessage() , Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_SPEECH_INPUT : {
                if (resultCode == RESULT_OK && null!= data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) ;
                    searchView.setQuery(result.get(0) , true);
                }
            }
        }
    }
}
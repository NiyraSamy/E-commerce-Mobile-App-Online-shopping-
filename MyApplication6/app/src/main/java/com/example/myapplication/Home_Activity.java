package com.example.myapplication;

import androidx.annotation.Nullable;
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
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Home_Activity extends AppCompatActivity {
    ImageView cart_image ,  voice_image , qr_image ;
    private static final int REQUEST_CODE_SPEECH_INPUT = 1000 ;
    Database database ;
    TextView n11 , p11 , n12 , p12 , n21 , p21 , n22 , p22 , n31 , p31 , n32 , p32 , n41 , p41 , n42 , p42 , publicmail;
    ImageView i11 , i12 , i21 , i22 , i31 , i32 , i41 , i42 ;
    Button cat1_btn , cat2_btn , cat3_btn , cat4_btn , view ;

    DrawerLayout drawerLayout ;
    List<String> listGroup;
    ExpandableListView expandableListView ;
    HashMap<String , List<String>> listItem ;
    MainAdapter adapter ;
    private SearchView searchView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_);

        publicmail = findViewById(R.id.public_email) ;
        qr_image = findViewById(R.id.qr_image) ;
        Intent intent = getIntent() ;
        String mail = intent.getStringExtra("publicMail");
        publicmail.setText(mail);

        Intent intent1 = new Intent(Home_Activity.this , Home_Activity.class) ;
        intent1.putExtra("publicMail" , mail) ;

        Intent int1 = new Intent(Home_Activity.this , Home_Activity.class) ;
        intent1.putExtra("qr" , " ") ;

        qr_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home_Activity.this , searchQR_Activity.class) ;
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
                Intent intent = new Intent(Home_Activity.this , ViewProduct_Activity.class);
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

        ImageSlider imageSlider = findViewById(R.id.slider);

        List<SlideModel> slideModels = new ArrayList<>() ;
        slideModels.add(new SlideModel("https://assets.pandaily.com/uploads/2020/06/online-shopping-apps.jpg", "E_Commerce"));
        slideModels.add(new SlideModel("https://s.wsj.net/public/resources/images/B3-EO431_WEB20O_16H_20190724122507.jpg" , ""));

        imageSlider.setImageList(slideModels , true);
        n11 = findViewById(R.id.name11);
        p11 = findViewById(R.id.price11);
        n12 = findViewById(R.id.name12);
        p12 = findViewById(R.id.price12);

        n21 = findViewById(R.id.name21);
        p21 = findViewById(R.id.price21);
        n22 = findViewById(R.id.name22);
        p22 = findViewById(R.id.price22);

        n31 = findViewById(R.id.name31);
        p31 = findViewById(R.id.price31);
        n32 = findViewById(R.id.name32);
        p32 = findViewById(R.id.price32);

        n41 = findViewById(R.id.name41);
        p41 = findViewById(R.id.price41);
        n42 = findViewById(R.id.name42);
        p42 = findViewById(R.id.price42);

        i11 = findViewById(R.id.image11) ;
        i12 = findViewById(R.id.image12) ;
        i21 = findViewById(R.id.image21) ;
        i22 = findViewById(R.id.image22) ;
        i31 = findViewById(R.id.image31) ;
        i32 = findViewById(R.id.image32) ;
        i41 = findViewById(R.id.image41) ;
        i42 = findViewById(R.id.image42) ;

        database = new Database(this);
        Cursor c11 = database.getid(1);
        n11.setText(c11.getString(1));
        p11.setText(c11.getString(3));
        byte[] image_byte11 = c11.getBlob(2);
        Bitmap bmp11 = BitmapFactory.decodeByteArray(image_byte11, 0, image_byte11.length);
        i11.setImageBitmap(bmp11);

        Cursor c12 = database.getid(2);
        n12.setText(c12.getString(1));
        p12.setText(c12.getString(3));
        byte[] image_byte12 = c12.getBlob(2);
        Bitmap bmp12 = BitmapFactory.decodeByteArray(image_byte12, 0, image_byte12.length);
        i12.setImageBitmap(bmp12);

        Cursor c21 = database.getid(3);
        n21.setText(c21.getString(1));
        p21.setText(c21.getString(3));
        byte[] image_byte21 = c12.getBlob(2);
        Bitmap bmp21 = BitmapFactory.decodeByteArray(image_byte21, 0, image_byte21.length);
        i21.setImageBitmap(bmp21);

        Cursor c22 = database.getid(4);
        n22.setText(c22.getString(1));
        p22.setText(c22.getString(3));
        byte[] image_byte22 = c22.getBlob(2);
        Bitmap bmp22 = BitmapFactory.decodeByteArray(image_byte22, 0, image_byte22.length);
        i22.setImageBitmap(bmp22);

        Cursor c31 = database.getid(5);
        n31.setText(c31.getString(1));
        p31.setText(c31.getString(3));
        byte[] image_byte31 = c31.getBlob(2);
        Bitmap bmp31 = BitmapFactory.decodeByteArray(image_byte31, 0, image_byte31.length);
        i31.setImageBitmap(bmp31);

        Cursor c32 = database.getid(6);
        n32.setText(c32.getString(1));
        p32.setText(c32.getString(3));
        byte[] image_byte32 = c32.getBlob(2);
        Bitmap bmp32 = BitmapFactory.decodeByteArray(image_byte32, 0, image_byte32.length);
        i32.setImageBitmap(bmp32);

        Cursor c41 = database.getid(7);
        n41.setText(c41.getString(1));
        p41.setText(c41.getString(3));
        byte[] image_byte41 = c41.getBlob(2);
        Bitmap bmp41 = BitmapFactory.decodeByteArray(image_byte41, 0, image_byte41.length);
        i41.setImageBitmap(bmp41);

        Cursor c42 = database.getid(8);
        n42.setText(c42.getString(1));
        p42.setText(c42.getString(3));
        byte[] image_byte42 = c42.getBlob(2);
        Bitmap bmp42 = BitmapFactory.decodeByteArray(image_byte42, 0, image_byte42.length);
        i42.setImageBitmap(bmp42);

        i11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home_Activity.this , ViewProduct_Activity.class);
                i.putExtra("item" , n11.getText().toString() );
                i.putExtra("publicMail" , mail ) ;
                startActivity(i);
            }
        });
        i12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home_Activity.this , ViewProduct_Activity.class);
                i.putExtra("item" , n12.getText().toString() );
                i.putExtra("publicMail" , mail ) ;
                startActivity(i);
            }
        });
        i21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home_Activity.this , ViewProduct_Activity.class);
                i.putExtra("item" , n21.getText().toString() );
                i.putExtra("publicMail" , mail ) ;
                startActivity(i);
            }
        });
        i22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home_Activity.this , ViewProduct_Activity.class);
                i.putExtra("item" , n22.getText().toString() );
                i.putExtra("publicMail" , mail ) ;
                startActivity(i);
            }
        });
        i31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home_Activity.this , ViewProduct_Activity.class);
                i.putExtra("item" , n31.getText().toString() );
                i.putExtra("publicMail" , mail ) ;
                startActivity(i);
            }
        });
        i32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home_Activity.this , ViewProduct_Activity.class);
                i.putExtra("item" , n32.getText().toString() );
                i.putExtra("publicMail" , mail ) ;
                startActivity(i);
            }
        });
        i41.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home_Activity.this , ViewProduct_Activity.class);
                i.putExtra("item" , n41.getText().toString() );
                i.putExtra("publicMail" , mail ) ;
                startActivity(i);
            }
        });
        i42.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home_Activity.this , ViewProduct_Activity.class);
                i.putExtra("item" , n42.getText().toString() );
                i.putExtra("publicMail" , mail ) ;
                startActivity(i);
            }
        });

        cat1_btn = findViewById(R.id.view_cat1_btn) ;
        cat2_btn = findViewById(R.id.view_cat2_btn) ;
        cat3_btn = findViewById(R.id.view_cat3_btn) ;
        cat4_btn = findViewById(R.id.view_cat4_btn) ;

        cat1_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home_Activity.this , Category1_Activity.class);
                i.putExtra("publicMail" , mail ) ;
                startActivity(i);
            }
        });
        cat2_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home_Activity.this , Category2_Activity.class);
                i.putExtra("publicMail" , mail ) ;
                startActivity(i);
            }
        });
        cat3_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home_Activity.this , Category3_Activity.class);
                i.putExtra("publicMail" , mail ) ;
                startActivity(i);
            }
        });
        cat4_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home_Activity.this , Category4_Activity.class);
                i.putExtra("publicMail" , mail ) ;
                startActivity(i);
            }
        });
        view = findViewById(R.id.viewall_btn);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home_Activity.this , MainActivity.class);
                intent.putExtra("publicMail" , mail ) ;
                startActivity(intent);
            }
        });

        drawerLayout = findViewById(R.id.drawer_layout);

        expandableListView = findViewById(R.id.expandable_listview);
        listGroup = new ArrayList<>();
        listItem = new HashMap<>();
        adapter = new MainAdapter(Home_Activity.this , listGroup , listItem );
        expandableListView.setAdapter(adapter);
        initListData();

        int id  = (int) expandableListView.getSelectedId();
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if(id == 0)
                {
                    Intent i = new Intent(Home_Activity.this , Category1_Activity.class);
                    i.putExtra("publicMail" , mail ) ;
                    startActivity(i);}
                if(id == 1)
                {
                    Intent i = new Intent(Home_Activity.this , Category2_Activity.class);
                    i.putExtra("publicMail" , mail ) ;
                    startActivity(i);
                }
                if(id == 2)
                {
                    Intent i = new Intent(Home_Activity.this , Category3_Activity.class);
                    i.putExtra("publicMail" , mail ) ;
                    startActivity(i);
                }
                if(id == 3)
                {
                    Intent i = new Intent(Home_Activity.this , Category4_Activity.class);
                    i.putExtra("publicMail" , mail ) ;
                    startActivity(i);
                }
                return false;
            }
        });

        cart_image = findViewById(R.id.cart_image);
        cart_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home_Activity.this , out_cart.class);
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
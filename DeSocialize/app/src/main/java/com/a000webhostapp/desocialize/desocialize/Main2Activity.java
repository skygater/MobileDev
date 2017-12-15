package com.a000webhostapp.desocialize.desocialize;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.a000webhostapp.desocialize.desocialize.java.User;
import com.a000webhostapp.desocialize.desocialize.localdb.DatabaseHelper;
import com.squareup.picasso.Picasso;

public class Main2Activity extends AppCompatActivity {
    TextView txt;
    String responce = "Hello";
    ImageView imgQr;

    //DATA BASE
    //Locla DataBase
    private DatabaseHelper mLocalDb;
    private User u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        txt = (TextView) findViewById(R.id.testqrtext);
        imgQr = (ImageView) findViewById(R.id.qr_img_show);


        responce = getIntent().getExtras().getString("QR-ID");
        txt.setText(responce);

        mLocalDb = new DatabaseHelper(this);
        u = mLocalDb.player();


    }

    public void backQr (View view){
        Intent next = new Intent(Main2Activity.this, QrScanActivity.class);
        startActivity(next);
        finish();
    }

    public void showQr (View view){
        Picasso.with(this).load(u.getQr()).into(imgQr);
    }
}

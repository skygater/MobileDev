package com.a000webhostapp.desocialize.desocialize;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.a000webhostapp.desocialize.desocialize.java.User;
import com.a000webhostapp.desocialize.desocialize.localdb.DatabaseHelper;
import com.squareup.picasso.Picasso;

public class QrShow extends AppCompatActivity {
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
        setContentView(R.layout.activity_qrshow);
        imgQr = (ImageView) findViewById(R.id.qr_img_show);
        responce = getIntent().getExtras().getString("qrid");

        Picasso.with(this).load(responce).into(imgQr);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent next = new Intent(QrShow.this, MenuActivity.class);
        startActivity(next);
        finish();
    }

}

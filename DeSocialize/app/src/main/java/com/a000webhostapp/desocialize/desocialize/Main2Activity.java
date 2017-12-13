package com.a000webhostapp.desocialize.desocialize;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {
    TextView txt;
    String responce;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        txt = (TextView) findViewById(R.id.testqrtext);

        responce = getIntent().getExtras().getString("QR-ID");

        txt.setText(responce);

    }
}

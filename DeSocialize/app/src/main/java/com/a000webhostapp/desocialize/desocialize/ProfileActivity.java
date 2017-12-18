package com.a000webhostapp.desocialize.desocialize;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {
    Integer userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        userId = getIntent().getIntExtra("Profile ID",2 );

        TextView test = findViewById(R.id.usrid);
        test.setText(userId+"");

    }
}

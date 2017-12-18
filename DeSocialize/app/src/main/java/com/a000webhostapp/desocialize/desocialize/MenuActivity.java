package com.a000webhostapp.desocialize.desocialize;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity {
    Integer id = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // setup buttons
        findViewById(R.id.menu_sp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singlePlayer();
            }
        });
        findViewById(R.id.menu_mp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                multiplayer();
            }
        });

        findViewById(R.id.btn_avtar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profile(id);
            }
        });
    }

    private void singlePlayer(){
        Intent intent = new Intent(this, SingleplayerActivity.class);
        startActivity(intent);
    }

    /*
     * Starts multiplayer mode
     *      - started when user clicks on MP button
     *      - Starts corresponding activity
     */
    private void multiplayer(){
        Intent intent = new Intent(this, MultiplayerActivity.class);
        startActivity(intent);
    }

    private void profile(Integer id){
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("Profile ID", id);
        startActivity(intent);
    }
}

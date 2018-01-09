package com.a000webhostapp.desocialize.desocialize;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.a000webhostapp.desocialize.desocialize.adapters.LobbyAdapter;
import com.a000webhostapp.desocialize.desocialize.java.FriendsOnline;

import java.util.ArrayList;
import java.util.List;

public class LobbyActivity extends AppCompatActivity {
    ListView listview;
    List<FriendsOnline> list;

    LayoutInflater inflater;
    List<String> forScroll;
    LobbyAdapter lobbyAdapter;
    Integer isModeSelected = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);
        list = new ArrayList<>();
        list = (List<FriendsOnline>) getIntent().getSerializableExtra("selectedList");
        forScroll = new ArrayList<>();

        inflater = this.getLayoutInflater();
        listview = findViewById(R.id.lobby_list);
        lobbyAdapter = new LobbyAdapter(this,list);
        listview.setAdapter(lobbyAdapter);

        findViewById(R.id.nav_back_lobby).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });

        findViewById(R.id.lobby_mode_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isModeSelected!=2) {
                    isModeSelected = 2; // toggle the boolean flag
                    findViewById(R.id.lobby_mode_first).setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.de_green));
                    findViewById(R.id.lobby_mode_two).setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(),R.color.de_grey));
                }
                else if (isModeSelected==2) {
                    isModeSelected = 0;
                    findViewById(R.id.lobby_mode_first).setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.colorPrimary));
                    findViewById(R.id.lobby_mode_two).setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(),R.color.colorPrimary));

                }
            }
        });

        findViewById(R.id.lobby_mode_two).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isModeSelected!=1) {
                    isModeSelected = 1; // toggle the boolean flag
                    findViewById(R.id.lobby_mode_first).setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(),R.color.de_grey));
                    findViewById(R.id.lobby_mode_two).setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(),R.color.de_green));
                }
                else if (isModeSelected==1) {
                    isModeSelected = 0;
                    findViewById(R.id.lobby_mode_first).setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.colorPrimary));
                    findViewById(R.id.lobby_mode_two).setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(),R.color.colorPrimary));

                }
            }
        });
    }

    public void back(){
        Intent intent = new Intent(this, MultiplayerActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MultiplayerActivity.class);
        startActivity(intent);
        finish();
    }

    public void start_game(){
        Intent intent = new Intent(this, MultiplayerActivity.class);
        startActivity(intent);
    }
}

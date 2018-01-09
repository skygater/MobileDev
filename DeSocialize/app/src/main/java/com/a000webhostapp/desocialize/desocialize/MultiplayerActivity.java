package com.a000webhostapp.desocialize.desocialize;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.a000webhostapp.desocialize.desocialize.Task.OnlineFriends;
import com.a000webhostapp.desocialize.desocialize.adapters.AdapterBase;
import com.a000webhostapp.desocialize.desocialize.adapters.ScrollAdapter;
import com.a000webhostapp.desocialize.desocialize.java.FriendsOnline;
import com.a000webhostapp.desocialize.desocialize.java.User;
import com.a000webhostapp.desocialize.desocialize.localdb.DatabaseHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MultiplayerActivity extends AppCompatActivity {

    ListView listview;
    //HorizontalScrollView scroll;
    LayoutInflater inflater;
    // New List adapter Dj
    AdapterBase adapterBase;
    List<FriendsOnline> forScroll;
    ScrollAdapter scrollAdapter;
    GridView gridView;
    // Liste
    List<FriendsOnline> friendsOnline;
    User u;
    JSONObject jsonObject;
    JSONArray jsonArray;
    DatabaseHelper databaseHelper;
    //Services
   TextView checking;
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer);
        databaseHelper = new DatabaseHelper(this);
        checking = (TextView) findViewById(R.id.checkOnline);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar3);
        forScroll = new ArrayList<>();

        OnlineFriends onlineFriends = new OnlineFriends(this){
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JSON_String = s;
                friendsOnline = new ArrayList<>();
                FriendsOnline friends= null;
                try {
                    jsonObject = new JSONObject(JSON_String);
                    jsonArray = jsonObject.getJSONArray("server_responce");
                    int count = 0;
                    String imgp, username;
                    int idp,points;
                    while(count < jsonArray.length()){
                        JSONObject jo = jsonArray.getJSONObject(count);
                        idp = jo.getInt("idpy");
                        imgp = jo.getString("imgp");
                        username = jo.getString("username");
                        points = jo.getInt("points");
                        friends = new FriendsOnline(idp,username,imgp,points);
                        friendsOnline.add(friends);
                        count ++;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        u = databaseHelper.player();

        onlineFriends.execute(u.getIdu());

        CountDownTimer mCountDownTimer;
        final int[] i = {0};
        mProgressBar.setProgress(i[0]);
        mProgressBar.setVisibility(View.VISIBLE);
        mCountDownTimer=new CountDownTimer(6000,5000) {

            @Override
            public void onTick(long millisUntilFinished) {
                Log.v("Log_tag", "Tick of Progress"+ i[0] + millisUntilFinished);
                i[0]++;
                mProgressBar.setProgress(i[0]);
            }
            @Override
            public void onFinish() {

                checking.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.GONE);
                //Toast.makeText(MultiplayerActivity.this, "HELLO??", Toast.LENGTH_SHORT).show();
               // Toast.makeText(MultiplayerActivity.this, friendsOnline.size()+"", Toast.LENGTH_SHORT).show();
                if (friendsOnline.size() != 0) {
                    //Toast.makeText(MultiplayerActivity.this, friendsOnline.get(0).getUsername(), Toast.LENGTH_SHORT).show();
                    //New Adapter;
                   inflateUsers();
                    //listview.setAdapter(adapterBase);
                }else{
                    checking.setVisibility(View.VISIBLE);
                    checking.setText("No players Online");
                    Toast.makeText(MultiplayerActivity.this, "No players Online ", Toast.LENGTH_SHORT).show();
                }


            }
        };
        mCountDownTimer.start();

        findViewById(R.id.nav_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });

        findViewById(R.id.nav_fwd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                to_lobby(forScroll);
            }
        });
    }

    public void inflateUsers (){
        adapterBase = new AdapterBase(this, friendsOnline, forScroll);
        inflater = this.getLayoutInflater();
        listview = findViewById(R.id.list);
        listview.setAdapter(adapterBase);
    }


    public void adder_logic (List<FriendsOnline> lista)
    {
        forScroll = lista;

        scrollAdapter = new ScrollAdapter(forScroll,this,friendsOnline);
        gridView = (GridView) findViewById(R.id.gridScrole);
        int size=lista.size();
        // Calculated single Item Layout Width for each grid element ....
        int width =80;

        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;

        int totalWidth = (int) (width * size * density);
        int singleItemWidth = (int) (width * density);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                totalWidth, LinearLayout.LayoutParams.MATCH_PARENT);

        gridView.setLayoutParams(params);
        gridView.setColumnWidth(singleItemWidth);
        gridView.setHorizontalSpacing(2);
        gridView.setStretchMode(GridView.STRETCH_SPACING);
        gridView.setNumColumns(size);
        gridView.setAdapter(scrollAdapter);



    }

    public void remover_logic (List<FriendsOnline> lista)
    {
        friendsOnline =  lista;
        adapterBase = new  AdapterBase(this,friendsOnline,forScroll);
        listview = findViewById(R.id.list);
        listview.setAdapter(adapterBase);

    }

    public void back(){
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
        finish();

    }

    public void to_lobby(List <FriendsOnline> lista){
        Intent intent = new Intent(this, LobbyActivity.class);
        intent.putExtra("selectedList", (Serializable) lista);
        startActivity(intent);
        finish();
    }
}

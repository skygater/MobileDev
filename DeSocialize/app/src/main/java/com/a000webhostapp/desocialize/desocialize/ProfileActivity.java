package com.a000webhostapp.desocialize.desocialize;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.a000webhostapp.desocialize.desocialize.Task.Profilupdate;
import com.a000webhostapp.desocialize.desocialize.java.FriendsOnline;
import com.a000webhostapp.desocialize.desocialize.java.User;
import com.a000webhostapp.desocialize.desocialize.localdb.DatabaseHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    User u;
    JSONObject jsonObject;
    JSONArray jsonArray;
    DatabaseHelper databaseHelper;

    Profilupdate profilupdate;

    TextView profileFriends, username, pointScore,email;

     int noFriends, points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        databaseHelper = new DatabaseHelper(this);
        profileFriends = (TextView) findViewById(R.id.profile_friends);
        username = (TextView) findViewById(R.id.profile_username) ;
        pointScore = (TextView) findViewById(R.id.profile_points);
        email = (TextView) findViewById(R.id.profile_email);

        profilupdate = new Profilupdate(this){
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JSON_String = s;


                try {
                    jsonObject = new JSONObject(JSON_String);
                    jsonArray = jsonObject.getJSONArray("server_responce");
                    int count = 0;

                    while(count < jsonArray.length()){
                        JSONObject jo = jsonArray.getJSONObject(count);
                        noFriends = jo.getInt("noFriends");
                        points = jo.getInt("points");
                        count ++;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
        u = databaseHelper.player();
        profilupdate.execute(u.getIdu());

        profileFriends.setText(noFriends+"");
        username.setText(u.getEmail());

        pointScore.setText(points+"Points");
        email.setText(u.getUsername());



        findViewById(R.id.nav_back_profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
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
}


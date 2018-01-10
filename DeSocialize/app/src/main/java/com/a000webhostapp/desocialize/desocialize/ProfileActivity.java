package com.a000webhostapp.desocialize.desocialize;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.a000webhostapp.desocialize.desocialize.Task.OnlineFriends;
import com.a000webhostapp.desocialize.desocialize.java.FriendsOnline;
import com.a000webhostapp.desocialize.desocialize.java.Profile;
import com.a000webhostapp.desocialize.desocialize.java.User;
import com.a000webhostapp.desocialize.desocialize.localdb.DatabaseHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    User u;
    List<Profile> p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);

        Profile profile = new Profile(this){
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JSON_String = s;
                p = new ArrayList<Profile>();
                Profile prof= null;
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
                        prof = new Profile(username,imgp,points,count);
                        profile.add(prof);
                        count ++;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        u = databaseHelper.player();

        profile.execute(u.getIdu());

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


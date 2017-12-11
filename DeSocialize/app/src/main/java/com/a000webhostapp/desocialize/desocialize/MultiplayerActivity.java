package com.a000webhostapp.desocialize.desocialize;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

public class MultiplayerActivity extends AppCompatActivity {

    ListView listview;
    //HorizontalScrollView scroll;
    LinearLayout scroll;
    String[] pointArray = new String[2];
    String[] nameArray = new String[2];
    Integer[] imageIDArray = new Integer[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer);

        nameArray[0] = "Jack";
        pointArray[0] = "100";
        nameArray[1] = "James";
        pointArray[1] = "122";
        imageIDArray[0] = 1;
        imageIDArray[1] = 2;


        CustomListAdapter whatever = new CustomListAdapter(this, pointArray, nameArray, imageIDArray);

        listview = findViewById(R.id.list);
        //scroll = findViewById(R.id.scrollId);
        scroll = findViewById(R.id.list_added);

        for( int i = 0; i < 28; i++){

            /*ImageView x = new ImageView(getApplicationContext());
            x.setImageResource(R.mipmap.ic_launcher);
            scroll.addView(x);*/

            LayoutInflater inflater=this.getLayoutInflater();
            View rowView=inflater.inflate(R.layout.scroller_user, null,true);

            ImageView x = rowView.findViewById(R.id.scroller_avatar);
            TextView y = rowView.findViewById(R.id.scroller_name);
            x.setImageResource(R.mipmap.ic_launcher);
            y.setText("James");
            scroll.addView(rowView);
        }

        listview.setAdapter(whatever);


    }
}

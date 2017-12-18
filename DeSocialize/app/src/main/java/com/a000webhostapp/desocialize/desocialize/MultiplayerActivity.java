package com.a000webhostapp.desocialize.desocialize;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MultiplayerActivity extends AppCompatActivity {

    ListView listview;
    //HorizontalScrollView scroll;
    LinearLayout scroll;

    ArrayList<String> nameList = new ArrayList<String>();
    ArrayList<Integer> pointList = new ArrayList<Integer>();
    Integer[] imageIDArray = new Integer[2];
    LayoutInflater inflater;
    CustomListAdapter whatever;
    View rowView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer);

        nameList.add("Dare");
        nameList.add("BouBo");
        pointList.add(2);
        pointList.add(3);

        whatever = new CustomListAdapter(this, pointList, nameList, imageIDArray);

        inflater = this.getLayoutInflater();
        listview = findViewById(R.id.list);

        //scroll = findViewById(R.id.scrollId);
         scroll = findViewById(R.id.list_added);

        listview.setAdapter(whatever);
        //whatever.remove(ar);


    }
    public void adder_logic (final Integer position)
    {
        rowView = inflater.inflate(R.layout.scroller_user, null ,true);
        scroll = findViewById(R.id.list_added);

        ImageView x = rowView.findViewById(R.id.scroller_avatar);
        final TextView y = rowView.findViewById(R.id.scroller_name);
        x.setImageResource(R.mipmap.ic_launcher);
        y.setText(nameList.get(position));
        rowView.setTag(position);
        //rowView.setTag(scroll.getVerticalScrollbarPosition());
        scroll.addView(rowView);


        Button remover = rowView.findViewById(R.id.scroller_remove);
        remover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = 2;
                position = (Integer) rowView.getTag();
                remover_logic(position);
            }
        });

    }
    public void remover_logic (Integer position)
    {
        Toast.makeText(this, position + "", Toast.LENGTH_SHORT).show();

        //scroll.removeViewAt(position);
    }

}

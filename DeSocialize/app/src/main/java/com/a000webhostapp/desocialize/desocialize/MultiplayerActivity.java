package com.a000webhostapp.desocialize.desocialize;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

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

    // New List adapter Dj
    AdapterBase adapterBase;
    List<String> forScroll;

    ScrollAdapter scrollAdapter;

    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer);

        nameList.add("Dare");
        nameList.add("BouBo");
        nameList.add("Dare");
        nameList.add("BouBo");
        nameList.add("Dare");
        nameList.add("BouBo");
        nameList.add("Dare");
        nameList.add("BouBo");
        pointList.add(2);
        pointList.add(3);

        forScroll = new ArrayList<>();


        whatever = new CustomListAdapter(this, pointList, nameList, imageIDArray);
        //Neww Adapter;
        adapterBase = new AdapterBase(this,nameList,forScroll);

        inflater = this.getLayoutInflater();
        listview = findViewById(R.id.list);


        //scroll =(HorizontalScrollView) findViewById(R.id.scrollId);
         //scroll = findViewById(R.id.list_added);

        listview.setAdapter(adapterBase);
        //whatever.remove(ar);



    }


    public void adder_logic (List<String> lista)
    {
       forScroll = lista;

       scrollAdapter = new ScrollAdapter(forScroll,this,nameList);
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

    public void remover_logic (List<String> lista)
    {
        nameList = (ArrayList<String>) lista;
        adapterBase = new AdapterBase(this,nameList,forScroll);
        listview = findViewById(R.id.list);
        listview.setAdapter(adapterBase);

    }

}

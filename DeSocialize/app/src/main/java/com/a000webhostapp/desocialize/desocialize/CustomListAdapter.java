package com.a000webhostapp.desocialize.desocialize;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class CustomListAdapter extends ArrayAdapter {

    //to reference the Activity
    private final Activity context;

    //to store the images
    private final Integer[] imageArray;

    private Context mContext;

    ArrayList<String> nameList = new ArrayList<String>();
    ArrayList<Integer> pointList = new ArrayList<Integer>();



    public CustomListAdapter(Activity context, ArrayList<Integer> pointList, ArrayList<String> nameList, Integer[] imageArrayParam){

        super(context,R.layout.list_row , nameList);

        this.context=context;
        this.imageArray = imageArrayParam;
        this.nameList = nameList;
        this.pointList = pointList;
        this.mContext = context;

    }

    public View getView(final int position, View view, final ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        final View rowView=inflater.inflate(R.layout.list_row, null,true);

        //this code gets references to objects in the list_row.xml file
        final TextView nametxt =  rowView.findViewById(R.id.list_name);
        final TextView pointtxt =  rowView.findViewById(R.id.list_points);
        ImageView image = rowView.findViewById(R.id.list_avatar);
        Button btn = rowView.findViewById(R.id.list_add_btn);

        //this code sets the values of the objects to values from the arrays
        nametxt.setText(nameList.get(position));
        pointtxt.setText(pointList.get(position) +"");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return rowView;

    };

}

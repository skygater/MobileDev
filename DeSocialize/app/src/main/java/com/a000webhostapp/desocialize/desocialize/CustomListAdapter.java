package com.a000webhostapp.desocialize.desocialize;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


public class CustomListAdapter extends ArrayAdapter {

    //to reference the Activity
    private final Activity context;

    //to store the images
    private final Integer[] imageArray;

    //to store the names
    private final String[] nameArray;

    //to store the points
    private final String[] pointArray;

    public CustomListAdapter(Activity context, String[] pointArrayParam, String[] nameArrayParam, Integer[] imageArrayParam){

        super(context,R.layout.list_row , nameArrayParam);

        this.context=context;
        this.imageArray = imageArrayParam;
        this.nameArray = nameArrayParam;
        this.pointArray = pointArrayParam;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.list_row, null,true);

        //this code gets references to objects in the list_row.xml file
        TextView nametxt =  rowView.findViewById(R.id.list_name);
        TextView pointtxt =  rowView.findViewById(R.id.list_points);
        ImageView image = rowView.findViewById(R.id.list_avatar);

        //this code sets the values of the objects to values from the arrays
        nametxt.setText(nameArray[position]);
        pointtxt.setText(pointArray[position]+"");
        //image.setImageResource(imageArray[position]);

        return rowView;

    };
}

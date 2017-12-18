package com.a000webhostapp.desocialize.desocialize;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by djordjekalezic on 18/12/2017.
 */

public class AdapterBase extends BaseAdapter {
    Context mContext;
    List<String> listNames;
     List <String> listScroll;

    public AdapterBase (Context mContext, List<String> listNames, List<String> listScroll){
        this.mContext = mContext;
        this.listNames = listNames;
        this.listScroll = listScroll;

    }


    @Override
    public int getCount() {
        return listNames.size();
    }

    @Override
    public Object getItem(int position) {
        return listNames.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = View.inflate(mContext, R.layout.list_row,null);
        final TextView list_name = (TextView) view.findViewById(R.id.list_name);
        list_name.setText(listNames.get(position).toString());
        Button btn = (Button) view.findViewById(R.id.list_add_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,  position+"", Toast.LENGTH_SHORT).show();
                listScroll.add(listNames.get(position).toString());
                if(mContext instanceof MultiplayerActivity){
                    ((MultiplayerActivity)mContext).adder_logic(listScroll);
                    listNames.remove(position);
                    notifyDataSetChanged();
                }
            }
        });



        view.setTag(position);
        return view;

    }
}

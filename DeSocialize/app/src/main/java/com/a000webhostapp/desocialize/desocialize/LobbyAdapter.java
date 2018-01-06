package com.a000webhostapp.desocialize.desocialize;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Dario on 06-Jan-18.
 */

public class LobbyAdapter extends BaseAdapter {
    Context mContext;
    List<String> listNames;

    public LobbyAdapter (Context mContext, List<String> listNames){
        this.mContext = mContext;
        this.listNames = listNames;
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

        View view = View.inflate(mContext, R.layout.lobby_list_row,null);
        final TextView list_name = (TextView) view.findViewById(R.id.lobby_list_name);
        list_name.setText(listNames.get(position).toString());
        ImageButton btn = (ImageButton) view.findViewById(R.id.lobby_list_add_btn);
        view.setTag(position);
        return view;

    }
}

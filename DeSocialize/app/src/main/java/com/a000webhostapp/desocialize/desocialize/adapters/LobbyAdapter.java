package com.a000webhostapp.desocialize.desocialize.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.a000webhostapp.desocialize.desocialize.R;
import com.a000webhostapp.desocialize.desocialize.java.FriendsOnline;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by djordjekalezic on 07/01/2018.
 */

public class LobbyAdapter extends BaseAdapter {
    Context mContext;
    List<FriendsOnline> listNames;

    public LobbyAdapter (Context mContext, List<FriendsOnline> listNames){
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

        View view = View.inflate(mContext, R.layout.item_lobbyfriends,null);
        final TextView list_name = (TextView) view.findViewById(R.id.lobby_list_name);

        TextView list_points = (TextView) view.findViewById(R.id.lobby_list_points);
        ImageView list_avatar =(ImageView) view.findViewById(R.id.lobby_list_avatar);
        list_name.setText(listNames.get(position).getUsername());
        list_points.setText(listNames.get(position).getPoints()+"pt");

        if(!listNames.get(position).getImgp().equalsIgnoreCase("//")) {
            Picasso.with(mContext).load(listNames.get(position).getImgp()).into(list_avatar);
        }
        view.setTag(position);
        return view;

    }
}

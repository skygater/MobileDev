package com.a000webhostapp.desocialize.desocialize.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.a000webhostapp.desocialize.desocialize.MultiplayerActivity;
import com.a000webhostapp.desocialize.desocialize.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by djordjekalezic on 07/01/2018.
 */

public class ScrollAdapter extends BaseAdapter {

    List<String> listNames;
    Context mContext;
    List<String>nazad;

    public ScrollAdapter(List<String> listNames, Context mContext, List<String> nazad){
        this.mContext = mContext;
        this.listNames = listNames;
        this.nazad = nazad;
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
    public View getView( final int position, View convertView, ViewGroup parent) {
        View v = View.inflate(mContext, R.layout.item_scrolladd, null);
        TextView name = v.findViewById(R.id.scroller_name);
        ImageView xpic = v.findViewById(R.id.scroller_avatar);
        xpic.setImageResource(R.mipmap.ic_launcher);
        name.setText(listNames.get(position).toString());

        Button remover = v.findViewById(R.id.scroller_remove);
        remover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nazad.add(listNames.get(position).toString());
                if(mContext instanceof MultiplayerActivity){
                    ((MultiplayerActivity)mContext).remover_logic(nazad);
                    listNames.remove(position);
                    notifyDataSetChanged();
                }
                //Toast.makeText(mContext, position+"", Toast.LENGTH_SHORT).show();

            }
        });

        v.setTag(position);
        return v;

    }
}

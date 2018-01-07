package com.a000webhostapp.desocialize.desocialize.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.a000webhostapp.desocialize.desocialize.MultiplayerActivity;
import com.a000webhostapp.desocialize.desocialize.R;

import java.util.List;

/**
 * Created by djordjekalezic on 07/01/2018.
 */

public class AdapterBase  extends BaseAdapter {
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

        View view = View.inflate(mContext, R.layout.item_addmulti,null);
        final TextView list_name = (TextView) view.findViewById(R.id.list_name);
        list_name.setText(listNames.get(position).toString());
        ImageButton btn = (ImageButton) view.findViewById(R.id.list_add_btn);
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

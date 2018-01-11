package com.a000webhostapp.desocialize.desocialize.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.a000webhostapp.desocialize.desocialize.R;

import java.util.List;

/**
 * Created by djordjekalezic on 10/01/2018.
 */

public class AvatarSelectorAdapter extends BaseAdapter {
    Context mContext;
    List<Integer> listImages;

    public AvatarSelectorAdapter (Context mContext, List<Integer> listImages){
        this.mContext = mContext;
        this.listImages = listImages;
    }


    @Override
    public int getCount() {
        return listImages.size();
    }

    @Override
    public Object getItem(int position) {
        return listImages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = View.inflate(mContext, R.layout.item_myavatars,null);
        final TextView avatar_selected = (TextView) view.findViewById(R.id.avatar_selected);
        ImageView avatar =(ImageView) view.findViewById(R.id.avatar_picker);

        //Napravit ovo sa serverom
        /*if(getItemId(position)== selected){
        avatar_selected.setText("Selected");}*/

        avatar.setImageResource(listImages.get(position));

        return view;

    }
}

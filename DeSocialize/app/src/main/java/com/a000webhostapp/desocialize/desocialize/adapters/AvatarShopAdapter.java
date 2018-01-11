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

public class AvatarShopAdapter extends BaseAdapter {
    Context mContext;
    List<Integer> listPrice;
    List<Integer> listImages;

    public AvatarShopAdapter (Context mContext, List<Integer> listPrice, List<Integer> listImages){
        this.mContext = mContext;
        this.listPrice = listPrice;
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

        View view = View.inflate(mContext, R.layout.item_shopadd,null);
        final TextView avatar_price = (TextView) view.findViewById(R.id.price);
        ImageView avatar_image =(ImageView) view.findViewById(R.id.avatar);

        avatar_price.setText(listPrice.get(position)+"");
        avatar_image.setImageResource(listImages.get(position));

        /*if(!listNames.get(position).getImgp().equalsIgnoreCase("//")) {
            Picasso.with(mContext).load(listNames.get(position).getImgp()).into(list_avatar);
        }*/

        /*ImageButton btn = (ImageButton) view.findViewById(R.id.list_add_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,  position+"", Toast.LENGTH_SHORT).show();
                listScroll.add(listNames.get(position));
                if(mContext instanceof MultiplayerActivity){
                    ((MultiplayerActivity)mContext).adder_logic(listScroll);
                    listNames.remove(position);
                    notifyDataSetChanged();
                }
            }
        });



        view.setTag(listImages.get(position).getIdpy());*/
        return view;

    }
}

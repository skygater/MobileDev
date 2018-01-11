package com.a000webhostapp.desocialize.desocialize;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.a000webhostapp.desocialize.desocialize.adapters.AvatarSelectorAdapter;
import com.a000webhostapp.desocialize.desocialize.adapters.AvatarShopAdapter;

import java.util.ArrayList;
import java.util.List;

public class ShopActivity extends AppCompatActivity {

    List<Integer> images;
    List<Integer> prices;
    AvatarShopAdapter adapterShop;
    AvatarSelectorAdapter adapterSelector;
    LayoutInflater inflater;
    GridView grid;
    GridView lista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        prices = new ArrayList<>();
        images = new ArrayList<>();

        prices.add(300);
        images.add(R.drawable.common_google_signin_btn_icon_dark);
        prices.add(300);
        images.add(R.drawable.common_google_signin_btn_icon_dark);
        prices.add(300);
        images.add(R.drawable.common_google_signin_btn_icon_dark);
        prices.add(300);
        images.add(R.drawable.common_google_signin_btn_icon_dark);
        prices.add(300);
        images.add(R.drawable.common_google_signin_btn_icon_dark);
        prices.add(300);
        images.add(R.drawable.common_google_signin_btn_icon_dark);
        prices.add(300);
        images.add(R.drawable.common_google_signin_btn_icon_dark);
        prices.add(300);
        images.add(R.drawable.common_google_signin_btn_icon_dark);
        prices.add(300);
        images.add(R.drawable.common_google_signin_btn_icon_dark);


        adapterShop = new AvatarShopAdapter(this, prices, images);
        inflater = this.getLayoutInflater();
        grid = findViewById(R.id.avatargrid);
        grid.setAdapter(adapterShop);

        adapterSelector = new AvatarSelectorAdapter(this, images);
        inflater = this.getLayoutInflater();

        int size=images.size();
        // Calculated single Item Layout Width for each grid element ....
        int width =80;

        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;

        int totalWidth = (int) (width * size * density);
        int singleItemWidth = (int) (width * density);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                totalWidth, LinearLayout.LayoutParams.MATCH_PARENT);

        lista = findViewById(R.id.gridScroll);
        lista.setLayoutParams(params);
        lista.setColumnWidth(singleItemWidth);
        lista.setHorizontalSpacing(2);
        lista.setStretchMode(GridView.STRETCH_SPACING);
        lista.setNumColumns(size);
        lista.setAdapter(adapterSelector);
    }

    public void back(View view){
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
        finish();

    }
}

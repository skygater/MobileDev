package com.a000webhostapp.desocialize.desocialize;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.jaredrummler.android.widget.AnimatedSvgView;

public class MainActivity extends AppCompatActivity {

    AnimatedSvgView svgView;
    int index = -1;
    LinearLayout lh1 ;
    LinearLayout layoutlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        svgView = (AnimatedSvgView) findViewById(R.id.animated_svg_view);
        lh1 = (LinearLayout) findViewById(R.id.layouth1);
        layoutlogin  = (LinearLayout) findViewById(R.id.layoutlogin);


        // LOGO ANIMATION0
        svgView.postDelayed(new Runnable() {
            @Override public void run() {
                svgView.start();
            }
        }, 500);

        svgView.setOnStateChangeListener(new AnimatedSvgView.OnStateChangeListener() {

            @Override public void onStateChange(@AnimatedSvgView.State int state) {
                // Check LOCAL DB! if (state == AnimatedSvgView.STATE_TRACE_STARTED)
                if (state == AnimatedSvgView.STATE_FINISHED) {
                    lh1.setGravity(0);
                    lh1.setPadding(0,50,0,0);
                    layoutlogin.setVisibility(View.VISIBLE);
                }
            }
        });


        // LOGO ANIMATION0 END


    }
}

package com.a000webhostapp.desocialize.desocialize;

import android.Manifest;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.a000webhostapp.desocialize.desocialize.Premission.RPResultListener;
import com.a000webhostapp.desocialize.desocialize.Premission.RuntimePermissionUtil;
import com.google.android.gms.vision.text.Line;

import github.nisrulz.qreader.QRDataListener;
import github.nisrulz.qreader.QREader;

public class QrScanActivity extends AppCompatActivity {

    //Permission for camera
    private static final String camerPerm = Manifest.permission.CAMERA;

    boolean hasCameraPermission = true;

    //result - presented !


    private LinearLayout user_qr_done;
    private ImageView user_qr_img;
    private TextView user_qr_username, user_qr_points;
    private ImageButton user_approve;
    String result = "";

    // QR call ;
    private SurfaceView surfaceView;
    private QREader qrEader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscann);
        //Initialize

        user_qr_done = (LinearLayout) findViewById(R.id.user_qr_done);
        user_qr_img = (ImageView) findViewById(R.id.user_qr_img);
        user_qr_username = (TextView) findViewById(R.id.user_qr_username);
        user_qr_points = (TextView) findViewById(R.id.user_qr_points);
        user_approve = (ImageButton) findViewById(R.id.user_approve);

        // Setting the Permission;
        hasCameraPermission = RuntimePermissionUtil.checkPermissonGranted(this,camerPerm);

        final Button btnss = (Button) findViewById(R.id.buttonQr);

        btnss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartActivity();
            }
        });

        //Set up Surface and camera

        surfaceView = (SurfaceView) findViewById(R.id.cameraqr);
        if (hasCameraPermission){
            //Setup QReader
            setupQReader();
        } else {
            RuntimePermissionUtil.requestPermission(QrScanActivity.this, camerPerm, 100);
        }
        }

    /* Methods */

   public  void restartActivity() {
        startActivity(new Intent(this, QrScanActivity.class));
        finish();
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (hasCameraPermission) {
            // Cleanup in onPause()
          qrEader.releaseAndCleanup();

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Initialize

        user_qr_done = (LinearLayout) findViewById(R.id.user_qr_done);
        user_qr_img = (ImageView) findViewById(R.id.user_qr_img);
        user_qr_username = (TextView) findViewById(R.id.user_qr_username);
        user_qr_points = (TextView) findViewById(R.id.user_qr_points);
        user_approve = (ImageButton) findViewById(R.id.user_approve);

        if (hasCameraPermission) {
            // Init and Start with SurfaceView
            qrEader.initAndStart(surfaceView);

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        moveTaskToBack(true);


    }

    // Setting up the QReader
    public void  setupQReader(){
        qrEader = new QREader.Builder(this, surfaceView, new QRDataListener() {
            @Override
            public void onDetected(final String data) {

                user_qr_username.post(new Runnable() {
                    @Override
                    public void run() {
                        user_qr_done.setVisibility(View.VISIBLE);
                        user_approve.setVisibility(View.VISIBLE);

                        user_qr_username.setText(data);
                        qrEader.stop();
                    }
                });
                result = data+"";


            }

        }).facing(QREader.BACK_CAM)
                .enableAutofocus(true)
                .height(surfaceView.getHeight())
                .width(surfaceView.getWidth())
                .build();
    }

    public void approve (View view){
        Intent next = new Intent(QrScanActivity.this, Main2Activity.class);
        next.putExtra("QR-ID", result);
         startActivity(next);
         finish();
    }


    //----In this Activity we need to ask for CAM permission!

    public void onRequestPermissionsResult(int requestCode, final String[] permissions, final int[] grantResults){

        if (requestCode == 100){
            RuntimePermissionUtil.onRequestPermissionsResult(grantResults, new RPResultListener() {
                @Override
                public void onPermissionGranted() {
                    if (RuntimePermissionUtil.checkPermissonGranted(QrScanActivity.this, camerPerm)){
                    restartActivity();
                    }
                }

                @Override
                public void onPermissionDenied() {

                }
            });
        }
    }

}

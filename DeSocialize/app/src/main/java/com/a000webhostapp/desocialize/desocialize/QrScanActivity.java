package com.a000webhostapp.desocialize.desocialize;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.a000webhostapp.desocialize.desocialize.Premission.RPResultListener;
import com.a000webhostapp.desocialize.desocialize.Premission.RuntimePermissionUtil;

import github.nisrulz.qreader.QRDataListener;
import github.nisrulz.qreader.QREader;

public class QrScanActivity extends AppCompatActivity {

    //Permission for camera
    private static final String camerPerm = Manifest.permission.CAMERA;

    boolean hasCameraPermission = true;

    //result - presented int text view just for TEST!
    private TextView result;


    // QR call ;
    private SurfaceView surfaceView;
    private QREader qrEader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscann);

        // Setting the Permission;
        hasCameraPermission = RuntimePermissionUtil.checkPermissonGranted(this,camerPerm);

        //Initializing - FOR TESTING BUTTON !!
        result = (TextView) findViewById(R.id.qrtext);
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
            public void onDetected(String data) {
                result.setText(data);
                Intent next = new Intent(QrScanActivity.this, Main2Activity.class);
                next.putExtra("QR-ID", data + "");
                startActivity(next);
            }

        }).facing(QREader.BACK_CAM)
                .enableAutofocus(true)
                .height(surfaceView.getHeight())
                .width(surfaceView.getWidth())
                .build();
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

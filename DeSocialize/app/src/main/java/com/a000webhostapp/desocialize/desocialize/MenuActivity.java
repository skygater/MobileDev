package com.a000webhostapp.desocialize.desocialize;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.a000webhostapp.desocialize.desocialize.Task.Approve;
import com.a000webhostapp.desocialize.desocialize.Task.CheckLobby;
import com.a000webhostapp.desocialize.desocialize.Task.DeclineCall;
import com.a000webhostapp.desocialize.desocialize.Task.OnlineStatus;
import com.a000webhostapp.desocialize.desocialize.java.User;
import com.a000webhostapp.desocialize.desocialize.localdb.DatabaseHelper;
import com.a000webhostapp.desocialize.desocialize.service.NotificationJobScheduler;

public class MenuActivity extends AppCompatActivity {

    TextView txt;
    String responce = "Hello";
    ImageView imgQr;

    //DATA BASE
    //Locla DataBase
    private DatabaseHelper mLocalDb;
    private User u;

    //Services
    private  static final int JOB_ID = 101;
    private static final long REFRESH_INTERVAL  = 5 * 10;
    private static final long REFRESH_INTERVAL1  = 5 * 1000;
    private JobScheduler jobScheduler;
    private JobInfo jobInfo;


    /// Some info
    LinearLayout call;
    TextView message;
    int inviteUser = 0;


    private BroadcastReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        call = (LinearLayout) findViewById(R.id.call);
        message = (TextView) findViewById(R.id.message1);


        mLocalDb = new DatabaseHelper(this);
        u = mLocalDb.player();

        OnlineStatus onlineStatus = new OnlineStatus(this);
        onlineStatus.execute(u.getIdu(), 1);
        JobInfo.Builder builder;
        ComponentName componentName = new ComponentName(this,NotificationJobScheduler.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            builder = new JobInfo.Builder(JOB_ID, componentName);
            builder.setMinimumLatency(REFRESH_INTERVAL);
            builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
            builder.setPersisted(true);
        }else {
            builder = new JobInfo.Builder(JOB_ID, componentName);
            builder.setPeriodic(REFRESH_INTERVAL1);
            builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
            builder.setPersisted(true);

        }
        jobInfo = builder.build();
        jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(jobInfo);


       // Toast.makeText(this, "Job scheduled", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter intentFilter = new IntentFilter("android.intent.action.MAIN");
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //extract our message from intent
                String msg_for_me = intent.getStringExtra("some_msg");
                //log our message value
                call.setVisibility(View.VISIBLE);
                message.setText("Invitation to a game!");
              inviteUser = Integer.parseInt(msg_for_me);
                //Toast.makeText(MenuActivity.this, msg_for_me+"- I recived", Toast.LENGTH_SHORT).show();

            }
        };
        //registering our receiver
        this.registerReceiver(mReceiver, intentFilter);

    }

    @Override
    protected void onPause() {
        super.onPause();
        this.unregisterReceiver(this.mReceiver);
    }

    public void singleplayer (View view){
        jobScheduler.cancel(JOB_ID);
        Intent intent = new Intent(this, SingleplayerActivity.class);
        startActivity(intent);
        finish();
       // Toast.makeText(this, "Job canceled ", Toast.LENGTH_SHORT).show();
    }

  public void showqr (View view){
      Intent next  = new Intent(MenuActivity.this,QrShow.class);
      String qr = u.getQr();
      next.putExtra("qrid",qr);
      startActivity(next);
      finish();

  }

public void multiplayer (View view ){
    Intent next  = new Intent(MenuActivity.this,MultiplayerActivity.class);
    String qr = u.getQr();
    jobScheduler.cancel(JOB_ID);
   // Toast.makeText(this, "Job canceled ", Toast.LENGTH_SHORT).show();

    startActivity(next);
    finish();

}
    public void shop (View view){
        jobScheduler.cancel(JOB_ID);
        Intent intent = new Intent(this, ShopActivity.class);
        startActivity(intent);
        finish();
        //Toast.makeText(this, "Job canceled ", Toast.LENGTH_SHORT).show();
    }

    public void profile(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        //intent.putExtra("Profile ID", id);
        startActivity(intent);
    }

    public void approvecall (View view){
        call.setVisibility(View.GONE);

        Approve approve = new Approve(this);
        approve.execute(u.getIdu(),inviteUser);
        Intent intent = new Intent(this,GameRoomActivity.class);
        intent.putExtra("invitefrom",inviteUser);
        startActivity(intent);
        jobScheduler.cancel(JOB_ID);

        //Toast.makeText(this, "Job canceled ", Toast.LENGTH_SHORT).show();
    }

    public  void  declinecall (View view){
        //delete a call
       CheckLobby declineCall = new CheckLobby (this);
        call.setVisibility(View.GONE);
        declineCall.execute(u.getIdu(), inviteUser);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        OnlineStatus onlineStatus = new OnlineStatus(this);
        onlineStatus.execute(u.getIdu(), 0);
        jobScheduler.cancel(JOB_ID);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        jobScheduler.cancel(JOB_ID);
        OnlineStatus onlineStatus = new OnlineStatus(this);
        onlineStatus.execute(u.getIdu(), 0);
        jobScheduler.cancel(105);
    }
}

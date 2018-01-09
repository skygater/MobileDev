package com.a000webhostapp.desocialize.desocialize;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    private JobScheduler jobScheduler;
    private JobInfo jobInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mLocalDb = new DatabaseHelper(this);
        u = mLocalDb.player();

        OnlineStatus onlineStatus = new OnlineStatus(this);
        onlineStatus.execute(u.getIdu(), 1);

        ComponentName componentName = new ComponentName(this,NotificationJobScheduler.class);
        JobInfo.Builder  builder = new JobInfo.Builder(JOB_ID,componentName);
        builder.setPeriodic(5000);
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        builder.setPersisted(true);
        jobInfo = builder.build();
        jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(jobInfo);
        Toast.makeText(this, "Job scheduled", Toast.LENGTH_SHORT).show();

    }

    public void singleplayer (View view){
        jobScheduler.cancel(JOB_ID);
        Intent intent = new Intent(this, SingleplayerActivity.class);
        startActivity(intent);
        Toast.makeText(this, "Job canceled ", Toast.LENGTH_SHORT).show();
    }

    public void shop (View view){
        //jobScheduler.cancel(JOB_ID);
        Intent intent = new Intent(this, ShopActivity.class);
        startActivity(intent);
        //Toast.makeText(this, "Job canceled ", Toast.LENGTH_SHORT).show();
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
    Toast.makeText(this, "Job canceled ", Toast.LENGTH_SHORT).show();

    startActivity(next);
    finish();

}

public void profile (View view) {
    Intent next = new Intent( MenuActivity.this, ProfileActivity.class);
    startActivity(next);
    finish();
}


    /*@Override
    public void onBackPressed() {
        super.onBackPressed();
        OnlineStatus onlineStatus = new OnlineStatus(this);
        onlineStatus.execute(u.getIdu(), 0);

    }*/
}

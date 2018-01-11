package com.a000webhostapp.desocialize.desocialize;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.a000webhostapp.desocialize.desocialize.Task.OnlineStatus;
import com.a000webhostapp.desocialize.desocialize.Task.Restart;
import com.a000webhostapp.desocialize.desocialize.Task.SendUnlocked;
import com.a000webhostapp.desocialize.desocialize.adapters.LobbyAdapter;
import com.a000webhostapp.desocialize.desocialize.java.Result;
import com.a000webhostapp.desocialize.desocialize.java.User;
import com.a000webhostapp.desocialize.desocialize.localdb.DatabaseHelper;
import com.a000webhostapp.desocialize.desocialize.service.GameService;
import com.a000webhostapp.desocialize.desocialize.service.PendingJobScheduler;
import com.a000webhostapp.desocialize.desocialize.service.PhoneUnlockedReceiver;

import org.w3c.dom.Text;

import java.util.Calendar;


public class GameRoomActivity extends AppCompatActivity {
    private  static final int JOB_ID = 105;
    private static final long REFRESH_INTERVAL  = 5 * 10;
    private static final long REFRESH_INTERVAL1  = 5 * 10;
    private JobScheduler jobScheduler;
    private JobInfo jobInfo;
    BroadcastReceiver checkStart;
    BroadcastReceiver checkStart1;
    int startYes;

    ProgressBar progress;

    //VIEW
    TextView startmsg;
    Result lastResult = Result.LOSS;
    View resultOverlay;
    View timeRemainingView;
    TextView resultTV;


    String winLose = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_room);
        startmsg = (TextView) findViewById(R.id.textstart);
        progress = (ProgressBar) findViewById(R.id.progressbar33);
        JobInfo.Builder builder;
        ComponentName componentName = new ComponentName(this,GameService.class);
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

        Toast.makeText(this, "Job scheduled", Toast.LENGTH_SHORT).show();

        resultTV = findViewById(R.id.resultTV);
        resultOverlay = findViewById(R.id.resultOverlay);
        timeRemainingView = findViewById(R.id.timeRemainingLayout);
        Resources resources = getResources();

        // setup phone unlock receiver
        PhoneUnlockedReceiver.register(this, new GameRoomActivity.SPUnlock());


    }

    public void restartgame (View view){
        Restart restart = new Restart(this);
        restart.execute(startYes);
        Intent intent = new Intent(this, MenuActivity.class);
        jobScheduler.cancel(JOB_ID);
        startActivity(intent);
        finish();
    }

    private void win(){
        startmsg.setVisibility(View.GONE);

        if(lastResult.equals(Result.LOSS)){
            startmsg.setVisibility(View.GONE);
            resultTV.setText(R.string.youWin);
            resultOverlay.setBackgroundColor(getResources().getColor(R.color.colorWin));
            timeRemainingView.setVisibility(View.INVISIBLE);
        }

        lastResult = Result.WIN;
        findViewById(R.id.resultOverlay).setVisibility(View.VISIBLE);
    }

    private void loss(){
        startmsg.setVisibility(View.GONE);

        if(lastResult.equals(Result.WIN)){
            resultTV.setText(R.string.youLost);
            resultOverlay.setBackgroundColor(getResources().getColor(R.color.colorLoss));
            timeRemainingView.setVisibility(View.VISIBLE);
        }

        lastResult = Result.LOSS;
        findViewById(R.id.resultOverlay).setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (winLose.equalsIgnoreCase("won")){
            win();
            jobScheduler.cancel(JOB_ID);
        }else
        if (winLose.equalsIgnoreCase("lost")){
            loss();
            jobScheduler.cancel(JOB_ID);

        }

        IntentFilter intentFilter = new IntentFilter("com.a000webhostapp.desocialize.desocialize.gamestart");
        checkStart = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {


                startYes = intent.getIntExtra("start",0);
                if (startYes != 0) {
                   /* WindowManager.LayoutParams params = getWindow().getAttributes();
                    params.flags |= WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
                    params.screenBrightness = -1;
                    getWindow().setAttributes(params);*/
                    startmsg.setText("CLick the power button to start - play with the group " + startYes);

                }

                }


        };
        //registering our receiver
        this.registerReceiver(checkStart, intentFilter);


    }

    @Override
    protected void onPause() {
        super.onPause();
        GameRoomActivity.this.unregisterReceiver(GameRoomActivity.this.checkStart);
    }

    public void  helpme (View view){
        Restart restart = new Restart(this);
        restart.execute(startYes);
        Intent intent = new Intent(this, MenuActivity.class);
        jobScheduler.cancel(JOB_ID);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Restart restart = new Restart(this);
        restart.execute(startYes);
        Intent intent = new Intent(this, MenuActivity.class);
        jobScheduler.cancel(JOB_ID);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        jobScheduler.cancel(JOB_ID);

    }

    private class SPUnlock extends PhoneUnlockedReceiver {
        /** start time + duration = final time
         * tells us after what time the user won */
        Calendar finalTime;
        User u;
        DatabaseHelper databaseHelper;

        @Override
        public void screenUnlocked() {

            CountDownTimer mCountDownTimer;
            final int[] i = {0};
            progress.setProgress(i[0]);
            progress.setVisibility(View.VISIBLE);
            mCountDownTimer=new CountDownTimer(6000,5000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    Log.v("Log_tag", "Tick of Progress" + i[0] + millisUntilFinished);
                    i[0]++;
                    progress.setProgress(i[0]);
                }

                @Override
                public void onFinish() {


                    progress.setVisibility(View.GONE);
                    if (winLose.equalsIgnoreCase("won")){
                        win();
                        jobScheduler.cancel(JOB_ID);
                    }else {
                        SendUnlocked sendUnlocked = new SendUnlocked(GameRoomActivity.this);
                        databaseHelper = new DatabaseHelper(GameRoomActivity.this);
                        u = databaseHelper.player();
                        sendUnlocked.execute(u.getIdu());
                        loss();
                        jobScheduler.cancel(JOB_ID);
                    }

                }



            };
            mCountDownTimer.start();





        }

        @Override
        public void screenLocked() {
            IntentFilter intentFilter1 = new IntentFilter("com.a000webhostapp.desocialize.desocialize.unlockcheck");
            checkStart1 = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                    winLose = intent.getStringExtra("result");
                    Toast.makeText(GameRoomActivity.this, winLose, Toast.LENGTH_SHORT).show();

                }


            };
            //registering our receiver
            GameRoomActivity.this.registerReceiver(checkStart1, intentFilter1);


        }
    }
}

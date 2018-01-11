package com.a000webhostapp.desocialize.desocialize;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.a000webhostapp.desocialize.desocialize.Task.AddLoby;
import com.a000webhostapp.desocialize.desocialize.Task.AddToLobby;
import com.a000webhostapp.desocialize.desocialize.Task.Restart;
import com.a000webhostapp.desocialize.desocialize.adapters.LobbyAdapter;
import com.a000webhostapp.desocialize.desocialize.java.FriendsOnline;
import com.a000webhostapp.desocialize.desocialize.java.User;
import com.a000webhostapp.desocialize.desocialize.localdb.DatabaseHelper;
import com.a000webhostapp.desocialize.desocialize.service.NotificationJobScheduler;
import com.a000webhostapp.desocialize.desocialize.service.PendingJobScheduler;

import java.util.ArrayList;
import java.util.List;

public class LobbyActivity extends AppCompatActivity {
    ListView listview;
    List<FriendsOnline> list;

    LayoutInflater inflater;
    List<String> forScroll;
    LobbyAdapter lobbyAdapter;
    Integer isModeSelected = 0;
    AddToLobby addToLobby ;

    public Integer[] users;

    private DatabaseHelper mLocalDb;
    private User u;

    //Services
    private  static final int JOB_ID = 103;
    private static final long REFRESH_INTERVAL  = 5 * 10;
    private static final long REFRESH_INTERVAL1  = 5 * 10;
    private JobScheduler jobScheduler;
    private JobInfo jobInfo;

    private BroadcastReceiver mReceiver;
    int [] players;
    int [] listdecline;
    private BroadcastReceiver declineReciver;

    Button start;

    int gametype = 1;
    ProgressBar p ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        p = (ProgressBar) findViewById(R.id.progressBar4);
        p.setVisibility(View.GONE);
        start = (Button) findViewById(R.id.lobby_start);
        list = new ArrayList<>();
        list = (List<FriendsOnline>) getIntent().getSerializableExtra("selectedList");
        forScroll = new ArrayList<>();
        users = new Integer[list.size()];


        for (int i = 0; i <list.size() ; i++) {
             users[i] = list.get(i).getIdpy();
        }


        mLocalDb = new DatabaseHelper(this);
        u = mLocalDb.player();

        addToLobby = new AddToLobby(this, u);
        addToLobby.execute(users);

        JobInfo.Builder builder;
        ComponentName componentName = new ComponentName(this,PendingJobScheduler.class);
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

        inflater = this.getLayoutInflater();
        listview = findViewById(R.id.lobby_list);
        lobbyAdapter = new LobbyAdapter(this,list);
        listview.setAdapter(lobbyAdapter);

        findViewById(R.id.nav_back_lobby).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });

        findViewById(R.id.lobby_mode_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isModeSelected!=2) {
                    isModeSelected = 2; // toggle the boolean flag
                    findViewById(R.id.lobby_mode_first).setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.de_green));
                    findViewById(R.id.lobby_mode_two).setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(),R.color.de_grey));
                }
                else if (isModeSelected==2) {
                    isModeSelected = 0;
                    findViewById(R.id.lobby_mode_first).setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.colorPrimary));
                    findViewById(R.id.lobby_mode_two).setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(),R.color.colorPrimary));

                }
            }
        });

        findViewById(R.id.lobby_mode_two).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                gametype = 1;

                if (isModeSelected!=1) {
                    isModeSelected = 1; // toggle the boolean flag
                    findViewById(R.id.lobby_mode_first).setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(),R.color.de_grey));
                    findViewById(R.id.lobby_mode_two).setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(),R.color.de_green));
                }
                else if (isModeSelected==1) {
                    isModeSelected = 0;
                    findViewById(R.id.lobby_mode_first).setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.colorPrimary));
                    findViewById(R.id.lobby_mode_two).setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(),R.color.colorPrimary));

                }
            }
        });

        start.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.colorPrimary));


    }

    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter intentFilter = new IntentFilter("com.a000webhostapp.desocialize.desocialize.checkStatus");
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //extract our message from intent

                //log our message value
                players = intent.getIntArrayExtra("approved");

                if (players == null) {
                   // Toast.makeText(LobbyActivity.this, "Nothing", Toast.LENGTH_SHORT).show();
                } else if (players.length == 0) {
                   // Toast.makeText(LobbyActivity.this, "Nothing", Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = 0; i < players.length; i++) {
                        if (players[i] == list.get(i).getIdpy()) {
                            list.get(i).setAccept(1);
                        }
                        inflater = LobbyActivity.this.getLayoutInflater();
                        listview = findViewById(R.id.lobby_list);
                        lobbyAdapter = new LobbyAdapter(LobbyActivity.this, list);
                        listview.setAdapter(lobbyAdapter);
                    }


                    //Toast.makeText(LobbyActivity.this, players[0] + "- I recived", Toast.LENGTH_SHORT).show();
                }

            }
        };
        //registering our receiver
        this.registerReceiver(mReceiver, intentFilter);

        IntentFilter intentFilter1 = new IntentFilter("com.a000webhostapp.desocialize.desocialize.checkDeclaind");
        declineReciver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                listdecline = intent.getIntArrayExtra("pending");

                if (listdecline == null) {
                   // Toast.makeText(LobbyActivity.this, "ERROR in app", Toast.LENGTH_SHORT).show();
                } else if (listdecline.length == 0) {
                    Toast.makeText(LobbyActivity.this, "No players wants to play", Toast.LENGTH_SHORT).show();
                    //list.remove(0);
                } else {
                    for (int i = 0; i < listdecline.length; i++) {
                        if (listdecline[i] != list.get(i).getIdpy()) {
                            list.remove(i);
                        }

                        inflater = LobbyActivity.this.getLayoutInflater();
                        listview = findViewById(R.id.lobby_list);
                        lobbyAdapter = new LobbyAdapter(LobbyActivity.this, list);
                        listview.setAdapter(lobbyAdapter);
                    }
                }

            }
        };
        this.registerReceiver(declineReciver,intentFilter1);

    }

    @Override
    protected void onPause() {
        super.onPause();
        this.unregisterReceiver(this.mReceiver);
        this.unregisterReceiver(this.declineReciver);
    }

    public void back(){
        Restart restart = new Restart(this);
        restart.execute(u.getIdu());
        Intent intent = new Intent(this, MenuActivity.class);
        jobScheduler.cancel(JOB_ID);
        startActivity(intent);
        finish();;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Restart restart = new Restart(this);
        restart.execute(u.getIdu());
        Intent intent = new Intent(this, MenuActivity.class);
        jobScheduler.cancel(JOB_ID);
        startActivity(intent);
        finish();
    }

    public void start_game(View view){

        int pass = 0;
        for (int i = 0; i <list.size() ; i++) {
            if (list.get(i).accept == 1){
                pass = 1;
            }else{
                pass = 0;
            }
        }
        if (pass == 1){

            AddLoby addLoby = new AddLoby(this,u.getIdu(),gametype);
            addLoby.execute("Lobby"+gametype);
            CountDownTimer mCountDownTimer;
            final int[] i = {0};
            p.setProgress(i[0]);
            p.setVisibility(View.VISIBLE);
            mCountDownTimer=new CountDownTimer(4000,3000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    Log.v("Log_tag", "Tick of Progress"+ i[0] + millisUntilFinished);
                    i[0]++;
                    p.setProgress(i[0]);
                }
                @Override
                public void onFinish() {
                    jobScheduler.cancel(JOB_ID);
                    Intent intent = new Intent(LobbyActivity.this, GameRoomActivity.class);
                    startActivity(intent);
                    finish();

                    }

            };
            mCountDownTimer.start();

        }else{
            Toast.makeText(this, "Need to wait for all players ", Toast.LENGTH_SHORT).show();
        }

    }
}

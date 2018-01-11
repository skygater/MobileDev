package com.a000webhostapp.desocialize.desocialize.service;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.a000webhostapp.desocialize.desocialize.Task.CheckUnlock;
import com.a000webhostapp.desocialize.desocialize.Task.GameStart;
import com.a000webhostapp.desocialize.desocialize.java.CheckList;
import com.a000webhostapp.desocialize.desocialize.java.User;
import com.a000webhostapp.desocialize.desocialize.localdb.DatabaseHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by djordjekalezic on 10/01/2018.
 */

public class GameService extends JobService {

    JSONObject jsonObject;
    JSONArray jsonArray;
    User u;
    DatabaseHelper databaseHelper;

   // Tasks

    GameStart gameStart;
    CheckUnlock checkUnlock;

    List<CheckList> checkLists;


    @Override
    public boolean onStartJob(final JobParameters params) {
        checkLists = new ArrayList<>();
        databaseHelper = new DatabaseHelper(this);
        u = databaseHelper.player();
        gameStart = new GameStart(this){
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JSON_String = s;
                String username, email, password, qr, imgp;
                int groupgame = 0;

                try {
                    jsonObject = new JSONObject(JSON_String);
                    jsonArray = jsonObject.getJSONArray("server_responce");
                    int count = 0;
                    while (count < jsonArray.length()) {
                        JSONObject jo = jsonArray.getJSONObject(count);
                        groupgame = jo.getInt("group");
                        count++;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (jsonArray != null ) {
                 // Toast.makeText(GameService.this, groupgame+"", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent("com.a000webhostapp.desocialize.desocialize.gamestart").putExtra("start", groupgame);
                    sendBroadcast(i);
                }


                jobFinished(params, false);
            }
        };

        checkUnlock = new CheckUnlock(this){

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JSON_String = s;
                int idu, checkunlock;
                try {
                    jsonObject = new JSONObject(JSON_String);
                    jsonArray = jsonObject.getJSONArray("server_responce");
                    int count = 0;
                    CheckList c = null;
                    while (count < jsonArray.length()) {
                        JSONObject jo = jsonArray.getJSONObject(count);
                        idu = jo.getInt("iduser");
                        checkunlock = jo.getInt("unlockstatus");
                        c = new CheckList(idu, checkunlock);
                        checkLists.add(c);
                        count++;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i <checkLists.size() ; i++) {
                    if (checkLists.get(i).getCheckNo() == 1) {
                        if (checkLists.get(i).getUser() == u.getIdu()) {
                            Intent i1 = new Intent("com.a000webhostapp.desocialize.desocialize.unlockcheck").putExtra("result", "lost");
                            sendBroadcast(i1);
                        }
                    } else if (checkLists.get(i).getCheckNo() == 2) {
                        if (checkLists.get(i).getUser() == u.getIdu()) {
                            Intent i1 = new Intent("com.a000webhostapp.desocialize.desocialize.unlockcheck").putExtra("result", "won");
                            sendBroadcast(i1);
                        }
                    }
                }

                if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    scheduleRefresh();
                }
                jobFinished(params, false);
            }
        };


        gameStart.execute(u.getIdu());
        checkUnlock.execute(u.getIdu());

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {

        gameStart.cancel(true);
        checkUnlock.cancel(true);
        return false;
    }

    private void scheduleRefresh() {
        JobScheduler mJobScheduler = (JobScheduler) getApplicationContext()
                .getSystemService(JOB_SCHEDULER_SERVICE);
        JobInfo.Builder mJobBuilder =
                new JobInfo.Builder(105,
                        new ComponentName(getPackageName(),
                                GameService.class.getName()));

  /* For Android N and Upper Versions */
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mJobBuilder
                    .setMinimumLatency(6 * 1000) //TIME_INTERVAL
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        }


        if (mJobScheduler != null && mJobScheduler.schedule(mJobBuilder.build())
                <= JobScheduler.RESULT_FAILURE) {
            //Scheduled Failed/LOG or run fail safe measures
            Log.d("TAG", "Unable to schedule the service!");
        }


    }
}

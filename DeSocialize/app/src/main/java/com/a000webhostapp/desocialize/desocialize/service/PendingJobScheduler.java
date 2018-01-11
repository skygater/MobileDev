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

import com.a000webhostapp.desocialize.desocialize.Task.DeclineCall;
import com.a000webhostapp.desocialize.desocialize.Task.NotificationExecute;
import com.a000webhostapp.desocialize.desocialize.Task.PendingExecute;
import com.a000webhostapp.desocialize.desocialize.java.User;
import com.a000webhostapp.desocialize.desocialize.localdb.DatabaseHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by djordjekalezic on 10/01/2018.
 */

public class PendingJobScheduler extends JobService{

    private PendingExecute pendingExecute;
    private  DeclineCall declineCall;
    JSONObject jsonObject;
    JSONArray jsonArray;
    User u;
    DatabaseHelper databaseHelper;
    int [] players = null;
    int [] decline = null;


    @Override
    public boolean onStartJob(final JobParameters params){

        pendingExecute = new PendingExecute() {
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JSON_String = s;
                int [] array;
                int idu = 2;
                // String test = "Not invited";
                int invite = 0;
                try {
                    jsonObject = new JSONObject(JSON_String);
                    jsonArray = jsonObject.getJSONArray("server_responce");
                    int count = 0;
                    array = new int[jsonArray.length()];
                    while (count < jsonArray.length()) {
                        JSONObject jo = jsonArray.getJSONObject(count);
                        idu = jo.getInt("approved");
                        array[count] = idu;
                        count++;
                    }
                    players = array;

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                    if (players != null){
                    Intent i = new Intent("com.a000webhostapp.desocialize.desocialize.checkStatus").putExtra("approved", players);
                    sendBroadcast(i);
                    }

               // Toast.makeText(PendingJobScheduler.this, idu+"", Toast.LENGTH_SHORT).show();
                if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    scheduleRefresh();
                }

                jobFinished(params, false);

            }
        };

      declineCall = new DeclineCall(){

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JSON_String = s;
                int [] array;
                int idu = 0;
                try {
                    jsonObject = new JSONObject(JSON_String);
                    jsonArray = jsonObject.getJSONArray("server_responce");
                    int count = 0;
                    array = new int[jsonArray.length()];
                    while (count < jsonArray.length()) {
                        JSONObject jo = jsonArray.getJSONObject(count);
                        idu = jo.getInt("pending");
                        array[count] = idu;
                        count++;
                    }
                    decline = array;

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (decline != null){
                    Intent i = new Intent("com.a000webhostapp.desocialize.desocialize.checkDeclaind").putExtra("pending", decline);
                    sendBroadcast(i);
                }

                // Toast.makeText(PendingJobScheduler.this, idu+"", Toast.LENGTH_SHORT).show();
                if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    scheduleRefresh();
                }

                jobFinished(params, false);
            }
        };

        databaseHelper = new DatabaseHelper(this);
        u = databaseHelper.player();
        pendingExecute.execute(u.getIdu());
     declineCall.execute(u.getIdu());


        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        pendingExecute.cancel(true);
        declineCall.cancel(true);
        return false;
    }
    private void scheduleRefresh() {
        JobScheduler mJobScheduler = (JobScheduler) getApplicationContext()
                .getSystemService(JOB_SCHEDULER_SERVICE);
        JobInfo.Builder mJobBuilder =
                new JobInfo.Builder(103,
                        new ComponentName(getPackageName(),
                                PendingJobScheduler.class.getName()));

  /* For Android N and Upper Versions */
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mJobBuilder
                    .setMinimumLatency(6 * 1000) //YOUR_TIME_INTERVAL
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        }


        if (mJobScheduler != null && mJobScheduler.schedule(mJobBuilder.build())
                <= JobScheduler.RESULT_FAILURE) {
            //Scheduled Failed/LOG or run fail safe measures
            Log.d("TAG", "Unable to schedule the service!");
        }


    }
}

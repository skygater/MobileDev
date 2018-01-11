package com.a000webhostapp.desocialize.desocialize.service;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.a000webhostapp.desocialize.desocialize.Task.NotificationExecute;
import com.a000webhostapp.desocialize.desocialize.java.User;
import com.a000webhostapp.desocialize.desocialize.localdb.DatabaseHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by djordjekalezic on 07/01/2018.
 */

public class NotificationJobScheduler extends JobService {
    private NotificationExecute notificationExecute;
    JSONObject jsonObject;
    JSONArray jsonArray;
    User u;
    DatabaseHelper databaseHelper;

    @Override
    public boolean onStartJob(final JobParameters params) {
        notificationExecute = new NotificationExecute() {
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JSON_String = s;
                String username, email, password, qr, imgp;
                int idu = 2;
               // String test = "Not invited";
                int invite = 0;
                try {
                    jsonObject = new JSONObject(JSON_String);
                    jsonArray = jsonObject.getJSONArray("server_responce");
                    int count = 0;
                    while (count < jsonArray.length()) {
                        JSONObject jo = jsonArray.getJSONObject(count);
                        idu = jo.getInt("accstatus");
                        invite = jo.getInt("invite");
                        //Toast.makeText(MJobScheduler.this, "LOL", Toast.LENGTH_SHORT).show();
                        count++;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (idu == 0) {
                    //Toast.makeText(NotificationJobScheduler.this, invite+"", Toast.LENGTH_SHORT).show();
                Intent i = new Intent("android.intent.action.MAIN").putExtra("some_msg", invite+"");
                sendBroadcast(i);
                }


                if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    scheduleRefresh();
                }

                jobFinished(params, false);

            }
        };
        databaseHelper = new DatabaseHelper(this);
        u = databaseHelper.player();
        notificationExecute.execute(u.getIdu());


        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        notificationExecute.cancel(true);
        return false;
    }

    private void scheduleRefresh() {
        JobScheduler mJobScheduler = (JobScheduler) getApplicationContext()
                .getSystemService(JOB_SCHEDULER_SERVICE);
        JobInfo.Builder mJobBuilder =
                new JobInfo.Builder(101,
                        new ComponentName(getPackageName(),
                                NotificationJobScheduler.class.getName()));

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

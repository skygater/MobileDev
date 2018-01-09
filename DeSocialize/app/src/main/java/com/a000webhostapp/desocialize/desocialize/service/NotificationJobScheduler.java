package com.a000webhostapp.desocialize.desocialize.service;

import android.app.job.JobParameters;
import android.app.job.JobService;
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
        notificationExecute = new NotificationExecute(){
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JSON_String= s ;
                String username, email , password, qr, imgp;
                int idu;
                String test = "Nije pozvan";
                try {
                    jsonObject = new JSONObject(JSON_String);
                    jsonArray = jsonObject.getJSONArray("server_responce");
                    int count = 0;
                    while(count < jsonArray.length()){
                        JSONObject jo = jsonArray.getJSONObject(count);
                        idu = jo.getInt("accstatus");
                        if (idu == 0){
                            test = "Pozvan";
                        }
                        //Toast.makeText(MJobScheduler.this, "LOL", Toast.LENGTH_SHORT).show();
                        count ++;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(NotificationJobScheduler.this, test, Toast.LENGTH_SHORT).show();
                jobFinished(params,false);

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


}

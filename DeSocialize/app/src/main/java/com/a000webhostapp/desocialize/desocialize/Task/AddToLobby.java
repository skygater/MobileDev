package com.a000webhostapp.desocialize.desocialize.Task;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by djordjekalezic on 09/01/2018.
 */

public class AddToLobby extends AsyncTask <Integer, Void, Integer> {

    Context mContext;

   public AddToLobby ( Context ctx){
       this.mContext  = ctx;
   }


    @Override
    protected Integer doInBackground(Integer... values) {

        String reg_url = "https://desocialize.000webhostapp.com/isonline.php";

        int user = values[0];
        int online= values[1];
        try
        {
        JSONObject jArrayFacebookData = new JSONObject();
        JSONObject jObjectData = new JSONObject();

            for (int i = 0; i < values.length; i++) {

                jObjectData.put("user"+i , values[i]);

            }

        jArrayFacebookData.put("players",jObjectData);



            URL url = new URL(reg_url);
            HttpURLConnection httpURLConnection =(HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
            String  data = URLEncoder.encode("user","UTF-8") + "=" + URLEncoder.encode(user+"","UTF-8") +"&"+
                    URLEncoder.encode("online", "UTF-8")+"="+URLEncoder.encode(online+"", "UTF-8");
            bufferedWriter.write(data);

            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            InputStream inputStream = httpURLConnection.getInputStream();
            inputStream.close();
            return 1;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return 0;




    }
}

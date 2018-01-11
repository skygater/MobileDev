package com.a000webhostapp.desocialize.desocialize.Task;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.a000webhostapp.desocialize.desocialize.java.User;

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

public class AddToLobby extends AsyncTask <Integer, Void, String> {

    Context mContext;
    User user;

   public AddToLobby ( Context ctx, User u ){

       this.mContext  = ctx;
       this.user = u;
   }


    @Override
    protected String doInBackground(Integer... values) {

        String reg_url = "https://desocialize.000webhostapp.com/addtolobby.php";
        String users ="NISTA";
        int u = user.getIdu();
        try
        {

            JSONObject players = new JSONObject();

            JSONObject jObjectData = new JSONObject();

            for (int i = 0; i < values.length; i++) {

                jObjectData.put("user"+i , values[i]+"");
            }
            players.put("data",jObjectData);

             users = players.toString();

           // Toast.makeText(mContext, users, Toast.LENGTH_SHORT).show();
            URL url = new URL(reg_url);
            HttpURLConnection httpURLConnection =(HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
            String  data = URLEncoder.encode("players","UTF-8") + "=" + URLEncoder.encode(players.toString(),"UTF-8")+"&"+
                    URLEncoder.encode("invite", "UTF-8")+"="+URLEncoder.encode(u+"", "UTF-8");
            bufferedWriter.write(data);

            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            InputStream inputStream = httpURLConnection.getInputStream();
            inputStream.close();
            return users;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return users;




    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Toast.makeText(mContext, s, Toast.LENGTH_LONG).show();
    }
}

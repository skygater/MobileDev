package com.a000webhostapp.desocialize.desocialize.Task;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

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
 * Created by djordjekalezic on 10/01/2018.
 */

public class AddLoby extends AsyncTask<String,Integer,String> {
    Context ctx;
    int invitefrom;
    int type;


    public AddLoby(Context ctx, int invitefrom, int type) {
        this.ctx = ctx;
        this.invitefrom = invitefrom;
        this.type = type;

    }

    @Override
    protected String doInBackground(String... values) {
        String reg_url = "https://desocialize.000webhostapp.com/addLobby.php";

        String lobbycode = values[0];

        try {
            URL url = new URL(reg_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String data = URLEncoder.encode("idg", "UTF-8") + "=" + URLEncoder.encode(type + "", "UTF-8") + "&" +
                    URLEncoder.encode("group", "UTF-8") + "=" + URLEncoder.encode(invitefrom + "", "UTF-8") + "&" +
                    URLEncoder.encode("lobby", "UTF-8") + "=" + URLEncoder.encode(lobbycode, "UTF-8");
            bufferedWriter.write(data);

            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            InputStream inputStream = httpURLConnection.getInputStream();
            inputStream.close();


            return "Lobby succesfull!";
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }



    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();

    }
}
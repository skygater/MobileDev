package com.a000webhostapp.desocialize.desocialize.Task;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by djordjekalezic on 07/01/2018.
 */

public class NotificationExecute extends AsyncTask<Integer,Void,String> {

    String json_url ="https://desocialize.000webhostapp.com/statusupdate.php";
    public String JSON_String = "";

    @Override
    protected String doInBackground(Integer... values) {
          /* Background Task for geting a JSON file */
        StringBuilder stringBuilder = new StringBuilder();
        int user = values[0];

        try {

            URL url = new URL(json_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
            String  data = URLEncoder.encode("user","UTF-8") + "=" + URLEncoder.encode(user+"","UTF-8");
            bufferedWriter.write(data);

            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();


            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            while ((JSON_String = bufferedReader.readLine()) != null){
                stringBuilder.append(JSON_String);
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString().trim();
    }
}

package com.a000webhostapp.desocialize.desocialize;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.a000webhostapp.desocialize.desocialize.Task.RegisterTask;
import com.a000webhostapp.desocialize.desocialize.java.User;
import com.a000webhostapp.desocialize.desocialize.localdb.DatabaseHelper;
import com.jaredrummler.android.widget.AnimatedSvgView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    //For Login UsernameAndPass
    String username, pass;
    List<User> users;

    //Locla DataBase
    private  DatabaseHelper mLocalDb;
    //animation var
    AnimatedSvgView svgView;
    int index = -1;
    Animation shake;
    //Layout var
    LinearLayout lh1 ;
    LinearLayout layoutlogin;
    LinearLayout layoutregister;
    LinearLayout layoutadd;
    LinearLayout log_reg;

    //Registration add info
    EditText add_usernam,add_email,add_pass1;
    EditText login_username, login_pass;
    Button btnreg;
    Button btn_login;
    ProgressBar mProgressBar;
    ProgressBar mProgressBar1;

    //JSON users
    String JSON_String = "";
    String JSON_data;
    JSONObject jsonObject;
    JSONArray jsonArray;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialization
        svgView = (AnimatedSvgView) findViewById(R.id.animated_svg_view);
        shake = AnimationUtils.loadAnimation(this, R.anim.shake);

        mProgressBar=(ProgressBar)findViewById(R.id.progressBar1);
        lh1 = (LinearLayout) findViewById(R.id.layouth1);
        layoutlogin  = (LinearLayout) findViewById(R.id.layoutlogin);
        layoutregister = (LinearLayout) findViewById(R.id.layoutregister);
        login_username = (EditText) findViewById(R.id.login_username);
        login_pass = (EditText) findViewById(R.id.login_pass);
        btnreg = (Button) findViewById(R.id.btnreg);
        log_reg = (LinearLayout) findViewById(R.id.log_reg);
        mProgressBar1 = (ProgressBar) findViewById(R.id.progressBar2);
        btn_login = (Button) findViewById(R.id.btn_login);

        users = new ArrayList<>();

        // Get JSON
        new BackgroundTask().execute();

        //Local Data Base Check
        mLocalDb = new DatabaseHelper(this);
        File database  = getApplicationContext().getDatabasePath(DatabaseHelper.DBNAME);
        if (false == database.exists()){

            mLocalDb.getReadableDatabase();
            //Copy DB
            if (copyDataBase(this)){
                Toast.makeText(this,"Copy database scusses",Toast.LENGTH_SHORT ).show();

            }else{
                Toast.makeText(this,"Copy database error",Toast.LENGTH_SHORT ).show();
                return;
            }
        }

        // LOGO ANIMATION0
        svgView.postDelayed(new Runnable() {
            @Override public void run() {
                svgView.start();
            }
        }, 500);

        svgView.setOnStateChangeListener(new AnimatedSvgView.OnStateChangeListener() {

            @Override public void onStateChange(@AnimatedSvgView.State int state) {
                /* Check LOCAL DB!
               if (state == AnimatedSvgView.STATE_TRACE_STARTED){

               }else*/
                if (state == AnimatedSvgView.STATE_FINISHED) {
                    if (mLocalDb.isRegistrated()){
                        Intent next  = new Intent(MainActivity.this,QrScanActivity.class);
                        startActivity(next);
                        finish();
                    }else {
                        lh1.setGravity(0);
                        lh1.setPadding(0, 50, 0, 0);
                        layoutlogin.setVisibility(View.VISIBLE);
                        layoutregister.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        // LOGO ANIMATION0 END
    }


    // Methods onClick;



    /* Login User */
    public void login (View view){

        //HIDE KEYBORD ON LOGIN
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

        int passCheck = 0;

        if (isNetworkAvailable()){
            //Toast.makeText(this, "Yes there is network", Toast.LENGTH_SHORT).show();
            login_username = (EditText) findViewById(R.id.login_username);
            login_pass = (EditText) findViewById(R.id.login_pass);
           // Check Insert;
            if (login_username.getText().toString() == null || login_username.getText().toString().equals("")){
                login_username.setText("");
                login_username.setHintTextColor(0xFFe74c3c);
                passCheck = 1;
            }
            if (login_pass.getText().toString() == null || login_pass.getText().toString().equals("")){
                login_pass.setText("");
                login_pass.setHintTextColor(0xFFe74c3c);
                passCheck = 1;

            }
            // Check Insert PASS OR FAIL
            if (passCheck==0){
                String username = login_username.getText().toString();
                String pass = login_pass.getText().toString();
                // Check users PASS AND USERNAME/EMAIL
                for (User u : users) {

                    if (username.equals(u.getUsername()) || username.equals(u.getEmail())){
                        if (pass.equals(u.getPassword())){
                            //Adding to local DB;
                            mLocalDb.insertPlayer(mLocalDb,u.getUsername(),u.getEmail(),u.getIdu(),u.getPassword());
                            Intent homePage = new Intent(this, QrScanActivity.class);
                            startActivity(homePage);
                            finish();
                            break;
                        }else{
                            login_pass.setText("");
                            login_pass.setHint("Password");
                            login_pass.setHintTextColor(0xFFe74c3c);
                        }
                    }else{
                        login_pass.setText("");
                        login_pass.setHintTextColor(0xFFe74c3c);
                        login_username.setText("");
                        login_username.setHint("Username or Email");
                        login_username.setHintTextColor(0xFFe74c3c);
                        btnreg.startAnimation(shake);
                    }
                }
                // FAIL
            }else{
                Toast.makeText(this, "Plese enter Username/Email and Password", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this, "Please connect to internet!", Toast.LENGTH_SHORT).show();
        }
    }

    /* User go to registration */
    public void register(View view){
        if (isNetworkAvailable()){
            layoutadd = (LinearLayout) findViewById(R.id.layoutaddinfo);
            layoutlogin.setVisibility(View.GONE);
            layoutregister.setVisibility(View.GONE);
            layoutadd.setVisibility(View.VISIBLE);
        }else{
            Toast.makeText(this, "Please connect to internet!", Toast.LENGTH_SHORT).show();
        }
    }
    /* User go back to login  */
    public void backLogin(View view){

            layoutadd = (LinearLayout) findViewById(R.id.layoutaddinfo);
            layoutlogin.setVisibility(View.VISIBLE);
            layoutregister.setVisibility(View.VISIBLE);
            layoutadd.setVisibility(View.GONE);

    }


    /* User register info */
    public void addUser (View view){
        //add values to username, email, pass1;
        final String username,email,pass;
        add_usernam = (EditText) findViewById(R.id.add_username);
        username = add_usernam.getText().toString();
        add_email = (EditText) findViewById(R.id.add_email);
        email = add_email.getText().toString();
        add_pass1 = (EditText) findViewById(R.id.add_pass1);
        pass = add_pass1.getText().toString();

        //Preforming ceheck
        int checkreg = registrationCheck();

        if(checkreg == 0){
            //Correct!
            if (isNetworkAvailable()) {
                RegisterTask registerTask = new RegisterTask(this,mProgressBar);
                registerTask.execute(username,email,pass);
                CountDownTimer mCountDownTimer;
                final int[] i = {0};
                mProgressBar.setProgress(i[0]);
                mProgressBar.setVisibility(View.VISIBLE);
                mCountDownTimer=new CountDownTimer(6000,5000) {

                    @Override
                    public void onTick(long millisUntilFinished) {
                        Log.v("Log_tag", "Tick of Progress"+ i[0] + millisUntilFinished);
                        i[0]++;
                        mProgressBar.setProgress(i[0]);
                    }
                    @Override
                    public void onFinish() {
                        mProgressBar.setVisibility(View.GONE);
                        mLocalDb.insertPlayer(mLocalDb,username,email,1,pass);
                        Intent homePage = new Intent(MainActivity.this, QrScanActivity.class);
                        startActivity(homePage);
                    }
                };
                mCountDownTimer.start();



            }else{
                Toast.makeText(this, "Please connect to internet!", Toast.LENGTH_SHORT).show();
            }
        }else{
            //Not Correct
            Toast.makeText(this, "Need to add info!", Toast.LENGTH_SHORT).show();
        }
       
    }



    // CHECKS

    /* Registration check */
    public int registrationCheck (){
        int passNo = 0;
        if (add_usernam.getText().toString() == null || add_usernam.getText().toString().equals("")) {
            add_usernam.setText("");
            add_usernam.setHintTextColor(0xFFe74c3c);
            passNo =1;
        }
        if (add_email.getText().toString() == null || add_email.getText().toString().equals("") ){
            add_email.setText("");
            add_email.setHintTextColor(0xFFe74c3c);
            passNo =1;
        }
        if (!checkEmail(add_email.getText().toString())){
            add_email.setText("");
            add_email.setHint("Add real Email ");
            add_email.setHintTextColor(0xFFe74c3c);

            passNo =1;
        }
        if (add_pass1.getText().toString() == null || add_pass1.getText().toString().equals("")){
            add_pass1.setText("");
            add_pass1.setHintTextColor(0xFFe74c3c);
            passNo =1;
        }
        for (User u : users) {
            if (add_email.getText().toString().equalsIgnoreCase(u.getEmail())){
                add_email.setText("");
                add_email.setHint("Email is registered ");
                add_email.setHintTextColor(0xFFe74c3c);
                passNo =1;
            }
        }
        return passNo;


    }

    /* Check The NETWORK  */
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    /* Registration email input check */
    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    private boolean checkEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }
    /*First Local DB copy ! */
    private boolean copyDataBase (Context context){

        try {
            InputStream inputStream = context.getAssets().open(DatabaseHelper.DBNAME);
            String outFileName = DatabaseHelper.DBLOCATION + DatabaseHelper.DBNAME;

            OutputStream outputStream = new FileOutputStream(outFileName);

            byte [] buff = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buff)) > 0){
                outputStream.write(buff,0,length);
            }
            outputStream.flush();
            outputStream.close();
            Log.v("MainActivity","DB Copied");
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    class  BackgroundTask extends AsyncTask<Void,Void,String>{

        String json_url ="";

    @Override
    protected String doInBackground(Void ... voids) {
        /* Background Task for geting a JSON file */
        StringBuilder stringBuilder = new StringBuilder();
        try {
            URL url = new URL(json_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
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

    @Override
    protected void onPreExecute() {
        /* Where do we get JSON*/

        json_url = "https://desocialize.000webhostapp.com/users_de.php";

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        /* Geting a JSON String and extracting the info in side the LIST */
        JSON_String = result;
        User u = null;
        try {
            jsonObject = new JSONObject(JSON_String);
            jsonArray = jsonObject.getJSONArray("server_responce");
            int count = 0;
            String username, email , password;
            int idu;
            while(count < jsonArray.length()){
                JSONObject jo = jsonArray.getJSONObject(count);
                idu = jo.getInt("id");
                username = jo.getString("username");
                email = jo.getString("email");
                password = jo.getString("pass");
                u = new User(idu,username,email,password);
                users.add(u);
                count ++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

       //Toast.makeText(MainActivity.this, JSON_String, Toast.LENGTH_SHORT).show();
    }
}
}

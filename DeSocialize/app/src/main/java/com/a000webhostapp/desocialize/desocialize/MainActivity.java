package com.a000webhostapp.desocialize.desocialize;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.a000webhostapp.desocialize.desocialize.localdb.DatabaseHelper;
import com.jaredrummler.android.widget.AnimatedSvgView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    //For Login UsernameAndPass
    String username, pass;

    //Locla DataBase
    private  DatabaseHelper mLocalDb;
    //animation var
    AnimatedSvgView svgView;
    int index = -1;
    //Layout var
    LinearLayout lh1 ;
    LinearLayout layoutlogin;
    LinearLayout layoutregister;
    LinearLayout layoutadd;


    //Registration add info
    EditText add_usernam,add_email,add_pass1;
    EditText login_username, login_pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialization
        svgView = (AnimatedSvgView) findViewById(R.id.animated_svg_view);
        lh1 = (LinearLayout) findViewById(R.id.layouth1);
        layoutlogin  = (LinearLayout) findViewById(R.id.layoutlogin);
        layoutregister = (LinearLayout) findViewById(R.id.layoutregister);
        login_username = (EditText) findViewById(R.id.login_username);
        login_pass = (EditText) findViewById(R.id.login_pass);

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
                // Check LOCAL DB!
               if (state == AnimatedSvgView.STATE_TRACE_STARTED){
                   if (mLocalDb.isRegistrated()){
                       Intent next  = new Intent(MainActivity.this,Main2Activity.class);
                       startActivity(next);
                   }
               }else
                if (state == AnimatedSvgView.STATE_FINISHED) {
                    lh1.setGravity(0);
                    lh1.setPadding(0,50,0,0);
                    layoutlogin.setVisibility(View.VISIBLE);
                    layoutregister.setVisibility(View.VISIBLE);
                }
            }
        });
        // LOGO ANIMATION0 END
    }


    // Methods onClick;
    /* Login User */
    public void login (View view){
        if (isNetworkAvailable()){
            Toast.makeText(this, "Yes there is network", Toast.LENGTH_SHORT).show();
            login_username = (EditText) findViewById(R.id.login_username);
            login_pass = (EditText) findViewById(R.id.login_pass);





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

    /* User register info */
    public void addUser (View view){
        //add values to username, email, pass1;
        add_usernam = (EditText) findViewById(R.id.add_username);
        add_email = (EditText) findViewById(R.id.add_email);
        add_pass1 = (EditText) findViewById(R.id.add_pass1);
        //Preforming ceheck
        int checkreg = registrationCheck();

        if(checkreg == 0){
            //Correct!
            if (isNetworkAvailable()) {
                layoutadd.setVisibility(View.GONE);
                login_username.setText(add_usernam.getText().toString());
                login_pass.setText(add_pass1.getText().toString());
                layoutlogin.setVisibility(View.VISIBLE);
                Toast.makeText(this, "Just login !", Toast.LENGTH_SHORT).show();
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

}

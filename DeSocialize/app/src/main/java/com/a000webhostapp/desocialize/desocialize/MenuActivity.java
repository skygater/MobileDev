package com.a000webhostapp.desocialize.desocialize;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.a000webhostapp.desocialize.desocialize.java.User;
import com.a000webhostapp.desocialize.desocialize.localdb.DatabaseHelper;

public class MenuActivity extends AppCompatActivity {

    TextView txt;
    String responce = "Hello";
    ImageView imgQr;

    //DATA BASE
    //Locla DataBase
    private DatabaseHelper mLocalDb;
    private User u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mLocalDb = new DatabaseHelper(this);
        u = mLocalDb.player();

    }

  public void showqr (View view){
      Intent next  = new Intent(MenuActivity.this,QrShow.class);
      String qr = u.getQr();
      next.putExtra("qrid",qr);
      startActivity(next);
      finish();

  }

public void multiplayer (View view ){
    Intent next  = new Intent(MenuActivity.this,MultiplayerActivity.class);
    String qr = u.getQr();
    /// SEND ID OF THE PLAYER !! CHECK IF ONLINE

    startActivity(next);
    finish();

}

public void profile (View view) {
    Intent next = new Intent( MenuActivity.this, ContactsContract.Profile.class);
    startActivity(next);
    finish();
}

}

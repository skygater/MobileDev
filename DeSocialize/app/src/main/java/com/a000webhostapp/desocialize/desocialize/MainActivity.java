package com.a000webhostapp.desocialize.desocialize;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/*
 *  MENU SCREEN
 *      - display logo and navigation
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profileMI:
                // User chose his profile, navigate to corresponging activity
                return true;


            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    /*
     * Starts singleplayer mode
     *      - started when user clicks on SP button
     *      - Starts corresponding activity
     */
    public void singlePlayer(){

    }

    /*
     * Starts multiplayer mode
     *      - started when user clicks on MP button
     *      - Starts corresponding activity
     */
    public void multiplayer(){

    }
}

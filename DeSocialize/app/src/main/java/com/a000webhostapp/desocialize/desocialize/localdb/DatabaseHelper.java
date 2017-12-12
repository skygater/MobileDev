package com.a000webhostapp.desocialize.desocialize.localdb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by djordjekalezic on 11/12/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "dbsoc.db";
    public static final String DBLOCATION = "/data/data/com.a000webhostapp.desocialize.desocialize/databases/";

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public DatabaseHelper(Context context){
        super(context,DBNAME,null,1);
        this.mContext = context;
    }

    public void openDatabase(){

        String dbPath = mContext.getDatabasePath(DBNAME).getPath();

        if(mDatabase != null && mDatabase.isOpen()){
            return;
        }
        mDatabase = SQLiteDatabase.openDatabase(dbPath,null,SQLiteDatabase.OPEN_READWRITE);
    }

    public void closeDatabase(){
        if(mDatabase != null){
            mDatabase.close();
        }
    }

    public boolean isRegistrated (){

        openDatabase();
        Cursor cursor = mDatabase.rawQuery("select * from player ", null);
        if (cursor.moveToFirst()){
            cursor.close();
            closeDatabase();
            return true;
        }
        return false;
    }

    public boolean insertPlayer (DatabaseHelper db, String username, String email, int idu, String password){

        mDatabase = db.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("idu",idu);
        contentValues.put("email",email);
        contentValues.put("username", username);
        contentValues.put("pass", password);

        openDatabase();
        mDatabase.insert("player",null,contentValues);
        closeDatabase();
        //Toast.makeText(mContext, "Inserted in DB "+idu, Toast.LENGTH_SHORT).show();
        return true;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

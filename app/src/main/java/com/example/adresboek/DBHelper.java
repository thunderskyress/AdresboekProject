package com.example.adresboek;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "Adressen.db";

    public DBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_PLAATS = "CREATE TABLE" + Plaats.TABLE + "("
                + Plaats.KEY_ID + "INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Plaats.KEY_name + "TEXT,"
                + Plaats.KEY_adres + "TEXT)";

        db.execSQL(CREATE_TABLE_PLAATS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ Plaats.TABLE);

        onCreate(db);
    }
}

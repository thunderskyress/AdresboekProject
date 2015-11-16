package com.example.adresboek;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.adresboek.DBHelper;
import com.example.adresboek.Plaats;

import java.util.ArrayList;
import java.util.HashMap;

public class PlaatsRepo {
    private DBHelper dbHelper;

    public PlaatsRepo(Context context){
        dbHelper = new DBHelper(context);
    }

    public int insert(Plaats plaats){

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Plaats.KEY_name, plaats.Plaats_Name);
        values.put(Plaats.KEY_adres, plaats.Plaats_Adres);

        long plaats_id = db.insert(Plaats.TABLE,null,values);
        db.close();
        return (int) plaats_id;
    }

    public void delete(int plaats_id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(Plaats.TABLE,Plaats.KEY_ID + "= ?",new String[]{String.valueOf(plaats_id)});
        db.close();
    }

    public void update(Plaats plaats){

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Plaats.KEY_name,plaats.Plaats_Name);
        values.put(Plaats.KEY_adres,plaats.Plaats_Adres);

        db.update(Plaats.TABLE, values, Plaats.KEY_ID + "=?", new String[]{String.valueOf(plaats.Plaats_ID)});
        db.close();
    }

    public ArrayList<HashMap<String,String>> getPlaatsList(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT " +
                Plaats.KEY_ID + "," +
                Plaats.KEY_name + "," +
                Plaats.KEY_adres + " FROM " + Plaats.TABLE;
        ArrayList<HashMap<String,String>>plaatsList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery,null);

        if (cursor.moveToFirst()){
            do {
                HashMap<String,String> plaats = new HashMap<String, String>();
                plaats.put("id",cursor.getString(cursor.getColumnIndex(Plaats.KEY_ID)));
                plaats.put("name",cursor.getString(cursor.getColumnIndex(Plaats.KEY_name)));
                plaatsList.add(plaats);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return plaatsList;
    }

    public Plaats getPlaatsByID(int id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT " +
                Plaats.KEY_ID + "," +
                Plaats.KEY_name + "," +
                Plaats.KEY_adres + "," +
                " FROM " + Plaats.TABLE +
                " WHERE " + Plaats.KEY_ID + "= ?";

        int iCount = 0;
        Plaats plaats = new Plaats();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(id)});

        if (cursor.moveToFirst()){
            do {
                plaats.Plaats_ID = cursor.getInt(cursor.getColumnIndex(Plaats.KEY_ID));
                plaats.Plaats_Name = cursor.getString(cursor.getColumnIndex(Plaats.KEY_name));
                plaats.Plaats_Adres = cursor.getString(cursor.getColumnIndex(Plaats.KEY_adres));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return plaats;

    }

}

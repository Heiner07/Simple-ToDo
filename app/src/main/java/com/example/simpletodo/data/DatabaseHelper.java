package com.example.simpletodo.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.res.AssetManager;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class DatabaseHelper extends SQLiteOpenHelper {

    private final static String TAG = "DatabaseHelper";
    private final static String DATABASE_SCRIPT_PATH = "dbScript.sql";
    private final static String DATABASE_NAME = "simpleToDo.db";
    private AssetManager assetManager;
    private static DatabaseHelper databaseHelper;

    private DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        if(context != null)
            assetManager = context.getAssets();
    }

    public static DatabaseHelper getDatabaseInstance(@Nullable Context context){
        if(databaseHelper == null){
            databaseHelper = new DatabaseHelper(context);
        }
        return databaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String[] sqlStatements = getSQLStatements(getFileContentAsString(DATABASE_SCRIPT_PATH));
        if(sqlStatements != null){
            for (String statement:sqlStatements) {
                db.execSQL(statement);
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private String[] getSQLStatements(String script){
        if(script == null){
            return null;
        }

        return script.split(";");
    }

    private String getFileContentAsString(String filePath){
        if(assetManager == null || filePath == null){
            return null;
        }
        StringBuilder sb = new StringBuilder();
        String str;
        try{
            InputStream is = assetManager.open(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8 ));
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
            br.close();
            return sb.toString();
        }catch (IOException e){
            Log.d(TAG, "Error reading file: "+ filePath+": "+e);
        }
        return null;
    }
}

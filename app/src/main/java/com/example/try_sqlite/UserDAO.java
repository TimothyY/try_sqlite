package com.example.try_sqlite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class UserDAO {
    String tableName = "MsUser";
    String msUser_username = "username";
    String msUser_password = "password";

    void createTable(SQLiteDatabase db){
        String qCreateMsUser =
                "CREATE TABLE IF NOT EXISTS '"+ tableName +"' (\n" +
                        "'"+msUser_username+"'TEXT NOT NULL PRIMARY KEY UNIQUE,\n" +
                        "'"+msUser_password+"'TEXT\n" +
                        ");";
        db.execSQL(qCreateMsUser);
    }

    public void addUser(Context mCtx, User user){
        MySqliteHelper helper = new MySqliteHelper(mCtx);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(msUser_username, user.username);
        cv.put(msUser_password, user.password);

        db.insertWithOnConflict(
                tableName,
                null, cv, SQLiteDatabase.CONFLICT_REPLACE
        );
        db.close();
    }

    public ArrayList<User> getAllUsers(Context mCtx){
        ArrayList<User> users = new ArrayList<>();
        MySqliteHelper helper = new MySqliteHelper(mCtx);
        SQLiteDatabase db = helper.getReadableDatabase();

        String selectionString = null;
        String[] selectionArgs = null;

        Cursor resultCursor =
                db.query(
                        tableName,
                        null,
                        selectionString,
                        selectionArgs,
                        null,
                        null,
                        null);
        while(resultCursor.moveToNext()){
            @SuppressLint("Range") String username = resultCursor.getString(resultCursor.getColumnIndex(msUser_username));
            @SuppressLint("Range") String password = resultCursor.getString(resultCursor.getColumnIndex(msUser_password));
            users.add(new User(username,password));
        }

        return users;
    }

    String convertUsersToStr(ArrayList<User> users){
        String result = "";
        for(int i=0;i<users.size();i++){
            result=result+users.get(i).username+" "+users.get(i).password+"\n";
        }
        return result;
    }

    public User getSpecificUser(Context mCtx, String paramUsername, String paramPassword){
        String scope = "getSpecificUser";
        User resultUser = null;
        MySqliteHelper helper = new MySqliteHelper(mCtx);
        SQLiteDatabase db = helper.getReadableDatabase();

        String selectionString = msUser_username+"=? AND "+msUser_password+"=?";
        String[] selectionArgs = { paramUsername, paramPassword };

        Cursor resultCursor =
                db.query(
                        tableName,
                        null,
                        selectionString,
                        selectionArgs,
                        null,
                        null,
                        null);

        try{
            while(resultCursor.moveToNext()){
                @SuppressLint("Range") String username = resultCursor.getString(resultCursor.getColumnIndex(msUser_username));
                @SuppressLint("Range") String password = resultCursor.getString(resultCursor.getColumnIndex(msUser_password));
                resultUser = new User(username,password);
            }
        }catch (Exception e){
            Log.e(scope,e.getLocalizedMessage());
        }
        return resultUser;
    }

}

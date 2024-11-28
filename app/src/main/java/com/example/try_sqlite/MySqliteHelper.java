package com.example.try_sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MySqliteHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "mysqlite";
    public static final int DATABASE_VERSION = 1;
    public static final SQLiteDatabase.CursorFactory DATABASE_FACTORY = null;

    public MySqliteHelper(Context context) {
        super(context, DATABASE_NAME, DATABASE_FACTORY, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        UserDAO userDao = new UserDAO();
        userDao.createTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

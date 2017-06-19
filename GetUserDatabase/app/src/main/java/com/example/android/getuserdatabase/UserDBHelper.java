package com.example.android.getuserdatabase;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDBHelper extends SQLiteOpenHelper {

    // For constructor we need DB version and filename

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "users.db";

    public UserDBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        addUserTable(db);
    }

    private void addUserTable(SQLiteDatabase db) {
        // create SQL
        db.execSQL(
                "CREATE TABLE " + UserContract.UserEntry.TABLE_NAME + " (" +
                        UserContract.UserEntry._ID + " INTEGER PRIMARY KEY, " +
                        UserContract.UserEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                        UserContract.UserEntry.COLUMN_ADDRESS + " TEXT NOT NULL, " +
                        UserContract.UserEntry.COLUMN_EMAIL + " TEXT NOT NULL, " +
                        UserContract.UserEntry.COLUMN_PICTURE + " TEXT NOT NULL);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

package com.example.android.getuserdatabase;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


public class UserProvider extends ContentProvider {

    // identifiers for content and content id
    private static final int USER = 100;
    private static final int USER_ID = 101;

    private UserDBHelper dbHelper;

    // Uri matcher will match our constants
    private static final UriMatcher uriMatcher = buildUriMatcher();

    private static UriMatcher buildUriMatcher() {
        String content = UserContract.CONTENT_AUTHORITY;

        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        // tell matcher which uri's it knows about
        matcher.addURI(content,UserContract.PATH_USER,USER);
        matcher.addURI(content,UserContract.PATH_USER + "/#", USER_ID);

        return matcher;
    }


    @Override
    public boolean onCreate() {
        dbHelper = new UserDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        // get instance of writable db
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor returnCursor;
        long _id;

        switch (uriMatcher.match(uri)){
            case USER:
                returnCursor = db.query(
                        UserContract.UserEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case USER_ID:
                _id = ContentUris.parseId(uri);
                returnCursor = db.query(
                        UserContract.UserEntry.TABLE_NAME,
                        projection,
                        UserContract.UserEntry._ID + " =?",
                        new String[]{String.valueOf(_id)},
                        null,
                        null,
                        sortOrder
                );
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        returnCursor.setNotificationUri(getContext().getContentResolver(),uri);
        return returnCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)){
            case USER:
                return UserContract.UserEntry.CONTENT_TYPE;
            case USER_ID:
                return  UserContract.UserEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        long _id;
        Uri returnUri;

        // to know what table is used
        switch (uriMatcher.match(uri)){
            case USER:
                _id = db.insert(UserContract.UserEntry.TABLE_NAME,null,values);
                if(_id>0){
                    returnUri = UserContract.UserEntry.buildUserUri(_id);
                }
                else {
                    throw new UnsupportedOperationException("Unable to Insert row into: " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Notify any observers of our Table that something changed
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rows;

        switch (uriMatcher.match(uri)){
            case USER:
                rows = db.delete(UserContract.UserEntry.TABLE_NAME, selection,selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Content Provider notifies Content Resolver observing data that our data has changed
        // If deleted everything or deleted at least 1, notify the database
        if(selection == null || rows != 0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return rows;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rows;

        switch (uriMatcher.match(uri)){
            case USER:
                rows = db.update(UserContract.UserEntry.TABLE_NAME,values,selection,selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if(rows != 0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return rows;
    }
}

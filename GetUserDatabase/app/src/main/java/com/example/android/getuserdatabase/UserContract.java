package com.example.android.getuserdatabase;


import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class UserContract {

    // entire name for our Contact Provider so for example
    public static final String CONTENT_AUTHORITY = "com.example.android.getuserdatabase";

    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // relevant paths for table Movie and Genre
    public static final String PATH_USER = "user";

    public static final Uri USER_CONTENT_URI =
            BASE_CONTENT_URI.buildUpon().appendPath(PATH_USER).build();

    // define things related to database and content provider
    public static final class UserEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_USER).build();

        // for content type and item type
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/" + CONTENT_URI + "/" + PATH_USER;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/" + CONTENT_URI + "/" + PATH_USER;


        // define the relevant constants for the database table
        public static final String TABLE_NAME = "userTable";   // schema
        public static final String COLUMN_NAME = "userName";   // columns
        public static final String COLUMN_ADDRESS = "userAddress";   // columns
        public static final String COLUMN_EMAIL = "userEmail";   // columns
        public static final String COLUMN_PICTURE = "userPicture";   // columns

        // for enabling Content Resolver
        public static Uri buildUserUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI,id);
        }
    }
}

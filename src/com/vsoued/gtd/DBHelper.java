package com.vsoued.gtd;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.vsoued.gtd.Tasks.Task;
import com.vsoued.gtd.Tasks.Accounts;

public class DBHelper extends SQLiteOpenHelper {

    private static final String TAG = "GTDDB";
    private static final String DATABASE_NAME = "gtd.db";
    private static final int DATABASE_VERSION = 1;
    
    DBHelper(Context context) {

        // calls the super constructor, requesting the default cursor factory.
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     *
     * Creates the underlying database with table name and column names taken from the
     * Tasks class.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Task.TABLE_NAME_TASKS + " ("
                + Task._ID + " INTEGER PRIMARY KEY,"
                + Task.COLUMN_NAME_SUBJECT + " TEXT,"
                + Task.COLUMN_NAME_DESCRIPTION + " TEXT,"
                + Task.COLUMN_NAME_FOLDER + " TEXT,"
                + Task.COLUMN_NAME_TAG + " TEXT,"
                + Task.COLUMN_NAME_PRIORITY + " INTEGER,"
                + Task.COLUMN_NAME_PROJECT_ID + " INTEGER,"
                + Task.COLUMN_NAME_DATE + " TEXT,"
                + Task.COLUMN_NAME_TIME + " TEXT,"
                + Task.COLUMN_NAME_CONTACT + " TEXT,"
                + Task.COLUMN_NAME_CREATE_DATE + " INTEGER,"
                + Task.COLUMN_NAME_MODIFICATION_DATE + " INTEGER,"
                + Task.COLUMN_NAME_PATH + " TEXT"
                + ");");
        
       
        db.execSQL("INSERT INTO "+Task.TABLE_NAME_TASKS+" VALUES (0,'','','"+Task.FOLDER_PROJECTS+"','',0,0,0,0,'',0,0,'')");
        
        db.execSQL("CREATE TABLE " + Accounts.TABLE_NAME_ACCOUNTS + " ("
                + Accounts._ID + " INTEGER PRIMARY KEY,"
                + Accounts.COLUMN_NAME_USER + " TEXT,"
                + Accounts.COLUMN_NAME_PASSWORD + " TEXT,"
                + Accounts.COLUMN_NAME_IMAP_HOST + " TEXT,"
                + Accounts.COLUMN_NAME_IMAP_PORT + " TEXT,"
                + Accounts.COLUMN_NAME_SMTP_HOST + " TEXT,"
                + Accounts.COLUMN_NAME_SMTP_PORT + " TEXT"
                + ");");
    }

    /**
     *
     * Demonstrates that the provider must consider what happens when the
     * underlying datastore is changed. In this sample, the database is upgraded the database
     * by destroying the existing data.
     * A real application should upgrade the database in place.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Logs that the database is being upgraded
        Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");

        // Kills the table and existing data
        db.execSQL("DROP TABLE IF EXISTS gtd");

        // Recreates the database with a new version
        onCreate(db);
    }
}


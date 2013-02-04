package com.vsoued.gtd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.vsoued.gtd.Tasks.Task;
import com.vsoued.gtd.Tasks.Project;
import com.vsoued.gtd.Tasks.Action;

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
                + Task.COLUMN_NAME_CREATE_DATE + " INTEGER,"
                + Task.COLUMN_NAME_MODIFICATION_DATE + " INTEGER"
                + ");");
        
        db.execSQL("CREATE TABLE " + Project.TABLE_NAME_PROJECTS + " ("
                + Project._ID + " INTEGER PRIMARY KEY,"
                + Project.COLUMN_NAME_PROJECT_NAME + " TEXT,"
                + Project.COLUMN_NAME_PROJECT_DESCRIPTION + " TEXT,"
                + Project.COLUMN_NAME_CREATE_DATE + " INTEGER,"
                + Project.COLUMN_NAME_MODIFICATION_DATE + " INTEGER,"
                + Project.COLUMN_NAME_PRIORITY + " INTEGER"
                + ");");
          
        db.execSQL("CREATE TABLE " + Action.TABLE_NAME_ACTIONS + " ("
                + Action._ID + " INTEGER PRIMARY KEY,"
                + Action.COLUMN_NAME_SUBJECT + " TEXT,"
                + Action.COLUMN_NAME_DESCRIPTION + " TEXT,"
                + Action.COLUMN_NAME_PROJECT_ID + " INTEGER,"
                + Action.COLUMN_NAME_TAG + " TEXT,"
                + Action.COLUMN_NAME_PRIORITY + " INTEGER,"
                + Action.COLUMN_NAME_CREATE_DATE + " INTEGER,"
                + Action.COLUMN_NAME_MODIFICATION_DATE + " INTEGER"
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


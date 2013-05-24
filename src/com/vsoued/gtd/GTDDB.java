package com.vsoued.gtd;

import java.util.HashMap;

import com.vsoued.gtd.Tasks.Accounts;
import com.vsoued.gtd.Tasks.Task;

import android.accounts.Account;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class GTDDB {
    private DBHelper helper;  

    private SQLiteDatabase db;  

    /** 
     * 
     * @param context 
     */  
    public GTDDB(Context context){  
        helper = new DBHelper(context);  
        //db = helper.getWritableDatabase();  
    }
    
    public void open() throws SQLException {
        db = helper.getWritableDatabase();
    }

    public void close() {
        helper.close();
    }
    
    public long createTask(String subject, String description, String folder, int priority, int project_id, String path){
        Log.i("DB", "INSERTING IN = "+folder);
        ContentValues values = new ContentValues();  
        values.put(Task.COLUMN_NAME_SUBJECT, subject);  
        values.put(Task.COLUMN_NAME_DESCRIPTION, description);
        values.put(Task.COLUMN_NAME_FOLDER, folder);
        values.put(Task.COLUMN_NAME_PATH, path);
//        values.put(Task.COLUMN_NAME_TAG, tag);
        values.put(Task.COLUMN_NAME_PRIORITY, priority);
        values.put(Task.COLUMN_NAME_PROJECT_ID, project_id);
        Long time = Long.valueOf(System.currentTimeMillis());
        values.put(Task.COLUMN_NAME_CREATE_DATE, time);
        values.put(Task.COLUMN_NAME_MODIFICATION_DATE, time);
        return db.insert(Task.TABLE_NAME_TASKS, null, values);  
    }
    
    public long addAccount(String user, String password, String imap_host, String imap_port, String smtp_host, String smtp_port){
        ContentValues values = new ContentValues();  
        values.put(Accounts.COLUMN_NAME_USER, user);  
        values.put(Accounts.COLUMN_NAME_PASSWORD, password);
        values.put(Accounts.COLUMN_NAME_IMAP_HOST, imap_host);
        values.put(Accounts.COLUMN_NAME_IMAP_PORT, imap_port);
        values.put(Accounts.COLUMN_NAME_SMTP_HOST, smtp_host);
        values.put(Accounts.COLUMN_NAME_SMTP_PORT, smtp_port);
        return db.insert(Accounts.TABLE_NAME_ACCOUNTS, null, values);  
    }
    
    public Cursor getAccounts(){
        String[] columns = new String[] {Accounts._ID, Accounts.COLUMN_NAME_USER, Accounts.COLUMN_NAME_PASSWORD};  
        Cursor cursor = db.query(Accounts.TABLE_NAME_ACCOUNTS,columns,null, null, null, null,null);  
        return cursor;
    }
    
    public long deleteAccount(long id){
        return db.delete(Accounts.TABLE_NAME_ACCOUNTS,Accounts._ID+" = "+id ,null);
    }
    
    
    public Cursor listTask(String folder){
        Log.i("DB", "LISTING "+folder);

        String[] columns = new String[] {Task._ID, Task.COLUMN_NAME_SUBJECT, Task.COLUMN_NAME_PATH};  
        Cursor cursor = db.query(Task.TABLE_NAME_TASKS,columns,Task.COLUMN_NAME_FOLDER+" = '"+folder+"'", null  
             , null, null,Task.COLUMN_NAME_CREATE_DATE);  
        return cursor;
    }
    
    public Cursor showTask(long id){
        Log.i("DB", "DETALS FOR ID = "+id);
        String[] columns = new String[] {Task._ID, Task.COLUMN_NAME_SUBJECT, Task.COLUMN_NAME_DESCRIPTION, Task.COLUMN_NAME_FOLDER, Task.COLUMN_NAME_PRIORITY, Task.COLUMN_NAME_PATH, Task.COLUMN_NAME_PROJECT_ID};  
        Cursor cursor = db.query(Task.TABLE_NAME_TASKS,columns, Task._ID+" = "+id, null  
             , null, null,null);  
        return cursor;
    }
    
    public Cursor listProject(){
        String[] columns = new String[] {Task._ID, Task.COLUMN_NAME_SUBJECT, Task.COLUMN_NAME_DESCRIPTION};  
        Cursor cursor = db.query(Task.TABLE_NAME_TASKS,columns,Task._ID+" != '0' AND "+Task.COLUMN_NAME_PROJECT_ID+" = '0' AND "+Task.COLUMN_NAME_FOLDER+" = '"+Task.FOLDER_PROJECTS+"'", null  
             , null, null,Task.COLUMN_NAME_PRIORITY+" desc");  
        return cursor;
    }
    
    public Cursor subProjectsById(long id){
        String[] columns = new String[] {Task._ID, Task.COLUMN_NAME_SUBJECT, Task.COLUMN_NAME_DESCRIPTION};  
        Cursor cursor = db.query(Task.TABLE_NAME_TASKS,columns, Task.COLUMN_NAME_PROJECT_ID+" = '"+id+"'", null  
             , null, null,Task.COLUMN_NAME_PRIORITY+" desc");  
        return cursor;
    }
    
    public Cursor projectSpinner(){
        String[] columns = new String[] {Task._ID, Task.COLUMN_NAME_SUBJECT};  
        Cursor cursor = db.query(Task.TABLE_NAME_TASKS,columns, Task.COLUMN_NAME_FOLDER+" = '"+Task.FOLDER_PROJECTS+"'", null  
             , null, null,null);  
        return cursor;
    }
    
    public Cursor oneProject(long id){
        String[] columns = new String[] {Task._ID, Task.COLUMN_NAME_SUBJECT, Task.COLUMN_NAME_DESCRIPTION, Task.COLUMN_NAME_PRIORITY, Task.COLUMN_NAME_PROJECT_ID};  
        Cursor cursor = db.query(Task.TABLE_NAME_TASKS,columns,Task._ID+" = "+id , null  
             , null, null, null);  
        return cursor;
    }
    
    
    public Cursor filterTaskByTag(String tag){
        String[] columns = new String[] {Task._ID, Task.COLUMN_NAME_SUBJECT, Task.COLUMN_NAME_DESCRIPTION};  
        Cursor cursor = db.query(Task.TABLE_NAME_TASKS,columns,Task.COLUMN_NAME_TAG+" = "+tag, null  
             , null, null,Task.COLUMN_NAME_PRIORITY);  
        return cursor;
    }
    
    public void deleteProject(long id){
        recourseDelete(id);
    }
    
    public void recourseDelete(long id){
        Cursor c = subProjectsById(id);
        c.moveToFirst();
        while(!c.isAfterLast()){
            recourseDelete(c.getInt(c.getColumnIndex(Task._ID)));
            c.moveToNext();
        }
        db.delete(Task.TABLE_NAME_TASKS,Task._ID+" = "+id ,null);
    }
    
    public long deleteTask(long id){
        return db.delete(Task.TABLE_NAME_TASKS,Task._ID+" = "+id ,null);
    }
    
    public long updateTask(long id, ContentValues values){
        Log.i("DB", "EDITING = "+id);
        
        Long time = Long.valueOf(System.currentTimeMillis());
        values.put(Task.COLUMN_NAME_MODIFICATION_DATE, time);
        
        return db.update(Task.TABLE_NAME_TASKS, values, Task._ID+" = "+id, null);    
    }

    public Cursor filter(CharSequence constraint, String folder) { 
        Cursor cursor;
        Log.i("FILTER", constraint.toString()+" in "+folder);
            
            String[] columns = new String[] {Task._ID, Task.COLUMN_NAME_SUBJECT, Task.COLUMN_NAME_PATH};  
            cursor = db.query(Task.TABLE_NAME_TASKS,columns,Task.COLUMN_NAME_SUBJECT+" LIKE '%"+constraint+"%' AND "+Task.COLUMN_NAME_FOLDER+" = '"+folder+"'", null  
                 , null, null,Task.COLUMN_NAME_SUBJECT);  
        return cursor;
    }
}

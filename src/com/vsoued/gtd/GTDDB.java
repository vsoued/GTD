package com.vsoued.gtd;

import java.util.HashMap;

import com.vsoued.gtd.Tasks.Task;
import com.vsoued.gtd.Tasks.Project;

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
    
    public long createTask(String subject, String description, String folder, int priority, int project_id){
        Log.i("DB", "INSERTING IN = "+folder);
        ContentValues values = new ContentValues();  
        values.put(Task.COLUMN_NAME_SUBJECT, subject);  
        values.put(Task.COLUMN_NAME_DESCRIPTION, description);
        values.put(Task.COLUMN_NAME_FOLDER, folder);
//        values.put(Task.COLUMN_NAME_TAG, tag);
        values.put(Task.COLUMN_NAME_PRIORITY, priority);
        values.put(Task.COLUMN_NAME_PROJECT_ID, project_id);
        Long time = Long.valueOf(System.currentTimeMillis());
        values.put(Task.COLUMN_NAME_CREATE_DATE, time);
        values.put(Task.COLUMN_NAME_MODIFICATION_DATE, time);
        return db.insert(Task.TABLE_NAME_TASKS, null, values);  
    }
    
    public long createProject(String name, String description, int priority, int project_id){
        ContentValues values = new ContentValues(); 
        values.put(Project.COLUMN_NAME_PROJECT_NAME, name);  
        values.put(Project.COLUMN_NAME_PROJECT_DESCRIPTION, description);
        values.put(Project.COLUMN_NAME_PRIORITY, priority);
        values.put(Project.COLUMN_NAME_PROJECT_ID, project_id);
        Long time = Long.valueOf(System.currentTimeMillis());
        values.put(Project.COLUMN_NAME_CREATE_DATE, time);
        values.put(Project.COLUMN_NAME_MODIFICATION_DATE, time);
        return db.insert(Project.TABLE_NAME_PROJECTS, null, values);  
    }
    
    public Cursor listTask(String folder){
        Log.i("DB", "LISTING "+folder);

        String[] columns = new String[] {Task._ID, Task.COLUMN_NAME_SUBJECT, Task.COLUMN_NAME_DESCRIPTION};  
        Cursor cursor = db.query(Task.TABLE_NAME_TASKS,columns,Task.COLUMN_NAME_FOLDER+" = '"+folder+"'", null  
             , null, null,Task.COLUMN_NAME_CREATE_DATE);  
        return cursor;
    }
    
    public Cursor showTask(long id){
        Log.i("DB", "DETALS FOR ID = "+id);
        String[] columns = new String[] {Task._ID, Task.COLUMN_NAME_SUBJECT, Task.COLUMN_NAME_DESCRIPTION, Task.COLUMN_NAME_FOLDER, Task.COLUMN_NAME_PRIORITY};  
        Cursor cursor = db.query(Task.TABLE_NAME_TASKS,columns, Task._ID+" = "+id, null  
             , null, null,null);  
        return cursor;
    }
    
    public Cursor listProject(){
        String[] columns = new String[] {Project._ID, Project.COLUMN_NAME_PROJECT_NAME, Project.COLUMN_NAME_PROJECT_DESCRIPTION};  
        Cursor cursor = db.query(Project.TABLE_NAME_PROJECTS,columns,Project._ID+" != '0'", null  
             , null, null,Project.COLUMN_NAME_PRIORITY+" desc");  
        return cursor;
    }
    
    public Cursor projectSpinner(){
        String[] columns = new String[] {Project._ID, Project.COLUMN_NAME_PROJECT_NAME};  
        Cursor cursor = db.query(Project.TABLE_NAME_PROJECTS,columns,null, null  
             , null, null,null);  
        return cursor;
    }
    
    public Cursor oneProject(long id){
        String[] columns = new String[] {Project.COLUMN_NAME_PROJECT_NAME, Project.COLUMN_NAME_PROJECT_DESCRIPTION, Project.COLUMN_NAME_PRIORITY};  
        Cursor cursor = db.query(Project.TABLE_NAME_PROJECTS,columns,Project._ID+" = "+id , null  
             , null, null, null);  
        return cursor;
    }
    
    public Cursor showProject(long id){
        String[] columns = new String[] {Task.COLUMN_NAME_SUBJECT, Task.COLUMN_NAME_DESCRIPTION, Task.COLUMN_NAME_TAG};  
        Cursor cursor = db.query(Task.TABLE_NAME_TASKS ,columns,Task.COLUMN_NAME_PROJECT_ID+" = "+id, null  
             , null, null, Task.COLUMN_NAME_PRIORITY);  
        return cursor;
    }
    
    
    public Cursor filterTaskByTag(String tag){
        String[] columns = new String[] {Task._ID, Task.COLUMN_NAME_SUBJECT, Task.COLUMN_NAME_DESCRIPTION};  
        Cursor cursor = db.query(Task.TABLE_NAME_TASKS,columns,Task.COLUMN_NAME_TAG+" = "+tag, null  
             , null, null,Task.COLUMN_NAME_PRIORITY);  
        return cursor;
    }
    
    public long deleteProject(long id){
        db.delete(Task.TABLE_NAME_TASKS, Task.COLUMN_NAME_PROJECT_ID+" = "+id, null);
        return db.delete(Project.TABLE_NAME_PROJECTS,Project._ID+" = "+id ,null);
    }
    
    public long deleteTask(long id){
        return db.delete(Task.TABLE_NAME_TASKS,Task._ID+" = "+id ,null);
    }
    
 
    public long updateProject(long id, HashMap<String, String> map){
        ContentValues values = new ContentValues(); 
            for (String val : map.keySet()){
                values.put(val, map.get(val));
            }   
        
        Long time = Long.valueOf(System.currentTimeMillis());
        values.put(Project.COLUMN_NAME_MODIFICATION_DATE, time);
        
        return db.update(Project.TABLE_NAME_PROJECTS, values, Project._ID+" = "+id, null);    
    }
    
    public long updateTask(long id, ContentValues values){
        Log.i("DB", "EDITING = "+id);
        
        Long time = Long.valueOf(System.currentTimeMillis());
        values.put(Task.COLUMN_NAME_MODIFICATION_DATE, time);
        
        return db.update(Task.TABLE_NAME_TASKS, values, Task._ID+" = "+id, null);    
    }
}

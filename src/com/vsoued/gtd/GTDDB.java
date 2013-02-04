package com.vsoued.gtd;

import java.util.HashMap;

import com.vsoued.gtd.Tasks.Task;
import com.vsoued.gtd.Tasks.Project;
import com.vsoued.gtd.Tasks.Action;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class GTDDB {
    private DBHelper helper;  

    private SQLiteDatabase db;  

    /** 
     * 
     * @param context 
     */  
    public GTDDB(Context context){  
        helper = new DBHelper(context);  
        db = helper.getWritableDatabase();  
    }
    
    public void open() throws SQLException {
        db = helper.getWritableDatabase();
    }

    public void close() {
        helper.close();
    }
    
    public long insertTask(String subject, String description, String folder){
        ContentValues values = new ContentValues();  
        values.put(Task.COLUMN_NAME_SUBJECT, subject);  
        values.put(Task.COLUMN_NAME_DESCRIPTION, description);
        values.put(Task.COLUMN_NAME_FOLDER, folder);
        Long time = Long.valueOf(System.currentTimeMillis());
        values.put(Task.COLUMN_NAME_CREATE_DATE, time);
        values.put(Task.COLUMN_NAME_MODIFICATION_DATE, time);
        return db.insert(Task.TABLE_NAME_TASKS, null, values);  
    }
    
    public long insertAction(String subject, String description, int project_id, String tag, int priority){
        ContentValues values = new ContentValues();  
        values.put(Action.COLUMN_NAME_SUBJECT, subject);  
        values.put(Action.COLUMN_NAME_DESCRIPTION, description);
        values.put(Action.COLUMN_NAME_PRIORITY, priority);
        values.put(Action.COLUMN_NAME_TAG, tag);
        values.put(Action.COLUMN_NAME_PROJECT_ID, project_id);
        Long time = Long.valueOf(System.currentTimeMillis());
        values.put(Action.COLUMN_NAME_CREATE_DATE, time);
        values.put(Action.COLUMN_NAME_MODIFICATION_DATE, time);
        return db.insert(Action.TABLE_NAME_ACTIONS, null, values);  
    }
    
    public long insertProject(String name, String description, int priority){
        ContentValues values = new ContentValues(); 
        values.put(Project.COLUMN_NAME_PROJECT_NAME, name);  
        values.put(Project.COLUMN_NAME_PROJECT_DESCRIPTION, description);
        values.put(Project.COLUMN_NAME_PRIORITY, priority);
        Long time = Long.valueOf(System.currentTimeMillis());
        values.put(Project.COLUMN_NAME_CREATE_DATE, time);
        values.put(Project.COLUMN_NAME_MODIFICATION_DATE, time);
        return db.insert(Project.TABLE_NAME_PROJECTS, null, values);  
    }
    
    public Cursor listTask(String folder){
        String[] columns = new String[] {Task._ID, Task.COLUMN_NAME_SUBJECT, Task.COLUMN_NAME_DESCRIPTION};  
        Cursor cursor = db.query(Task.TABLE_NAME_TASKS,columns, Task.COLUMN_NAME_FOLDER+" = '"+folder+"'", null  
             , null, null,Task.COLUMN_NAME_MODIFICATION_DATE);  
//        if (cursor != null) {  
//         cursor.moveToFirst(); 
//        }
        return cursor;
    }
    
    public Cursor showTask(int id){
        String[] columns = new String[] {Task._ID, Task.COLUMN_NAME_SUBJECT, Task.COLUMN_NAME_DESCRIPTION, Task.COLUMN_NAME_FOLDER, Task.COLUMN_NAME_MODIFICATION_DATE};  
        Cursor cursor = db.query(Task.TABLE_NAME_TASKS,columns, Task._ID+" = "+id, null  
             , null, null,null);  
//        if (cursor != null) {  
//         cursor.moveToFirst(); 
//        }
        return cursor;
    }
    
    public Cursor listProject(){
        String[] columns = new String[] {Project.COLUMN_NAME_PROJECT_NAME, Project.COLUMN_NAME_PROJECT_DESCRIPTION};  
        Cursor cursor = db.query(Project.TABLE_NAME_PROJECTS,columns,null, null  
             , null, null,Project.COLUMN_NAME_PRIORITY);  
//        if (cursor != null) {  
//         cursor.moveToFirst(); 
//        }
        return cursor;
    }
    
    public Cursor showProject(int project_id){
        String[] columns = new String[] {Action.COLUMN_NAME_SUBJECT, Action.COLUMN_NAME_DESCRIPTION, Action.COLUMN_NAME_TAG};  
        Cursor cursor = db.query(Action.TABLE_NAME_ACTIONS,columns,Action.COLUMN_NAME_PROJECT_ID+" = "+project_id, null  
             , null, null, Action.COLUMN_NAME_PRIORITY);  
//        if (cursor != null) {  
//         cursor.moveToFirst(); 
//        }
        return cursor;
    }
    
    public Cursor listAction(){
        String[] columns = new String[] {Action._ID, Action.COLUMN_NAME_SUBJECT, Action.COLUMN_NAME_DESCRIPTION};  
        Cursor cursor = db.query(Action.TABLE_NAME_ACTIONS,columns,null, null  
             , null, null,Action.COLUMN_NAME_PRIORITY);  
//        if (cursor != null) {  
//         cursor.moveToFirst(); 
//        }
        return cursor;
    }
    
    public Cursor listActionByTag(String tag){
        String[] columns = new String[] {Action.COLUMN_NAME_SUBJECT, Action.COLUMN_NAME_DESCRIPTION};  
        Cursor cursor = db.query(Action.TABLE_NAME_ACTIONS,columns,Action.COLUMN_NAME_TAG+" = "+tag, null  
             , null, null,Action.COLUMN_NAME_PRIORITY);  
//        if (cursor != null) {  
//         cursor.moveToFirst(); 
//        }
        return cursor;
    }
    
    public long deleteProject(long id){
        return db.delete(Project.TABLE_NAME_PROJECTS,Project._ID+" = "+id ,null);
    }
    
    public long deleteTask(long id){
        return db.delete(Task.TABLE_NAME_TASKS,Task._ID+" = "+id ,null);
    }
    
    public long deleteAction(long id){
        return db.delete(Action.TABLE_NAME_ACTIONS,Action._ID+" = "+id ,null);
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
    
    public long updateTask(long id, HashMap<String, String> map){
        ContentValues values = new ContentValues(); 
            for (String val : map.keySet()){
                values.put(val, map.get(val));
            }   
        
        Long time = Long.valueOf(System.currentTimeMillis());
        values.put(Task.COLUMN_NAME_MODIFICATION_DATE, time);
        
        return db.update(Task.TABLE_NAME_TASKS, values, Task._ID+" = "+id, null);    
    }
    
    public long updateAction(long id, HashMap<String, String> map){
        ContentValues values = new ContentValues(); 
            for (String val : map.keySet()){
                values.put(val, map.get(val));
            }   
        
        Long time = Long.valueOf(System.currentTimeMillis());
        values.put(Action.COLUMN_NAME_MODIFICATION_DATE, time);
        
        return db.update(Action.TABLE_NAME_ACTIONS, values, Action._ID+" = "+id, null);    
    }
}

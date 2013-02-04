package com.vsoued.gtd;

import android.app.Activity;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.vsoued.gtd.Tasks.Task;


public class Details extends Activity{
    GTDDB db;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        
        db = new GTDDB(getBaseContext());
        String[] fields = new String[]  {Task.COLUMN_NAME_SUBJECT, Task.COLUMN_NAME_DESCRIPTION, Task.COLUMN_NAME_FOLDER, Task.COLUMN_NAME_MODIFICATION_DATE};
        int[] views = new int[] {R.id.text1, R.id.text2, R.id.text3, R.id.text4};
        Cursor c = db.showTask(this.getIntent().getIntExtra("index", 1));
        
        SimpleCursorAdapter prjName = new SimpleCursorAdapter( this, R.layout.activity_details_view, c, fields, views, 0);
        setContentView(R.layout.activity_details_view);
        ((ListView) findViewById(R.id.details_list)).setAdapter(prjName);
    }
}

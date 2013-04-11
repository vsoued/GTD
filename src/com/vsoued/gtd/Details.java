package com.vsoued.gtd;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.vsoued.gtd.Tasks.Task;


public class Details extends Activity{
    GTDDB db;
    long id;
    String[] fields;
    int[] views;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        
        db = new GTDDB(getBaseContext());
        fields = new String[]  {Task.COLUMN_NAME_SUBJECT, Task.COLUMN_NAME_DESCRIPTION, Task.COLUMN_NAME_FOLDER, Task.COLUMN_NAME_MODIFICATION_DATE};
        views = new int[] {R.id.text1, R.id.text2, R.id.text3, R.id.text4};
        id = this.getIntent().getLongExtra("index", 1);
        
        
        
        
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_details, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_delete:
                db.open();           
                db.deleteTask(id);
                db.close();
                finish();
                return true;
            case R.id.menu_edit:
                Intent intent = new Intent(this, Edit.class);
                //intent.setClass(getActivity(), Details.class);
                intent.putExtra("index", id);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        
    }
    
    @Override
    public void onResume(){
        super.onResume();
        db.open();
        Cursor c = db.showTask(id);
        
   
        SimpleCursorAdapter prjName = new SimpleCursorAdapter( this, R.layout.activity_details_view, c, fields, views, 0);
        setContentView(R.layout.activity_details_view);
        ((ListView) findViewById(R.id.details_list)).setAdapter(prjName);
        db.close();
    }
}

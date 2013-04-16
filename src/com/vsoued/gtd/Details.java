package com.vsoued.gtd;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.vsoued.gtd.Tasks.Task;


public class Details extends Activity{
    GTDDB db;
    long id;
    TextView text1;
    TextView text2;
    TextView text3;
    TextView text4;
    RatingBar bar;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_view);
        
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        
        db = new GTDDB(getBaseContext());
        id = this.getIntent().getLongExtra("index", 1);
        
        text1 = (TextView) findViewById(R.id.text1);
        text2 = (TextView) findViewById(R.id.text2);
        text3 = (TextView) findViewById(R.id.text3);
        text4 = (TextView) findViewById(R.id.text4);
        bar = (RatingBar) findViewById(R.id.ratingBar1);
        
        
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
        c.moveToFirst();
        text1.setText(c.getString(c.getColumnIndex(Task.COLUMN_NAME_SUBJECT)));
        text2.setText(c.getString(c.getColumnIndex(Task.COLUMN_NAME_DESCRIPTION)));
        text3.setText(c.getString(c.getColumnIndex(Task.COLUMN_NAME_FOLDER)));
        bar.setRating((float)c.getInt(c.getColumnIndex(Task.COLUMN_NAME_PRIORITY)));
        db.close();
    }
}

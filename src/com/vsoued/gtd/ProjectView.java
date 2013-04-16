package com.vsoued.gtd;

import com.vsoued.gtd.Tasks.Project;
import com.vsoued.gtd.Tasks.Task;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleCursorTreeAdapter;
import android.widget.TextView;

public class ProjectView extends Activity{
    GTDDB db;
    long id;
    SimpleCursorTreeAdapter adapter;
    public String folder = Project.TABLE_NAME_PROJECTS;
   // String[] project_columns = {Project.COLUMN_NAME_PROJECT_NAME, Project.COLUMN_NAME_PROJECT_DESCRIPTION};
    String[] action_columns = {Task.COLUMN_NAME_SUBJECT,Task.COLUMN_NAME_DESCRIPTION};
    int[] project_views = {android.R.id.text1, android.R.id.text2};
    int[] action_views = {android.R.id.text1, android.R.id.text2};
    int[] views = new int[] {R.id.project_name, R.id.project_description};
    TextView text1;
    TextView text2;
    RatingBar bar;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_details);
        
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        
        db = new GTDDB(getBaseContext());
        id = this.getIntent().getLongExtra("index", 1);
        
        text1 = (TextView) findViewById(R.id.project_name);
        text2 = (TextView) findViewById(R.id.project_description);
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
                db.deleteProject(id);
                db.close();
                finish();
                return true;
            case R.id.menu_edit:
//                Intent intent = new Intent(this, Edit.class);
//                //intent.setClass(getActivity(), Details.class);
//                intent.putExtra("index", id);
//                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        
    }
    @Override
    public void onResume(){
        super.onResume();
        db.open();
        Cursor c = db.oneProject(id);
        c.moveToFirst();
        text1.setText(c.getString(c.getColumnIndex(Project.COLUMN_NAME_PROJECT_NAME)));
        text2.setText(c.getString(c.getColumnIndex(Project.COLUMN_NAME_PROJECT_DESCRIPTION)));
        bar.setRating((float)c.getInt(c.getColumnIndex(Project.COLUMN_NAME_PRIORITY)));
        db.close();
        
//      adapter = new ProjectAdapter(this,db.listProject(),
//      android.R.layout.simple_expandable_list_item_1, project_columns,project_views,
//      android.R.layout.simple_expandable_list_item_2,action_columns,action_views);
//setListAdapter(adapter);
 //setContentView(R.layout.activity_details_view);

        
    }
    
    public class ProjectAdapter extends SimpleCursorTreeAdapter {

        public ProjectAdapter(Context context, Cursor cursor, 
                int groupLayout, String[] groupFrom, int[] groupTo, 
                int childLayout, String[] childFrom, int[] childTo) {
            super(context, cursor, groupLayout, groupFrom, groupTo, childLayout, childFrom,
                    childTo);
            // TODO Auto-generated constructor stub
        }

        @Override
        protected Cursor getChildrenCursor(Cursor cursor) {
            
            db.open();
            if (cursor.isBeforeFirst()){
                cursor.moveToFirst();
            }
            Cursor children = db.showProject(cursor.getPosition());
            
            db.close();
            return children;
        }
        
    }
   
}


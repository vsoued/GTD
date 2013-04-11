package com.vsoued.gtd;

import com.vsoued.gtd.Tasks.Project;
import com.vsoued.gtd.Tasks.Task;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleCursorTreeAdapter;
import android.widget.TextView;

public class ProjectView extends Activity{
    GTDDB db;
    long id;
    SimpleCursorTreeAdapter adapter;
    public String folder = Project.TABLE_NAME_PROJECTS;
    String[] project_columns = {Project.COLUMN_NAME_PROJECT_NAME, Project.COLUMN_NAME_PROJECT_DESCRIPTION};
    String[] action_columns = {Task.COLUMN_NAME_SUBJECT,Task.COLUMN_NAME_DESCRIPTION};
    int[] project_views = {android.R.id.text1, android.R.id.text2};
    int[] action_views = {android.R.id.text1, android.R.id.text2};
    int[] views = new int[] {R.id.project_name, R.id.project_description};
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_details);
        
        
        
        db = new GTDDB(getBaseContext());
        id = this.getIntent().getLongExtra("index", 1);
        
        
        
        
    }
    @Override
    public void onResume(){
        super.onResume();
        db.open();
        Cursor c = db.oneProject(id);
        adapter = new ProjectAdapter(this,db.listProject(),
                android.R.layout.simple_expandable_list_item_1, project_columns,project_views,
                android.R.layout.simple_expandable_list_item_2,action_columns,action_views);
        //setListAdapter(adapter);
           //setContentView(R.layout.activity_details_view);
        ((TextView) findViewById(R.id.project_name)).setText(c.getString(c.getColumnIndex(project_columns[0])));
        ((TextView) findViewById(R.id.project_description)).setText(c.getString(c.getColumnIndex(project_columns[1])));
        db.close();
        
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


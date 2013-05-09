package com.vsoued.gtd;

import com.vsoued.gtd.Tasks.Task;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleCursorTreeAdapter;
import android.widget.TextView;

public class ProjectView extends Activity{
    GTDDB db;
    long id;
    int count = 0;
    SimpleCursorTreeAdapter adapter; 
   // int[] views = new int[] {R.id.project_name, R.id.project_description};
    TextView text1;
    TextView text2;
    RatingBar bar;
    ExpandableListView expandableList;
    String[] groupFrom = {Task.COLUMN_NAME_SUBJECT};
    String[] childFrom = {Task.COLUMN_NAME_SUBJECT};
    int[] groupTo = {android.R.id.text1};
    int[] childTo = {android.R.id.text2};
    int groupLayout = android.R.layout.simple_expandable_list_item_1;
    int childLayout = android.R.layout.simple_expandable_list_item_2;
    
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
        expandableList = (ExpandableListView) findViewById(R.id.expandable_list_view);
       
        
        
        
        
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
        text1.setText(c.getString(c.getColumnIndex(Task.COLUMN_NAME_SUBJECT)));
        text2.setText(c.getString(c.getColumnIndex(Task.COLUMN_NAME_DESCRIPTION)));
        bar.setRating((float)c.getInt(c.getColumnIndex(Task.COLUMN_NAME_PRIORITY)));
        c.moveToPosition(-1);
        expandableList.setAdapter(new ProjectAdapter(this,c, groupLayout, groupFrom, groupTo, 
                    childLayout, childFrom, childTo));
        //recourse(id, expandableList);
        db.close();

        
    }
    
//    void recourse(long id2, ExpandableListView view){
//        Cursor c = db.subProjectsById(id2);
//        ExpandableListView elv; 
//        if (c.getCount() > 0){
//            elv = new ExpandableListView(this);
//            elv.setAdapter(new ProjectAdapter(this,c, groupLayout, groupFrom, groupTo, 
//                    childLayout, childFrom, childTo));
//            view.addView(elv, -1, -2);
//            
//            c.moveToPosition(-1);
//            for (int i = 0; i < c.getCount(); i++){
//                c.moveToNext();
//                recourse(c.getInt(c.getColumnIndex(Project._ID)),elv);   
//            }
//        } else {
//            Cursor d = db.showProject(id2);        
//            ListView listv = new ListView(this);
//            listv.setAdapter(new SimpleCursorAdapter (this,android.R.layout.simple_list_item_1, d, new String[]{Task.COLUMN_NAME_SUBJECT}, new int[]{android.R.id.text1}, 0));
//            view.addView(listv, (int)-1, (int)-2);
//        }
//    }
    
    public class ProjectAdapter extends SimpleCursorTreeAdapter {
        //int padding = 20;
        

        public ProjectAdapter(Context context, Cursor cursor, 
                int groupLayout, String[] groupFrom, int[] groupTo, 
                int childLayout, String[] childFrom, int[] childTo) {
            super(context, cursor, groupLayout, groupFrom, groupTo, childLayout, childFrom,
                    childTo);
//            count++;
//            Log.i("projectadapter", ""+count);
            // TODO Auto-generated constructor stub
        }
//
//        @Override
//      public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent){
//            return  null;
//        }
        
//        @Override
//        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent){
//            Log.i("getgroupview", "is called");
//            convertView = new TextView(ProjectView.this);
//            ((TextView) convertView).setText("hihihihih");
//            return convertView;
//            

//            Log.i("getgroupview", " "+groupPosition);
//            
//            if (parent != null && parent instanceof ExpandableListView) {
//                Log.i("getgroupview", "parent is expandablelistview");
//
//                ExpandableListAdapter adapter = ((ExpandableListView) parent)
//                        .getExpandableListAdapter();
//                if (adapter instanceof ProjectAdapter) {
//                    Log.i("getgroupview", "adapter is project adapter");
//
//                    db.open();
//
//                    Cursor cursor = ((ProjectAdapter) adapter).getCursor();
//                    cursor.moveToFirst();
//                    Cursor children = db.subProjectsById(cursor.getInt(cursor.getColumnIndex(Project._ID)));
//                    children.moveToFirst();
//
//                    //                    mcursor = new MatrixCursor(children.getColumnNames(), 1);
//                    //                    mcursor.addRow(new Object[] {children.getInt(children.getColumnIndex(Project._ID)),
//                    //                            children.getString(children.getColumnIndex(Project.COLUMN_NAME_PROJECT_NAME)),
//                    //                            children.getString(children.getColumnIndex(Project.COLUMN_NAME_PROJECT_DESCRIPTION))});
//                    convertView = new ExpandableListView(ProjectView.this);
//                    ((ExpandableListView) convertView).setAdapter(new ProjectAdapter(ProjectView.this,
//                            children, groupLayout, groupFrom, groupTo,
//                            childLayout, childFrom, childTo));
//                    db.close();
//                    return convertView;
//
//                }
//            }
//        return null;
//
//            
//        }
//        @Override
//        public Object getChild(int arg0, int arg1) {
//            return arg1;
//        }
//        @Override
//        public Object getGroup(int groupPosition) {
//            return groupPosition;
//        }

//        @Override
//        public long getChildId(int groupPosition, int childPosition) {
//            return childPosition;
//        }
//        
//        @Override
//        public long getGroupId(int groupPosition) {
//            return groupPosition;
//        }
//        
//        @Override
//        public int getGroupCount() {
//                   return this.getCursor().getCount();
//
//        }
//
//        @Override
//        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent){
//            Log.i("getchildview", " "+childPosition);
//            
//                if (parent != null && parent instanceof ExpandableListView) {
//                    ExpandableListAdapter adapter = ((ExpandableListView) parent)
//                            .getExpandableListAdapter();
//                    if (adapter instanceof ProjectAdapter) {
//                        db.open();
//
//                        Cursor cursor = ((ProjectAdapter) adapter).getCursor();
//                        cursor.moveToFirst();
//                        Cursor children = db.subProjectsById(cursor.getInt(cursor.getColumnIndex(Task._ID)));
//                        
//                        LayoutInflater inflater = (LayoutInflater)ProjectView.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                        convertView = inflater.inflate(R.layout.activity_project_details, parent, false);
//                        convertView  =(ExpandableListView)convertView.findViewById(R.id.expandable_list_view);
//                       
//
//                        
//
//
//                        //                    mcursor = new MatrixCursor(children.getColumnNames(), 1);
//                        //                    mcursor.addRow(new Object[] {children.getInt(children.getColumnIndex(Project._ID)),
//                        //                            children.getString(children.getColumnIndex(Project.COLUMN_NAME_PROJECT_NAME)),
//                        //                            children.getString(children.getColumnIndex(Project.COLUMN_NAME_PROJECT_DESCRIPTION))});
//                        //convertView = new ExpandableListView(ProjectView.this);
//                        ((ExpandableListView) convertView).setAdapter(new ProjectAdapter(ProjectView.this,
//                                children, groupLayout, groupFrom, groupTo,
//                                childLayout, childFrom, childTo));
//                        db.close();
//                        parent.addView(convertView);
//                        return convertView;
//
//                    }
//                }
//                Log.i("getchildview", "something wrong");
//
//            return null;
//        }
        
        @Override
        protected Cursor getChildrenCursor(Cursor cursor) {
            db.open();
            
//            MatrixCursor mcursor = null;
//            Log.i("getchildrencursor", " "+cursor.getString(cursor.getColumnIndex(Task.COLUMN_NAME_SUBJECT)));

             
            Cursor children = db.subProjectsById(cursor.getInt(cursor.getColumnIndex(Task._ID)));
            
//            if(children.getCount() > 0){
//              children.moveToFirst();
//                Log.i("getCHILDREN", "count "+children.getCount()+" "+children.getString(children.getColumnIndex(Task.COLUMN_NAME_SUBJECT)));
//            
//                mcursor = new MatrixCursor(children.getColumnNames(), 1);
//                mcursor.addRow(new Object[] {children.getInt(children.getColumnIndex(Task._ID)),
//                        children.getString(children.getColumnIndex(Task.COLUMN_NAME_SUBJECT)),
//                        children.getString(children.getColumnIndex(Task.COLUMN_NAME_DESCRIPTION))});
//                return mcursor;
//            }
//            
//            if (children.getCount() == 0 || children == null){
//                Log.i("getchildrencursor", "no children");
//                
//            }
//            
//            Log.i("getchildrencursor", "didnt return mcursosr");

            return children;
        }
        
    }
    
   
}


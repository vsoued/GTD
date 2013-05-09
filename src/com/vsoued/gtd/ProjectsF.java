package com.vsoued.gtd;

import com.vsoued.gtd.Tasks.Task;

import android.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ProjectsF extends GTDListF {
    
    final String TAG = "PROJECTS_F";
    //String[] columns = {Task.COLUMN_NAME_FOLDER, Task.COLUMN_NAME_DESCRIPTION};

    String[] columns = {Task.COLUMN_NAME_SUBJECT, Task.COLUMN_NAME_DESCRIPTION};
    int[] views = {android.R.id.text1, android.R.id.text2};
    String folder = Task.FOLDER_PROJECTS;
    

    public ProjectsF() {
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i(TAG, "CREATED");
        super.TAG = TAG;
        super.columns = this.columns;
        super.views = this.views;
        super.folder = this.folder;
        super.onActivityCreated(savedInstanceState);
        
    }
    

    
    
    @Override
    void showDetails(long id) {
        
        Intent intent = new Intent(context, ProjectView.class);
        intent.putExtra("index", id);
        context.startActivity(intent);
    }
    
    @Override
    void setAdapter() {
        db.open();
        adapter = new SimpleCursorAdapter(context, 
                android.R.layout.simple_list_item_2, db.listProject(), columns, views, 0);
        adapter.setStringConversionColumn(adapter.getCursor().getColumnIndex(Task.COLUMN_NAME_SUBJECT));
        adapter.setFilterQueryProvider(new MyFilterQueryProvider());
        setListAdapter(adapter);
        db.close();
    }

}
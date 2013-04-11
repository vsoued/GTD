package com.vsoued.gtd;

import com.vsoued.gtd.Tasks.Task;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class NextActionF extends ListFragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */

    String TAG = "NEXT_ACTION_F";
    SimpleCursorAdapter adapter;
    private static GTDDB db;
    Context context;
    public String folder;
    public String[] columns = {Task.COLUMN_NAME_SUBJECT, Task.COLUMN_NAME_DESCRIPTION};
    public int[] views = {android.R.id.text1, android.R.id.text2};
    
    public NextActionF() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "CREATED");
        db = new GTDDB(getActivity());
        context = getActivity();
    }
    
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        db.open();
        showDetails(position);
        db.close();
    }

    void showDetails(long id) {
        
        Intent intent = new Intent(context, Details.class);
        intent.putExtra("index", id);
        context.startActivity(intent);
    }
    
    @Override
    public void onStart(){
        super.onStart();
        Log.i(TAG, "SHOW "+folder);

        db.open();
        adapter = new SimpleCursorAdapter(getActivity(), 
                android.R.layout.simple_list_item_2, db.listTask(Task.FOLDER_ACTIONS), columns, views, 0);
        setListAdapter(adapter);
        db.close();
    }
}

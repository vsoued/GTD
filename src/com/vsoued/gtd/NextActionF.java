package com.vsoued.gtd;

import com.vsoued.gtd.Tasks.Action;
import com.vsoued.gtd.Tasks.Task;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class NextActionF extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor>{
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */

    public static final String ARG_SECTION_NUMBER = "section_number";
    SimpleCursorAdapter adapter;
    int mCurCheckPosition = 0;
    private static GTDDB db;
    
    public NextActionF() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        db = new GTDDB(getActivity());
        // For the cursor adapter, specify which columns go into which views
        String[] columns = {Action.COLUMN_NAME_SUBJECT, Action.COLUMN_NAME_DESCRIPTION};
        int[] views = {android.R.id.text1, android.R.id.text2}; // The TextView in simple_list_item_1
        // Create an empty adapter we will use to display the loaded data.
        // We pass null for the cursor, then update it in onLoadFinished()
        adapter = new SimpleCursorAdapter(getActivity(), 
                android.R.layout.simple_list_item_2, db.listAction(), columns, views, 0);
        setListAdapter(adapter);

        // Prepare the loader.  Either re-connect with an existing one,
        // or start a new one.
        //getLoaderManager().initLoader(0, null, this);

        if (savedInstanceState != null) {
            // Restore last state for checked position.
            mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
        }
    }
    
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", mCurCheckPosition);
    }
    
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        showDetails(position);
    }

    /**
     * Helper function to show the details of a selected item, either by
     * displaying a fragment in-place in the current UI, or starting a
     * whole new activity in which it is displayed.
     */
    void showDetails(int index) {
        mCurCheckPosition = index;

      
//        Otherwise we need to launch a new activity to display
//        the dialog fragment with selected text.
        Intent intent = new Intent();
        intent.setClass(getActivity(), Details.class);
        intent.putExtra("index", index);
        intent.putExtra("table", 1);
        startActivity(intent);
    }
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // TODO Auto-generated method stub
        return new Loader<Cursor>(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
        // TODO Auto-generated method stub
        adapter.swapCursor(arg1);
        
    }

    @Override
    public void onLoaderReset(Loader<Cursor> arg0) {
        // TODO Auto-generated method stub
        adapter.swapCursor(null);
        
    }
}

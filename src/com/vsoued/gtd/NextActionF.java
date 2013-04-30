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

public class NextActionF extends GTDListF {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */

    String TAG = "NEXT_ACTION_F";
    String folder = Task.FOLDER_ACTIONS;
    String[] columns = {Task.COLUMN_NAME_SUBJECT, Task.COLUMN_NAME_PATH};
    int[] views = {android.R.id.text1, android.R.id.text2};
    
    public NextActionF() {
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
}

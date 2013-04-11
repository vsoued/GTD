package com.vsoued.gtd;

import com.vsoued.gtd.Tasks.Task;

import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ReferencesF extends GTDListF {
   
    final String TAG = "REFERENCES_F";
    String[] columns = {Task.COLUMN_NAME_SUBJECT, Task.COLUMN_NAME_DESCRIPTION};
    int[] views = {android.R.id.text1, android.R.id.text2};
    String folder = Task.FOLDER_REFERENCES;


    public ReferencesF() {}
    
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i(TAG,"CREATED");
        super.TAG = TAG;
        super.columns = this.columns;
        super.views = this.views;
        super.folder = this.folder;
        super.onActivityCreated(savedInstanceState);
    }
}

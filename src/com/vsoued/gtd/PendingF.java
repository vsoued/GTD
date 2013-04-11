package com.vsoued.gtd;

import com.vsoued.gtd.Tasks.Task;

import android.os.Bundle;
import android.util.Log;

public class PendingF extends GTDListF {
    
    final String TAG = "PENDING_F";
    String[] columns = {Task.COLUMN_NAME_SUBJECT, Task.COLUMN_NAME_DESCRIPTION};
    int[] views = {android.R.id.text1, android.R.id.text2};
    String folder = Task.FOLDER_PENDING;


    public PendingF() {}
    
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i(TAG,"CREATED");
        super.TAG = TAG;
        super.columns = this.columns;
        super.views = this.views;
        super.folder = this.folder;
        super.onActivityCreated(savedInstanceState);
    }
}

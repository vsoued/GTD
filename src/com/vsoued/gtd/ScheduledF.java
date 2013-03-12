package com.vsoued.gtd;

import com.vsoued.gtd.Tasks.Task;

import android.os.Bundle;

public class ScheduledF extends GTDListF {
   
  
    //int mCurCheckPosition = 0;
    String[] columns = {Task.COLUMN_NAME_SUBJECT, Task.COLUMN_NAME_DESCRIPTION};
    int[] views = {android.R.id.text1, android.R.id.text2};
    String folder = Task.FOLDER_INBOX;

    public ScheduledF() {}
  
    public void onActivityCreated(Bundle savedInstanceState) {
        super.columns = this.columns;
        super.views = this.views;
        super.folder = this.folder;
        super.onActivityCreated(savedInstanceState);
    }
    
}
package com.vsoued.gtd;

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

public abstract class GTDListF extends ListFragment {
    
        public static final String ARG_SECTION_NUMBER = "section_number";
        SimpleCursorAdapter adapter;
        int mCurCheckPosition = 0;
        private static GTDDB db;
        Context context;
        public String folder;
        public String[] columns;
        public int[] views;
        //public boolean change = false;

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            db = new GTDDB(getActivity());
            context = getActivity();
            
            db.open();
            adapter = new SimpleCursorAdapter(getActivity(), 
                    android.R.layout.simple_list_item_2, db.listTask(folder), columns, views, 0);
            setListAdapter(adapter);
            db.close();
        
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
            mCurCheckPosition = position;
            db.open();
            showDetails(id);
            db.close();
        }
        
        /**
         * Helper function to show the details of a selected item, either by
         * displaying a fragment in-place in the current UI, or starting a
         * whole new activity in which it is displayed.
         */
        void showDetails(long id) {
            
        //    Otherwise we need to launch a new activity to display
        //    the dialog fragment with selected text.
            Intent intent = new Intent(context, Details.class);
            //intent.setClass(getActivity(), Details.class);
            intent.putExtra("index", id);
            context.startActivity(intent);
        }

        
        
        @Override
        public void onResume(){
            super.onResume();
            if(true){
                db.open();
                adapter = new SimpleCursorAdapter(getActivity(), 
                        android.R.layout.simple_list_item_2, db.listTask(folder), columns, views, 0);
                setListAdapter(adapter);
                db.close();
            }
        }
        
        @Override
        public void onStart(){
            super.onStart();
            if(true){
                db.open();
                adapter = new SimpleCursorAdapter(getActivity(), 
                        android.R.layout.simple_list_item_2, db.listTask(folder), columns, views, 0);
                setListAdapter(adapter);
                db.close();
            }
        }
        
        
}

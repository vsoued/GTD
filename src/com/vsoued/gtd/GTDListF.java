package com.vsoued.gtd;

import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public abstract class GTDListF extends ListFragment {
    
        String TAG = "GTD_LIST_F";
        SimpleCursorAdapter adapter;
        protected static GTDDB db;
        Context context;
        public String folder;
        public String[] columns;
        public int[] views;

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            db = new GTDDB(getActivity());
            context = getActivity();
        
        }
        
        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            db.open();
            showDetails(id);
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

            setAdapter();
        }
        
        void setAdapter() {
            db.open();
            adapter = new SimpleCursorAdapter(getActivity(), 
                    android.R.layout.simple_list_item_2, db.listTask(folder), columns, views, 0);
            setListAdapter(adapter);
            db.close();
        }
        
        
}

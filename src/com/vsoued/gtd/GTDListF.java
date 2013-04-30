package com.vsoued.gtd;

import com.vsoued.gtd.Tasks.Task;

import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
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
        
        public class MyFilterQueryProvider implements FilterQueryProvider{

            @Override
            public Cursor runQuery(CharSequence constraint) {
                db.open();
                Cursor filter = db.filter(constraint, folder);
                //db.close();
                return filter;
                
            }
            
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
            ListView listv =  getListView();
            //listv.addHeaderView(v)
            listv.setFastScrollEnabled(true);
            listv.setTextFilterEnabled(true);
            
            
            setAdapter();
        }
        
        void setAdapter() {
            db.open();
            adapter = new SimpleCursorAdapter(getActivity(), 
                    android.R.layout.simple_list_item_2, db.listTask(folder), columns, views, 0);
            adapter.setStringConversionColumn(adapter.getCursor().getColumnIndex(Task.COLUMN_NAME_SUBJECT));
            adapter.setFilterQueryProvider(new MyFilterQueryProvider());
            setListAdapter(adapter);
            db.close();
        }
        
        
}

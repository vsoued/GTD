package com.vsoued.gtd;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.vsoued.gtd.Tasks.Task;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.os.Bundle;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
@SuppressLint("ValidFragment")
public class Main extends Activity implements
        ActionBar.OnNavigationListener {
    
    /**
     * The serialization (saved instance state) Bundle key representing the
     * current dropdown position.
     */
    private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
    protected static GTDDB db;
    private static final int[] POSITIONS = {
        R.id.inbox,
        R.id.nextaction,
        R.id.projects,
        R.id.scheduled,
        R.id.pending,
        R.id.references,
        R.id.someday};
    FragmentManager manager = getFragmentManager();
    //private int curPosition;
    private ArrayList<Mail> accounts;
    
    
    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        accounts = new ArrayList<Mail>();

        db = new GTDDB(this);


        SpinnerAdapter mSpinnerAdapter = ArrayAdapter.createFromResource(getActionBarThemedContextCompat(), R.array.folders_array,
                android.R.layout.simple_dropdown_item_1line);
       
        
        // Set up the action bar to show a dropdown list.
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        actionBar.setListNavigationCallbacks(mSpinnerAdapter, this);
        
        //curPosition = 0;
                
       
        
    }

               // TODO Auto-generated method stub
            

    /**
     * Backward-compatible version of {@link ActionBar#getThemedContext()} that
     * simply returns the {@link android.app.Activity} if
     * <code>getThemedContext</code> is unavailable.
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private Context getActionBarThemedContextCompat() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            return getActionBar().getThemedContext();
        } else {
            return this;
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore the previously serialized current dropdown position.
        if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
            getActionBar().setSelectedNavigationItem(
                    savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Serialize the current dropdown position.
        outState.putInt(STATE_SELECTED_NAVIGATION_ITEM, getActionBar()
                .getSelectedNavigationIndex());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_add_account:
                
                Intent intent = new Intent(this, MailAccount.class);
                this.startActivityForResult(intent, 0);
                return true;
            case R.id.menu_add:
                Intent intent1 = new Intent(this, NewTask.class);
                startActivity(intent1);
                return true;
            case R.id.menu_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    protected void onActivityResult(int requestCode, int resultCode,
            Intent data) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String[] auth = data.getStringArrayExtra("auth");
                accounts.add(new Mail(auth[0], auth[1]));
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(int position, long id) {

        FragmentTransaction ft = manager.beginTransaction();
        for (int i : POSITIONS){
            if (i == POSITIONS[position]){
                ft.show(manager.findFragmentById(i));
            } else {
                ft.hide(manager.findFragmentById(i));
            }
            
        }
        ft.commit();
        
       
        
        
//      
        //Log.i("MAIN", "CHANGE FROM "+curPosition+" TO "+position);
        
        //curPosition = position; 
        
        return true;
        
    }


    @Override
    protected void onResume() {
      //db.open();
      super.onResume();
    }

    @Override
    protected void onPause() {
      //db.close();
      super.onPause();
    }
 
}

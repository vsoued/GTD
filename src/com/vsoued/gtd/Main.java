package com.vsoued.gtd;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.vsoued.gtd.Tasks.Accounts;
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
import android.app.ActionBar.Tab;
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
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TabHost;
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
    TabHost host;
    Menu mainmenu;
    SubMenu submenu;
    
    
    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //host = (TabHost) findViewById(android.R.id.tabhost);


        SpinnerAdapter mSpinnerAdapter = ArrayAdapter.createFromResource(getActionBarThemedContextCompat(), R.array.folders_array,
                android.R.layout.simple_dropdown_item_1line);
       
        
        // Set up the action bar to show a dropdown list.
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        actionBar.setListNavigationCallbacks(mSpinnerAdapter, this);
        
        db = new GTDDB(this);
        
        accounts = new ArrayList<Mail>();
        
        db.open();
        Cursor a = db.getAccounts();
        a.moveToFirst();
        while (!a.isAfterLast()){
            accounts.add(new Mail(a.getString(a.getColumnIndex(Accounts.COLUMN_NAME_USER)),
                    a.getString(a.getColumnIndex(Accounts.COLUMN_NAME_PASSWORD))));
            a.moveToNext();
        }
        
        db.close();
        //accounts.add(new Mail("vsoued@gmail.com","kjhkhkjh"));


      
        
        setContentView(R.layout.activity_main);
        
//        if (savedInstanceState != null && savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
//           actionBar.setSelectedNavigationItem(
//                    savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
//        } else {
//            actionBar.setSelectedNavigationItem(0);
//        }
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
        SubMenu submenu = menu.addSubMenu(144, SubMenu.NONE, SubMenu.NONE , "Inbox");
        submenu.getItem().setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        submenu.add(SubMenu.NONE, 100, SubMenu.NONE , "Inbox");
        submenu.findItem(100).setOnMenuItemClickListener(
                new OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        FragmentTransaction ft = manager.beginTransaction();
                        //host.setVisibility(2);
                        Log.i("main", "transaction");
                        for (int i : POSITIONS){
                            if (i == POSITIONS[0]){
                                ft.show(manager.findFragmentById(i));
                            } else {
                                ft.hide(manager.findFragmentById(i));
                            }
                        }
                        ft.commit();
//                      sub.setHeaderTitle("Account");
                        return true;
                    }
                });

        int id = 0;
        for (Mail m: accounts){
            submenu.add(SubMenu.NONE, id, SubMenu.NONE,m._user);
            submenu.findItem(id).setOnMenuItemClickListener(
                    new OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            FragmentTransaction ft = manager.beginTransaction();
                            Fragment f = manager.findFragmentByTag(accounts.get(item.getItemId())._user);
                            if (manager.findFragmentByTag(accounts.get(item.getItemId())._user) == null){
                                f = new MailFragment(accounts.get(item.getItemId()));
                                ft.add(R.id.container, f, accounts.get(item.getItemId())._user);
                            } else {
                                ft.show(f);
                            }
                            for (int i : POSITIONS){
                                ft.hide(manager.findFragmentById(i));
                            }
                            ft.commit();
                            return true;
                        }
                    });
            id++;
        }
       if (getActionBar().getSelectedNavigationIndex() != 0){
           menu.setGroupVisible(144, false);
       }
        mainmenu = menu;
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
                intent1.putExtra("folder", getActionBar().getSelectedNavigationIndex());
                startActivity(intent1);
                return true;
            case R.id.menu_settings:
                return true;
            case R.id.menu_search:
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(getCurrentFocus(), InputMethodManager.SHOW_IMPLICIT);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    protected void onActivityResult(int requestCode, int resultCode,
            Intent data) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                invalidateOptionsMenu();                
                String[] auth = data.getStringArrayExtra("auth");
                accounts.add(new Mail(auth[0], auth[1]));
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(int position, long id) {

        //getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
        
        
        if (position == 0){
            mainmenu.setGroupVisible(144, true);
            
          
        } else {
            mainmenu.setGroupVisible(144, false);
        }
////            host.setVisibility(0);
////            Tab tab = getActionBar().newTab()
////                    .setText("Tasks");
////                    //.setTabListener(this);
////            getActionBar().addTab(tab);
//            
//            for (Mail m: accounts){
////                tab = getActionBar().newTab()
////                        .setText(m._user);
////                      //  .setTabListener(this);
////                getActionBar().addTab(tab);
////                
//            }
//        } else {
            FragmentTransaction ft = manager.beginTransaction();
            //host.setVisibility(2);
            Log.i("main", "transaction");
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


 
}

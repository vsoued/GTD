package com.vsoued.gtd;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.vsoued.gtd.Tasks.Task;

public class MailAccount extends Activity {
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);
     
    }
    
   
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_new_task, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_discard:
                finish();
                return true;
            case R.id.menu_save:
                Intent data = new Intent();
                data.putExtra("auth", new String[]{
                        ((EditText) findViewById(R.id.username)).getText().toString(),
                        ((EditText) findViewById(R.id.password)).getText().toString()}); 
                setResult(RESULT_OK, data); 
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        
    }
}

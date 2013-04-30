package com.vsoued.gtd;


import com.vsoued.gtd.Tasks.Task;

import android.app.ActionBar;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

public class NewTask extends Activity {
    GTDDB db;
    private String folder;
    Spinner spinner;
    Spinner spinner2;
    RatingBar bar;
    SparseIntArray map;
    String path;
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        db = new GTDDB(getBaseContext());
        path = "";
        spinner = (Spinner) findViewById(R.id.spinner1);    
        // Create an ArrayAdapter using the string array and a default spinner layout
        SpinnerAdapter adapter = ArrayAdapter.createFromResource(this, R.array.folders_array, 
                android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        
        spinner2 = (Spinner) findViewById(R.id.spinner2);    
        // Create an ArrayAdapter using the string array and a default spinner layout
        db.open();
        Cursor c = db.projectSpinner();
        SpinnerAdapter adapter2 = new SimpleCursorAdapter (this,android.R.layout.simple_spinner_dropdown_item, c, new String[]{Task.COLUMN_NAME_SUBJECT}, new int[]{android.R.id.text1}, 0);
        spinner2.setAdapter(adapter2);
        map = new SparseIntArray();
        int x = -1;
        c.moveToPosition(-1);
        while (!c.isLast()){
            c.moveToNext();
            x++;
            map.put(x, c.getInt(c.getColumnIndex(Task._ID))); 
        }
        
        db.close();
        
        
        bar = (RatingBar) findViewById(R.id.ratingBar1);

        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
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
                int id = map.get(spinner2.getSelectedItemPosition());
                db.open();
                Log.i("NEW TASK", "project "+id);
                while (id > 0){
                    Log.i("NEW TASK", "project "+id);
                    Cursor c = db.oneProject(id);
                    c.moveToFirst();
                    path = c.getString(c.getColumnIndex(Task.COLUMN_NAME_SUBJECT)) + "/ "+ path;
                    id = c.getInt(c.getColumnIndex(Task.COLUMN_NAME_PROJECT_ID));
                    Log.i("NEW TASK", "path "+path);
                }
                folder = Task.FOLDERS_ARRAY[spinner.getSelectedItemPosition()];
                Log.i("NEW TASK", "PUTTING TO PROJECT "+map.get(spinner2.getSelectedItemPosition()));
                    db.createTask(((EditText)findViewById(R.id.textbox1)).getText().toString(), 
                            ((EditText)findViewById(R.id.textbox2)).getText().toString(), folder,(int)bar.getRating(), map.get(spinner2.getSelectedItemPosition()),path);
                db.close();
                
//                Mail m = new Mail("vsoued@gmail.com", "hek:190688"); 
//                
//                String[] toArr = {"vsoued@gmail.com", "helfon.soued@gmail.com"}; 
//                m.setTo(toArr); 
//                m.setFrom("vsoued@gmail.com"); 
//                m.setSubject("This is an email sent using my Mail JavaMail wrapper from an Android device."); 
//                m.setBody("Email body."); 
//           
//                try { 
//                  //m.addAttachment("/sdcard/filelocation"); 
//           
//                  if(m.send()) { 
//                    Toast.makeText(this, "Email was sent successfully.", Toast.LENGTH_LONG).show(); 
//                  } else { 
//                    Toast.makeText(this, "Email was not sent.", Toast.LENGTH_LONG).show(); 
//                  } 
//                } catch(Exception e) { 
//                  //Toast.makeText(MailApp.this, "There was a problem sending the email.", Toast.LENGTH_LONG).show(); 
//                  Log.e("MailApp", "Could not send email", e); 
//                } 
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        
    }
}

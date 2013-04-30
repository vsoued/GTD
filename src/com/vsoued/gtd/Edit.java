package com.vsoued.gtd;

import java.util.HashMap;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;
import com.vsoued.gtd.Tasks.Task;

public class Edit extends Activity{
    GTDDB db;
    long id;
    public String folder;
    ContentValues values;
    Spinner spinner;
    Spinner spinner2;
    RatingBar bar;
    EditText text1;
    EditText text2;
    SparseIntArray map2;
    SparseIntArray map3;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        
        db = new GTDDB(getBaseContext());
        values = new ContentValues();
        
        id = this.getIntent().getLongExtra("index", 1);
        Log.i("EDIT", "EDIT INDEX "+id );
        text1 = ((EditText) findViewById(R.id.textbox1));
        text2 = ((EditText) findViewById(R.id.textbox2));
        
        spinner = (Spinner) findViewById(R.id.spinner1); 
        spinner2 = (Spinner) findViewById(R.id.spinner2);   
        SpinnerAdapter spinadapter = ArrayAdapter.createFromResource(this, R.array.folders_array, 
                android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinadapter);
        
        bar = (RatingBar) findViewById(R.id.ratingBar1);
        
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        for (int i = 0; i< Task.FOLDERS_ARRAY.length; i++){
            map.put(Task.FOLDERS_ARRAY[i], i);
        }
       
        db.open();
        Cursor c = db.showTask(id);
        
        c.moveToFirst();
        text1.setText(c.getString(c.getColumnIndex(Task.COLUMN_NAME_SUBJECT)));
        text2.setText(c.getString(c.getColumnIndex(Task.COLUMN_NAME_DESCRIPTION)));
        spinner.setSelection(map.get(c.getString(c.getColumnIndex(Task.COLUMN_NAME_FOLDER))));
        bar.setRating((float)c.getInt(c.getColumnIndex(Task.COLUMN_NAME_PRIORITY)));
        
        
        Cursor c2 = db.projectSpinner();
        SpinnerAdapter adapter2 = new SimpleCursorAdapter (this,android.R.layout.simple_spinner_dropdown_item, 
                c2, new String[]{Task.COLUMN_NAME_SUBJECT}, new int[]{android.R.id.text1}, 0);
        
        map2 = new SparseIntArray();
        map3 = new SparseIntArray();
         
        int x = -1;
        c2.moveToPosition(-1);
        while (!c2.isLast()){
            c2.moveToNext();
            x++;
            map2.put(x, c2.getInt(c2.getColumnIndex(Task._ID))); 
            map3.put(c2.getInt(c2.getColumnIndex(Task._ID)), x); 
        }

        Log.i("EDIT", "TASK IS IN PROJECT "+ c.getInt(c.getColumnIndex(Task.COLUMN_NAME_PROJECT_ID)));
        spinner2.setAdapter(adapter2);
        spinner2.setSelection(map3.get(c.getInt(c.getColumnIndex(Task.COLUMN_NAME_PROJECT_ID))));
        
        
        db.close();

     
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
                
                int pid = map2.get(spinner2.getSelectedItemPosition());
                String path = "";
                db.open();
                while (pid > 0){
                    Cursor c = db.oneProject(pid);
                    c.moveToFirst();
                    path = c.getString(c.getColumnIndex(Task.COLUMN_NAME_SUBJECT)) + "/ "+ path;
                    pid = c.getInt(c.getColumnIndex(Task.COLUMN_NAME_PROJECT_ID));
                }
//                ((Spinner) findViewById(R.id.spinner1)).get
               
                    values.put(Task.COLUMN_NAME_PROJECT_ID, map2.get(spinner2.getSelectedItemPosition()));
                    values.put(Task.COLUMN_NAME_PATH, path);
                    values.put(Task.COLUMN_NAME_SUBJECT, (text1.getText().toString()));  
                    values.put(Task.COLUMN_NAME_DESCRIPTION, (text2.getText().toString()));
                    values.put(Task.COLUMN_NAME_FOLDER, Task.FOLDERS_ARRAY[spinner.getSelectedItemPosition()]);
                    values.put(Task.COLUMN_NAME_PRIORITY, (int)bar.getRating());
                    db.updateTask(id, values);
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

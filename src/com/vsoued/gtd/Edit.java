package com.vsoued.gtd;

import java.util.HashMap;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;

import com.vsoued.gtd.Tasks.Task;

public class Edit extends Activity{
    GTDDB db;
    long id;
    String[] fields;
    int[] views;
    public String folder;
    HashMap<String, String>  values = new HashMap<String, String>();  
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
        
        
        db = new GTDDB(getBaseContext());
        fields = new String[]  {Task.COLUMN_NAME_SUBJECT, Task.COLUMN_NAME_DESCRIPTION};
        views = new int[] {R.id.textbox1, R.id.textbox2};
        id = this.getIntent().getLongExtra("index", 1);
        db.open();
        Cursor c = db.showTask(id);
//        ((EditText) findViewById(R.id.textbox1)).setText(c.getString(0));
//        ((EditText) findViewById(R.id.textbox2)).setText(c.getString(1));
        SimpleCursorAdapter prjName = new SimpleCursorAdapter(this, R.layout.activity_edit_task, c, fields, views, 0);
        setContentView(R.layout.activity_edit_task);
        ((ListView) findViewById(R.id.edit_list)).setAdapter(prjName);
        db.close();
        Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        //spinner.setOnItemSelectedListener(new SpinnerHandler());
        
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence> (this, android.R.layout.simple_spinner_item, Task.FOLDER_ARRAY);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        //spinner.setAdapter(adapter);
        
   
        
        //db.close();
        
        
        
       
        
     // Create an ArrayAdapter using the string array and a default spinner layout
     
    }
    
    private class SpinnerHandler implements OnItemSelectedListener{
        
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int pos,
                long id) {
            // TODO Auto-generated method stub
            folder = Task.FOLDER_ARRAY[pos];
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // TODO Auto-generated method stub
            folder = Task.FOLDER_INBOX;
        }
        
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
//                ((Spinner) findViewById(R.id.spinner1)).get
                values.put(Task.COLUMN_NAME_SUBJECT, ((EditText)findViewById(R.id.textbox1)).getText().toString());  
                values.put(Task.COLUMN_NAME_DESCRIPTION, ((EditText)findViewById(R.id.textbox2)).getText().toString());
                values.put(Task.COLUMN_NAME_FOLDER, Task.FOLDER_INBOX); 
                db.open();
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

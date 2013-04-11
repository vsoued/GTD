package com.vsoued.gtd;

import com.vsoued.gtd.Tasks.Task;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class NewTask extends Activity {
    GTDDB db;
    private String folder;
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        db = new GTDDB(getBaseContext());
        Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        spinner.setOnItemSelectedListener(new SpinnerHandler());
     // Create an ArrayAdapter using the string array and a default spinner layout
     ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence> (this, android.R.layout.simple_spinner_item, Task.FOLDER_ARRAY);
     // Specify the layout to use when the list of choices appears
     adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
     // Apply the adapter to the spinner
     spinner.setAdapter(adapter);

        // Set up the action bar to show a dropdown list.
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
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
                db.open();
                db.createTask(((EditText)findViewById(R.id.textbox1)).getText().toString(), 
                        ((EditText)findViewById(R.id.textbox2)).getText().toString(), folder);
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

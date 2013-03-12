package com.vsoued.gtd;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.mail.Message;
import javax.mail.MessagingException;




import android.app.ActionBar;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;


public class MailInbox extends ListFragment{
    int[] views = {android.R.id.text1, android.R.id.text2};
    final String ACCOUNT_TYPE_GOOGLE = "com.google";
    final String[] FEATURES_MAIL = {
            "service_mail"
    };
    String selectedAccount;
    Context context;
    ArrayAdapter<String> adapter;
    Mail mail = new Mail("vsoued@gmail.com", "hek:190688");
    
    public MailInbox() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i("MAIL_INBOX", "ON ACTIVITY CREATED");
        context = getActivity();
        adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1);
        super.onActivityCreated(savedInstanceState);
        
//        Void[] n = {null};
//        String[] msgs={};
//        try {
//            msgs = mail.new GetMail().execute(n).get();
//            
//            Log.i("MAIL_INBOX", "AFTER GET_MAIL");
//        } catch (InterruptedException e) {
//            // TODO Auto-generated catch block
//            Log.e("MAIL_INBOX", "ERROR");
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            // TODO Auto-generated catch block
//            Log.e("MAIL_INBOX", "ERROR");
//            e.printStackTrace();
//        }
//        
//        adapter = new ArrayAdapter<String>(getActivity(), 
//                android.R.layout.simple_list_item_1, msgs);
//       
//        
////        Log.i("MAIL_INBOX", "adapter created");
////
////        
//        setListAdapter(adapter);
        
        

        // Set up the action bar to show a dropdown list.
        final ActionBar actionBar = getActivity().getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
      
    }  
    
    @Override
    public void onResume(){
            super.onResume();
            
            
//            Void[] n = {null};
//            String[] msgs={};
//            try {
//                msgs = mail.new GetMail().execute(n).get();
//                
//                Log.i("MAIL_INBOX", "AFTER GET_MAIL");
//            } catch (InterruptedException e) {
//                // TODO Auto-generated catch block
//                Log.e("MAIL_INBOX", "ERROR");
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                // TODO Auto-generated catch block
//                Log.e("MAIL_INBOX", "ERROR");
//                e.printStackTrace();
//            }
//            
//            adapter.addAll(msgs);
////            = new ArrayAdapter<String>(getActivity(), 
////                    android.R.layout.simple_list_item_1, msgs);
//            
//            setListAdapter(adapter);
            
            Void[] n = {null};
            String[] msgs={};
            try {
                msgs = mail.new GetMail().execute(n).get();
                
                Log.i("MAIL_INBOX", "AFTER GET_MAIL");
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                Log.e("MAIL_INBOX", "ERROR");
                e.printStackTrace();
            } catch (ExecutionException e) {
                // TODO Auto-generated catch block
                Log.e("MAIL_INBOX", "ERROR");
                e.printStackTrace();
            }
            
//            adapter = new ArrayAdapter<String>(getActivity(), 
//                    android.R.layout.simple_list_item_1, msgs);
           adapter.addAll(msgs);
            
//            Log.i("MAIL_INBOX", "adapter created");
    //
//            
            setListAdapter(adapter);

            
        
    }
}

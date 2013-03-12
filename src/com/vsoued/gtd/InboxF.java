package com.vsoued.gtd;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.mail.Message;

import com.vsoued.gtd.Tasks.Task;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public class InboxF extends GTDListF {
//        int mCurCheckPosition = 0;
       
        String[] columns = {Task.COLUMN_NAME_SUBJECT, Task.COLUMN_NAME_DESCRIPTION};
        int[] views = {android.R.id.text1, android.R.id.text2};
        String folder = Task.FOLDER_INBOX;
        final String ACCOUNT_TYPE_GOOGLE = "com.google";
        final String[] FEATURES_MAIL = {
                "service_mail"
        };
        String selectedAccount;
        
        public InboxF() {
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            
            super.columns = this.columns;
            super.views = this.views;
            super.folder = this.folder;
            super.onActivityCreated(savedInstanceState);
            
            
        }  
        
        @Override
        public void onResume(){
        
           
                super.onResume();
                // Get the account list, and pick the first one
                AccountManager.get(context).getAccountsByTypeAndFeatures(ACCOUNT_TYPE_GOOGLE, FEATURES_MAIL,
                        new AccountManagerCallback() {
                            @Override
                            public void run(AccountManagerFuture future) {
                                Account[] accounts = null;
                                try {
                                    accounts = (Account[]) future.getResult();
                                    if (accounts != null && accounts.length > 0) {
                                        selectedAccount = accounts[0].name;
                                        //queryLabels(selectedAccount);
                                    }

                                } catch (OperationCanceledException oce) {
                                    // TODO: handle exception
                                } catch (IOException ioe) {
                                    // TODO: handle exception
                                } catch (AuthenticatorException ae) {
                                    // TODO: handle exception
                                }
                            }
                        }, null );

            
            super.onResume();
                
            
        }
}
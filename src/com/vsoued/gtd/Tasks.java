package com.vsoued.gtd;

import java.util.HashMap;

import android.provider.BaseColumns;

public final class Tasks {
  
    public static final class Task implements BaseColumns {
  
        /**
         * The table names
         */
        public static final String TABLE_NAME_TASKS = "tasks";
  
        /*
         * Column definitions
         */

        /**
         * Column name for the subject of the task
         * <P>Type: TEXT</P>
         */
        public static final String COLUMN_NAME_SUBJECT = "subject";

        /**
         * Column name of the task description
         * <P>Type: TEXT</P>
         */
        public static final String COLUMN_NAME_DESCRIPTION = "description";

        /**
         * Column name for the creation timestamp
         * <P>Type: INTEGER (long from System.curentTimeMillis())</P>
         */
        public static final String COLUMN_NAME_CREATE_DATE = "created";

        /**
         * Column name for the modification timestamp
         * <P>Type: INTEGER (long from System.curentTimeMillis())</P>
         */
        public static final String COLUMN_NAME_MODIFICATION_DATE = "modified";
        
        /**
         * Column name for the folder the task is in
         * <P>Type: TEXT</P>
         */
        public static final String COLUMN_NAME_FOLDER = "folder";
        
        /**
         * Column name for the project the task is in
         * <P>Type: INTEGER</P>
         */
        public static final String COLUMN_NAME_PROJECT_ID = "project_id";
        
        /**
         * Column name for the folder the task is in
         * <P>Type: TEXT</P>
         */
        public static final String COLUMN_NAME_TAG = "tag";
        
        /**
         * Column name for the project the task is in
         * <P>Type: INTEGER</P>
         */
        public static final String COLUMN_NAME_PRIORITY = "priority";
        
        /**
         * Column name for the folder the task is in
         * <P>Type: TEXT</P>
         */
        public static final String COLUMN_NAME_CONTACT = "contact";

        /**
         * Column name for the project the task is in
         * <P>Type: INTEGER</P>
         */
        public static final String COLUMN_NAME_DATE = "date";
        
        /**
         * Column name for the project the task is in
         * <P>Type: INTEGER</P>
         */
        public static final String COLUMN_NAME_TIME = "time";
        
        
        
        
        
        /**
         * Folder tags
         */
        public static final String FOLDER_INBOX = "inbox";
        public static final String FOLDER_ACTIONS = "actions";
        public static final String FOLDER_SCHEDULED = "scheduled";
        public static final String FOLDER_PENDING = "pending";
        public static final String FOLDER_SOMEDAY = "someday";
        public static final String FOLDER_REFERENCES = "references";
        
        public static final String[] FOLDER_ARRAY = {
            FOLDER_INBOX,
            FOLDER_ACTIONS,
            FOLDER_SCHEDULED,
            FOLDER_PENDING,
            FOLDER_SOMEDAY,
            FOLDER_REFERENCES
            };
        
        /**
         * Action tags
         */
        public static final String TAG_CALL = "call";
        public static final String TAG_ERRAND = "errand";
        public static final String TAG_COMPUTER = "computer";
        public static final String TAG_HOME = "home";
        public static final String TAG_OFFICE = "office";
        public static final String TAG_EMAIL = "email";
        public static final String TAG_MEETING = "meeting";
    }
    
    public static final class Project implements BaseColumns {
        
        // This class cannot be instantiated
        private Project() {}

        /**
         * The table names offered by this provider
         */
        
        public static final String TABLE_NAME_PROJECTS = "projects";

        /*
         * Column definitions
         */

        /**
         * Column name for the project names
         * <P>Type: TEXT</P>
         */
        public static final String COLUMN_NAME_PROJECT_NAME = "project_name";
        
        /**
         * Column name for the project descritption
         * <P>Type: TEXT</P>
         */
        public static final String COLUMN_NAME_PROJECT_DESCRIPTION = "project_description";
        
        /**
         * Column name for the creation timestamp
         * <P>Type: INTEGER (long from System.curentTimeMillis())</P>
         */
        public static final String COLUMN_NAME_CREATE_DATE = "created";

        /**
         * Column name for the modification timestamp
         * <P>Type: INTEGER (long from System.curentTimeMillis())</P>
         */
        public static final String COLUMN_NAME_MODIFICATION_DATE = "modified";
        
        /**
         * Column name for the action priority
         * <P>Type: INTEGER</P>
         */
        public static final String COLUMN_NAME_PRIORITY = "priority";
        
        /**
         * Column name for the project the task is in
         * <P>Type: INTEGER</P>
         */
        public static final String COLUMN_NAME_PROJECT_ID = "project_id";

        
    }
        
}


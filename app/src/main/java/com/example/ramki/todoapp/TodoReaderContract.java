package com.example.ramki.todoapp;

import android.provider.BaseColumns;

public final class TodoReaderContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public TodoReaderContract() {}

    /* Inner class that defines the table contents */
    public static abstract class TodoEntry implements BaseColumns {
        public static final String TABLE_NAME = "todos";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_CREATED_AT = "created_at";
        public static final String COLUMN_NAME_DUE_DATE = "due_date";
        public static final String COLUMN_NAME_DELETED = "deleted";
    }
}
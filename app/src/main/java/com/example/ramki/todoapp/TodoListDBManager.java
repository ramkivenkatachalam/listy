package com.example.ramki.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.ramki.todoapp.TodoReaderContract.TodoEntry;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TodoListDBManager extends SQLiteOpenHelper implements TodoListManager{
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "todo.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String DATE_TYPE = " INTEGER";
    private static final String BOOLEAN_TYPE = " INTEGER";
    private static final String NOT_NULL = " NOT NULL";

    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
        "CREATE TABLE " + TodoEntry.TABLE_NAME + " (" +
            TodoEntry._ID + " INTEGER PRIMARY KEY," +
            TodoEntry.COLUMN_NAME_TITLE + TEXT_TYPE + NOT_NULL + COMMA_SEP +
            TodoEntry.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
            TodoEntry.COLUMN_NAME_CREATED_AT + DATE_TYPE + NOT_NULL + COMMA_SEP +
            TodoEntry.COLUMN_NAME_DUE_DATE + DATE_TYPE + COMMA_SEP +
            TodoEntry.COLUMN_NAME_DELETED + BOOLEAN_TYPE + NOT_NULL +
        " )";

    private static final String SQL_DELETE_ENTRIES =
        "DROP TABLE IF EXISTS " + TodoEntry.TABLE_NAME;

    private final SQLiteDatabase todoDB;

    public TodoListDBManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        todoDB = this.getWritableDatabase();

    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */
    @Override
    public List<TodoItem> readItems() throws TodoListManagerException {
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
            TodoEntry._ID,
            TodoEntry.COLUMN_NAME_TITLE,
            TodoEntry.COLUMN_NAME_DESCRIPTION,
            TodoEntry.COLUMN_NAME_CREATED_AT,
            TodoEntry.COLUMN_NAME_DUE_DATE
        };

        String selection =  TodoEntry.COLUMN_NAME_DELETED + "= 0";
        // How you want the results sorted in the resulting Cursor
        String sortOrder = TodoEntry.COLUMN_NAME_DUE_DATE;

        Cursor cursor = todoDB.query(
            TodoEntry.TABLE_NAME,                     // The table to query
            projection,                               // The columns to return
//            null, null,
            selection,                                // The columns for the WHERE clause
            new String[] {},                   // The values for the WHERE clause
            null,                                     // don't group the rows
            null,                                     // don't filter by row groups
            sortOrder                                 // The sort order
        );

        cursor.moveToFirst();
        Log.d("loaded: ",  cursor.getCount() +" todos from DB");
        List<TodoItem> res = new ArrayList<TodoItem>();
        while (!cursor.isAfterLast()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(TodoEntry._ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(
                TodoEntry.COLUMN_NAME_TITLE));
            String desc = cursor.getString(cursor.getColumnIndexOrThrow(
                TodoEntry.COLUMN_NAME_DESCRIPTION));
            Date createdAt = new Date(cursor.getLong(cursor.getColumnIndexOrThrow(
                TodoEntry.COLUMN_NAME_CREATED_AT)));
            Long dueDateLong = cursor.getLong(cursor.getColumnIndexOrThrow(
                TodoEntry.COLUMN_NAME_DUE_DATE));
            Date dueDate = null;
            if (dueDateLong != null) {
                dueDate = new Date(dueDateLong);
            }
            TodoItem loadedTodo = new TodoItem(id, title, desc, createdAt, dueDate);
            Log.d("TodoListDBManager", "loaded :" + loadedTodo);
            res.add(loadedTodo);
            cursor.moveToNext();
        }
        return res;
    }

    @Override
    public TodoItem addItem(TodoItem todo) throws TodoListManagerException {
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(TodoEntry.COLUMN_NAME_TITLE, todo.getTitle());
        values.put(TodoEntry.COLUMN_NAME_DESCRIPTION, todo.getDesc());
        values.put(TodoEntry.COLUMN_NAME_CREATED_AT, new Date().getTime());
        values.put(TodoEntry.COLUMN_NAME_DUE_DATE, todo.getDueDateLong());
        values.put(TodoEntry.COLUMN_NAME_DELETED, false);
        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = todoDB.insert(
            TodoEntry.TABLE_NAME,
            null,
            values);
        todo.setID(newRowId);
        Log.d("inserted item: ", todo.toString());
        return todo;
    }

    @Override
    public void deleteItem(TodoItem todo) throws TodoListManagerException {
        todo.setDeleted(1);
        updateItem(todo);
    }

    @Override
    public int updateItem(TodoItem todo) throws TodoListManagerException {
        ContentValues values = new ContentValues();
        values.put(TodoEntry._ID, todo.getID());
        values.put(TodoEntry.COLUMN_NAME_TITLE, todo.getTitle());
        values.put(TodoEntry.COLUMN_NAME_DESCRIPTION, todo.getDesc());
        values.put(TodoEntry.COLUMN_NAME_DUE_DATE, todo.getDueDateLong());
        values.put(TodoEntry.COLUMN_NAME_DELETED, todo.getDeleted());

        // updating row
        return todoDB.update(TodoEntry.TABLE_NAME, values, TodoEntry._ID + " = ?",
            new String[] { String.valueOf(todo.getID()) });
    }

}
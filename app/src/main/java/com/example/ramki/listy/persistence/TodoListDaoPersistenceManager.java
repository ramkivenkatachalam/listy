package com.example.ramki.listy.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.ramki.listy.TodoListManagerException;
import com.example.ramki.listy.model.DaoMaster;
import com.example.ramki.listy.model.DaoSession;
import com.example.ramki.listy.model.TodoEntry;
import com.example.ramki.listy.model.TodoEntryDao;
import com.example.ramki.listy.model.TodoEntryDao.Properties;


import java.util.List;

public class TodoListDaoPersistenceManager implements TodoListPersistenceManager {

    public static final String DATABASE_NAME = "todoentry.db";
    private final SQLiteDatabase todoDB;
    private final DaoSession daoSession;
    private final TodoEntryDao todoEntryDao;


    public TodoListDaoPersistenceManager(Context context) {
        todoDB = new DaoMaster.DevOpenHelper(context, DATABASE_NAME, null).getWritableDatabase();
        final DaoMaster daoMaster = new DaoMaster(todoDB);
        daoSession = daoMaster.newSession();
        todoEntryDao = daoSession.getTodoEntryDao();
    }

    public void close() {
        todoDB.close();
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */
    @Override
    public List<TodoEntry> readItems() throws TodoListManagerException {
        return todoEntryDao.queryBuilder().where(Properties.Deleted.eq(false)).orderDesc(
            Properties.Due).list();
    }

    @Override
    public TodoEntry addItem(TodoEntry todo) throws TodoListManagerException {
        long newRowId = todoEntryDao.insert(todo);
        todo.setId(newRowId);
        Log.d("inserted item: ", todo.toString());
        return todo;
    }

    @Override
    public void deleteItem(TodoEntry todo) throws TodoListManagerException {
        todo.setDeleted(true);
        todoEntryDao.update(todo);
    }

    @Override
    public int updateItem(TodoEntry todo) throws TodoListManagerException {
        todoEntryDao.update(todo);
        return (int) todo.getId().longValue();
    }

}
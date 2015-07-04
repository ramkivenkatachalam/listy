package com.example.ramki.listy.model;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.example.ramki.listy.model.TodoEntry;

import com.example.ramki.listy.model.TodoEntryDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig todoEntryDaoConfig;

    private final TodoEntryDao todoEntryDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        todoEntryDaoConfig = daoConfigMap.get(TodoEntryDao.class).clone();
        todoEntryDaoConfig.initIdentityScope(type);

        todoEntryDao = new TodoEntryDao(todoEntryDaoConfig, this);

        registerDao(TodoEntry.class, todoEntryDao);
    }
    
    public void clear() {
        todoEntryDaoConfig.getIdentityScope().clear();
    }

    public TodoEntryDao getTodoEntryDao() {
        return todoEntryDao;
    }

}
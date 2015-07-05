package com.example.ramki.listy.persistence;

import com.example.ramki.listy.model.TodoEntry;


import java.util.List;

/**
 * Interface for todolist persistence
 */
public interface TodoListPersistenceManager {

    List<TodoEntry> readItems() throws TodoListPersistenceManagerException;

    TodoEntry addItem(TodoEntry todo) throws TodoListPersistenceManagerException;

    void deleteItem(TodoEntry todo) throws TodoListPersistenceManagerException;

    int updateItem(TodoEntry todo) throws TodoListPersistenceManagerException;
    void close();
}
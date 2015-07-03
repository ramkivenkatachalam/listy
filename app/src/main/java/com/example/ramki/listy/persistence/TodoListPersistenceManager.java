package com.example.ramki.listy.persistence;

import com.example.ramki.listy.TodoListManagerException;
import com.example.ramki.listy.model.TodoEntry;


import java.util.List;

/**
 * Interface for todolist persistence
 */
public interface TodoListPersistenceManager {
    List<TodoEntry> readItems() throws TodoListManagerException;
    TodoEntry addItem(TodoEntry todo) throws TodoListManagerException;
    void deleteItem(TodoEntry todo) throws TodoListManagerException;
    int updateItem(TodoEntry todo) throws TodoListManagerException;
    void close();
}
package com.example.ramki.listy;

import java.util.List;

/**
 * Created by ramki on 6/27/15.
 */
public interface TodoListManager {
    public List<TodoItem> readItems() throws TodoListManagerException;
    public TodoItem addItem(TodoItem todo) throws TodoListManagerException;
    public void deleteItem(TodoItem todo) throws TodoListManagerException;
    int updateItem(TodoItem todo) throws TodoListManagerException;
}
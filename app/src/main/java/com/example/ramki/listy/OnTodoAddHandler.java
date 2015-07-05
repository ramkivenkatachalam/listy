package com.example.ramki.listy;

import com.example.ramki.listy.model.TodoEntry;

/**
 * Activity needs to implement this interface to handle OnTodoAdd callback from fragment
 */
public interface OnTodoAddHandler {

    void onTodoAdd(TodoEntry todo);

}



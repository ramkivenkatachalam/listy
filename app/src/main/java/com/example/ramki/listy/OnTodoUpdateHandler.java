package com.example.ramki.listy;

import com.example.ramki.listy.model.TodoEntry;

/**
 * Activity needs to implement this interface to handle OnTodoUpdate callback from fragment
 */

public interface OnTodoUpdateHandler {

    void onTodoUpdate(TodoEntry todo);

}



package com.example.ramki.listy;

import com.example.ramki.listy.model.TodoEntry;

/**
 * Activity needs to implement this interface to handle OnTodoDelete callback from fragment
 */

public interface OnTodoDeleteHandler {

    void onTodoDelete(TodoEntry deleted);

}



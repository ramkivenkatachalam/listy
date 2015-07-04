package com.example.ramki.listy;

import android.support.v4.app.Fragment;

import com.example.ramki.listy.model.TodoEntry;


import java.util.List;

/**
 * Fragment that displays a list of todos. Calls back handlers
 * for any actions on the list (select, add or delete)
 */

public abstract class ITodoListFragment extends Fragment {

    // set todolist to display
    public abstract void setTodoList(List<TodoEntry> todoList);

    // setup handlers for all events
    public abstract void setOnAddHandler(OnTodoAddHandler handler);

    public abstract void setOnSelectHandler(OnTodoSelectHandler handler);

    public abstract void setOnDeleteHandler(OnTodoDeleteHandler handler);
}

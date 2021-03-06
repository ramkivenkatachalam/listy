package com.example.ramki.listy;

import android.support.v4.app.Fragment;

import com.example.ramki.listy.model.TodoEntry;

/**
 * Fragment for display details of todoEntry. Callsback handler when todoEntry is updated
 */
public abstract class ITodoDetailFragment extends Fragment {

    // set TodoEntry for display
    public abstract void setTodoEntry(TodoEntry todoEntry);

    // setup handlers for callback
    public abstract void setOnUpdateHandler(OnTodoUpdateHandler handler);
    public abstract void setOnDeleteHandler(OnTodoDeleteHandler handler);

    // is called when the Fragment is no longer in view, more efficient to
    // check for updates once instead of for any ui updates
    public abstract void checkForUpdates();
}

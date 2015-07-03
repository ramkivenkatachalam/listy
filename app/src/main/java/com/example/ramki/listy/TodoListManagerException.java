package com.example.ramki.listy;

/**
 * Created by ramki on 6/27/15.
 */
public class TodoListManagerException extends Exception {

    public TodoListManagerException(String msg) {
        super(msg);
    }

    public TodoListManagerException(Exception e) {
        super(e);
    }
}

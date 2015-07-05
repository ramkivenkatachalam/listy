package com.example.ramki.listy.persistence;


public class TodoListPersistenceManagerException extends Exception {

    public TodoListPersistenceManagerException(String msg) {
        super(msg);
    }

    public TodoListPersistenceManagerException(Exception e) {
        super(e);
    }
}

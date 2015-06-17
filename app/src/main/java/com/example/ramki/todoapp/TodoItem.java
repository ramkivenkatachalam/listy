package com.example.ramki.todoapp;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ramki on 6/16/15.
 */
public class TodoItem implements Comparable<TodoItem>, Serializable {
    private String desc;
    private Date dueDate;
    private Date createdAt;


    public TodoItem(String desc) {
        this.createdAt = new Date();
        this.desc = desc;
    }

    public TodoItem(String desc, Date dueDate) {
        this.createdAt = new Date();
        this.desc = desc;
        this.dueDate = dueDate;
    }

    public TodoItem() {

    }

    @Override
    public String toString() {
        return desc +" by " + dueDate;
    }

    public String getDesc() {
        return desc;
    }

    public Date getDueDate() {
        return dueDate;
    }

    @Override
    public int compareTo(TodoItem another) {
        if (this.dueDate == null) return 1;
        if (another.dueDate == null) return -1;
        return this.dueDate.compareTo(another.dueDate);
    }
}

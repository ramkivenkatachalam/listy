package com.example.ramki.todoapp;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ramki on 6/16/15.
 */
public class TodoItem implements Comparable<TodoItem>, Serializable {
    private String title;
    private String desc;
    private Date dueDate;
    private Date createdAt;
    private int deleted;
    private Long _ID;

    public TodoItem(Long id, String title, String desc, Date createdAt, Date dueDate) {
        this._ID = id;
        this.createdAt = createdAt;
        this.title = title;
        this.desc = desc;
        this.dueDate = dueDate;
        this.deleted = 0;
    }

    public TodoItem(String title, String desc, Date createdAt, Date dueDate) {
        this(null, title, desc, createdAt, dueDate);
    }

    @Override
    public String toString() {
        return new StringBuilder()
            .append("ID: ")
            .append(_ID)
            .append(", title: ")
            .append(title)
            .append(", desc: ")
            .append(desc)
            .append(", created on: ")
            .append(createdAt)
            .append(", due by: ")
            .append(dueDate)
            .append(", deleted: ")
            .append(deleted)

            .toString();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public Long getID() {
        return _ID;
    }

    public void setID(long ID) {
        _ID = ID;
    }

    public int getDeleted() {
        return deleted;
    }

    public Long getDueDateLong() {
        if (dueDate == null) return null;
        return dueDate.getTime();
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public long getCreatedAtLong() {
        return createdAt.getTime();
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }


    @Override
    public int compareTo(TodoItem another) {
        if (this.dueDate == null) return 1;
        if (another.dueDate == null) return -1;
        return this.dueDate.compareTo(another.dueDate);
    }

}

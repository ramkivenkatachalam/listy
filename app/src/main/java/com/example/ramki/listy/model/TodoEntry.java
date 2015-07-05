package com.example.ramki.listy.model;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END

import android.support.annotation.NonNull;

/**
 * Entity mapped to table TODO_ENTRY.
 */
public class TodoEntry extends TodoEntryComparable  {

    private Long id;
    /** Not-null value. */
    private String title;
    private String notes;
    private boolean deleted;
    /** Not-null value. */
    private java.util.Date created_on;
    private java.util.Date due;

    // KEEP FIELDS - put your custom fields here
    private static final long DAY_MILLIS = 86400000;
    // KEEP FIELDS END

    public TodoEntry() {
    }

    public TodoEntry(Long id) {
        this.id = id;
    }

    public TodoEntry(Long id, String title, String notes, boolean deleted, java.util.Date created_on, java.util.Date due) {
        this.id = id;
        this.title = title;
        this.notes = notes;
        this.deleted = deleted;
        this.created_on = created_on;
        this.due = due;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** Not-null value. */
    public String getTitle() {
        return title;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    /** Not-null value. */
    public java.util.Date getCreated_on() {
        return created_on;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setCreated_on(java.util.Date created_on) {
        this.created_on = created_on;
    }

    public java.util.Date getDue() {
        return due;
    }

    public void setDue(java.util.Date due) {
        this.due = due;
    }

    // KEEP METHODS - put your custom methods here

    /**
     *  We convert due dates in the UI to values like today, this week, this month etc
     *  so that it is less complicated for users. These methods are helper methods to convert to
     *  and from date objects
     */

    public enum DueEnum {
        PAST_DUE(0),
        TODAY(1),
        THIS_WEEK(2),
        THIS_MONTH(3),
        SOMETIME(4);

        private final int value;

        DueEnum(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }


    public void setDue(int dueEnumVal) {
        java.util.Date current = new java.util.Date();
        java.util.Date today = new java.util.Date(new java.util.Date().getTime()
            / 86400000L * 86400000);
        switch (dueEnumVal) {
            case 0:
                break;
            case 1:
                due = today;
                break;
            case 2:
                due = new java.util.Date(today.getTime() + 7 * DAY_MILLIS);
                break;
            case 3:
                due = new java.util.Date(today.getTime() + 30 * DAY_MILLIS);
                break;
            default:
                due = null;
        }
    }

    public int getDueEnum() {
        java.util.Date today = new java.util.Date(new java.util.Date().getTime()
            / 86400000L * 86400000);
        java.util.Date week = new java.util.Date(today.getTime() + 7 * DAY_MILLIS);

        if (due == null)
            return DueEnum.SOMETIME.getValue();
        if (today.compareTo(due) > 0)
            return DueEnum.PAST_DUE.getValue();
        if (today.compareTo(due) == 0)
            return DueEnum.TODAY.getValue();
        if (week.compareTo(due) >= 0)
            return DueEnum.THIS_WEEK.getValue();
        return DueEnum.THIS_MONTH.getValue();
    }

    @Override
    public String toString() {
        return new StringBuilder()
            .append("Todo [")
            .append("title: ")
            .append(this.title)
            .append(", notes: ")
            .append(this.notes)
            .append(", due: ")
            .append(this.due)
            .append(" ]")
            .toString();
    }

    @Override
    public int compareTo(@NonNull TodoEntryComparable another) {
        TodoEntry anotherTodo = (TodoEntry) another;
        if (this.due == null) return 1;
        if (anotherTodo.due == null) return -1;
        return this.due.compareTo(anotherTodo.due);
    }

    public boolean compare(String notes, int dueEnumVal) {
        if (this.notes == null ^ notes == null)
            return false;
        if (this.notes != null && this.notes.compareTo(notes) != 0)
            return false;

        int d = getDueEnum();
        return d == dueEnumVal;
    }

    public TodoEntry copy() {
        return new TodoEntry(this.id, this.title, this.notes, this.deleted, this.created_on,
            this.due);
    }
    // KEEP METHODS END

}

package com.example.ramki.listy;

import android.os.Parcel;
import android.os.Parcelable;


import java.io.Serializable;
import java.util.Date;

/**
 * Created by ramki on 6/16/15.
 */
public class TodoItem implements Comparable<TodoItem>, Serializable, Parcelable {
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

    protected TodoItem(Parcel in) {
        title = in.readString();
        desc = in.readString();
        long tmpDueDate = in.readLong();
        dueDate = tmpDueDate != -1 ? new Date(tmpDueDate) : null;
        long tmpCreatedAt = in.readLong();
        createdAt = tmpCreatedAt != -1 ? new Date(tmpCreatedAt) : null;
        deleted = in.readInt();
        _ID = in.readByte() == 0x00 ? null : in.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(desc);
        dest.writeLong(dueDate != null ? dueDate.getTime() : -1L);
        dest.writeLong(createdAt != null ? createdAt.getTime() : -1L);
        dest.writeInt(deleted);
        if (_ID == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(_ID);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<TodoItem> CREATOR = new Parcelable.Creator<TodoItem>() {
        @Override
        public TodoItem createFromParcel(Parcel in) {
            return new TodoItem(in);
        }

        @Override
        public TodoItem[] newArray(int size) {
            return new TodoItem[size];
        }
    };
}

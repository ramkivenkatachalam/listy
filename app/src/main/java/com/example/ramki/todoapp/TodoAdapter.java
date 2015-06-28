package com.example.ramki.todoapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by ramki on 6/15/15.
 */
public class TodoAdapter extends ArrayAdapter<TodoItem> {

    Context context;
    int layoutResourceId;
    List<TodoItem> todos;

    public TodoAdapter(Context context, int resource, List<TodoItem> objects) {
        super(context, resource, objects);
        Collections.sort(objects);
        this.context = context;
        this.layoutResourceId = resource;
        this.todos = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        TodoItem todoItem = getItem(position);
        TodoItemHolder holder = new TodoItemHolder();

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder.setDescTV((TextView) row.findViewById(R.id.tvTodoText));
            holder.setDueDateView((Button) row.findViewById(R.id.btnDueLabel));
            row.setTag(holder);
        }
        else
        {
            holder = (TodoItemHolder)row.getTag();
        }
        holder.updateDesc(todoItem.getTitle());
        holder.updateDueDate(todoItem.getDueDate());

        if (position % 2 == 1) {
            row.setBackgroundColor(Color.rgb(238, 248, 248));
        } else {
            row.setBackgroundColor(Color.WHITE);
        }
        return row;
  }
}

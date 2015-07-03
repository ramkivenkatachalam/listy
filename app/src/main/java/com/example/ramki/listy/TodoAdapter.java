package com.example.ramki.listy;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;


import com.example.ramki.listy.model.TodoEntry;


import java.util.Collections;
import java.util.List;

/**
 * Custom Array adapter for ListView of todos
 */
public class TodoAdapter extends ArrayAdapter<TodoEntry> {

    Context context;
    int layoutResourceId;
    List<TodoEntry> todos;

    public TodoAdapter(Context context, int resource, List<TodoEntry> todoList) {
        super(context, resource, todoList);
        Collections.sort(todoList);
        this.context = context;
        this.layoutResourceId = resource;
        this.todos = todoList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        TodoEntry todoEntry = getItem(position);
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
        holder.updateDesc(todoEntry.getTitle());
        holder.updateDueDate(todoEntry.getDue());

        if (position % 2 == 1) {
            row.setBackgroundColor(Color.rgb(238, 248, 248));
        } else {
            row.setBackgroundColor(Color.WHITE);
        }
        return row;
  }
}

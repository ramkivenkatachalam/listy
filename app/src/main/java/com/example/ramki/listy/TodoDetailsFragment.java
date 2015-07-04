package com.example.ramki.listy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ramki.listy.model.TodoEntry;

/**
 * Implementation of ITodoDetailFragment
 */
public class TodoDetailsFragment extends ITodoDetailFragment {
    private TextView tvTodoTitle;
    private TodoEntry todo;
    private OnTodoUpdateHandler updateListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View todoDetailsView =  inflater.inflate(R.layout.fragment_todo_details, container, false);
        tvTodoTitle = (TextView) todoDetailsView.findViewById(R.id.tvTodoTitle);
        tvTodoTitle.setText(todo.getTitle());
        return todoDetailsView;
    }

    @Override
    public void setTodoEntry(TodoEntry todoEntry) {
        this.todo = todoEntry;
    }

    @Override
    public void setOnUpdateHandler(OnTodoUpdateHandler listener) {
        this.updateListener = listener;
    }

}

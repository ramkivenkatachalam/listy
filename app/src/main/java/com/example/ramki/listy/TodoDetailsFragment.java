package com.example.ramki.listy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ramki.listy.model.TodoEntry;

/**
 * Fragment for showing detailed view of a todo
 * Must call setTodo() after creation
 */
public class TodoDetailsFragment extends Fragment {
    private TextView tvTodoTitle;
    private TodoEntry todo;

    public void setTodo(TodoEntry todo) {
        this.todo = todo;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View todoDetailsView =  inflater.inflate(R.layout.fragment_todo_details, container, false);
        tvTodoTitle = (TextView) todoDetailsView.findViewById(R.id.tvTodoTitle);
        tvTodoTitle.setText(todo.getTitle());
        return todoDetailsView;
    }

    private void setupListViewListener() {
    }

}

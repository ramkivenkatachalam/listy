package com.example.ramki.listy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by ramki on 6/28/15.
 */
public class TodoDetailsFragment extends Fragment {
    private TextView tvTodoTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        Bundle args = getArguments();
        TodoItem todo = (TodoItem) args.get("todo");

        // Inflate the layout for this fragment
        View todoDetailsView =  inflater.inflate(R.layout.fragment_todo_details, container, false);
        tvTodoTitle = (TextView) todoDetailsView.findViewById(R.id.tvTodoTitle);
        tvTodoTitle.setText(todo.getTitle());
        return todoDetailsView;
    }

    private void setupListViewListener() {
    }

}

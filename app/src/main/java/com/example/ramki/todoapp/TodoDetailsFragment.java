package com.example.ramki.todoapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

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

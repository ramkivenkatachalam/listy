package com.example.ramki.todoapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by ramki on 6/28/15.
 */
public class TodoListFragment extends Fragment {

    private List<TodoItem> todoItems = new ArrayList<>();
    private TodoAdapter todoAdapter;
    private ListView lvItems;
    private EditText etNew;
    private Button btnAdd;
    private TodoListManager todoListManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View todoListView = inflater.inflate(R.layout.fragment_todo_list, container, false);
        RelativeLayout rl = (RelativeLayout) todoListView.findViewById(R.id.rl);
        lvItems = (ListView) todoListView.findViewById(R.id.lvItems);
        etNew = (EditText) todoListView.findViewById(R.id.etNew);
        btnAdd = (Button) todoListView.findViewById(R.id.btnAdd);

        todoListManager = new TodoListDBManager(getActivity());
        try {
            todoItems = todoListManager.readItems();
        } catch (TodoListManagerException e) {
            e.printStackTrace();
        }
        todoAdapter = new TodoAdapter(getActivity(), R.layout.todo_item, todoItems);
        lvItems.setAdapter(todoAdapter);
        setupListViewListener();
        return todoListView;

    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position,
                long id) {
                TodoItem deleted = todoItems.get(position);
                try {
                    todoListManager.deleteItem(deleted);
                } catch (TodoListManagerException e) {
                    e.printStackTrace();
                }
                todoItems.remove(position);
                todoAdapter.notifyDataSetChanged();
                return false;
            }
        });
        /*
        lvItems.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fragment todoDetailsFragment = new TodoDetailsFragment();
                Bundle args = new Bundle();
                args.putParcelable("todo", todoItems.get(position));
                todoDetailsFragment.setArguments(args);
                // Add the fragment to the 'fragment_container' FrameLayout
                getActivity().getSupportFragmentManager().beginTransaction()
                             .replace(R.id.content_fragment_container, todoDetailsFragment)
                             .addToBackStack(null)
                             .commit();
            }


        });
        */

        btnAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String todoText = etNew.getText().toString();
                if (todoText.isEmpty()) {
                    return;
                }
                TodoItem newTodo = new TodoItem(todoText, null, new Date(), null);
                try {
                    newTodo = todoListManager.addItem(newTodo);
                } catch (TodoListManagerException e) {
                    e.printStackTrace();
                }
                todoItems.add(newTodo);
                Collections.sort(todoItems);
                todoAdapter.notifyDataSetChanged();
                etNew.setText("");
            }
        });

    }

}

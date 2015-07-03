package com.example.ramki.listy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
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


import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by ramki on 6/28/15.
 */
public class TodoListFragment extends Fragment {
    private List<TodoItem> todoItems;
    private TodoAdapter todoAdapter;
    private ListView lvItems;
    private EditText etNew;
    private Button btnAdd;
    private TodoListManager todoListManager;
    private PagerAdapter pagerAdapter;
    private ViewPager pager;


    public void setTodoItems(List<TodoItem> todoItems) {
        this.todoItems = todoItems;
    }

    public void setTodoListManager(TodoListManager todoListManager) {
        this.todoListManager = todoListManager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View todoListView = inflater.inflate(R.layout.fragment_todo_list, container, false);
        RelativeLayout rl = (RelativeLayout) todoListView.findViewById(R.id.rl);
        lvItems = (ListView) todoListView.findViewById(R.id.lvItems);
        etNew = (EditText) todoListView.findViewById(R.id.etNew);
        btnAdd = (Button) todoListView.findViewById(R.id.btnAdd);
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
                pagerAdapter.notifyDataSetChanged();
                return false;
            }
        });
        lvItems.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pager.setCurrentItem(position + 1, true);
            }
        });

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
                pagerAdapter.notifyDataSetChanged();
                etNew.setText("");
            }
        });

    }

    public void setPagerAdapter(PagerAdapter pagerAdapter) {
        this.pagerAdapter = pagerAdapter;
    }

    public void setPager(ViewPager pager) {
        this.pager = pager;
    }
}

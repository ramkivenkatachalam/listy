package com.example.ramki.listy;

import android.os.Bundle;
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
import android.widget.Toast;

import com.example.ramki.listy.model.TodoEntry;


import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Fragment for list view of all the todos
 */
public class TodoListFragment extends ITodoListFragment {

    private List<TodoEntry> todoList;
    private TodoAdapter todoAdapter;
    private ListView lvItems;
    private EditText etNew;
    private Button btnAdd;
    private OnTodoAddHandler addHandler;
    private OnTodoDeleteHandler deleteHandler;
    private OnTodoSelectHandler selectHanlder;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View todoListView = inflater.inflate(R.layout.fragment_todo_list, container, false);
        RelativeLayout rl = (RelativeLayout) todoListView.findViewById(R.id.rl);
        lvItems = (ListView) todoListView.findViewById(R.id.lvItems);
        etNew = (EditText) todoListView.findViewById(R.id.etNew);
        btnAdd = (Button) todoListView.findViewById(R.id.btnAdd);
        todoAdapter = new TodoAdapter(getActivity(), R.layout.todo_item, todoList);
        lvItems.setAdapter(todoAdapter);
        setupListViewListener();
        return todoListView;

    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position,
                long id) {
                TodoEntry deleted = todoList.get(position);
                todoList.remove(position);
                todoAdapter.notifyDataSetChanged();
                deleteHandler.onTodoDelete(deleted);
                return false;
            }
        });

        lvItems.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectHanlder.onTodoSelect(position);
            }
        });

        btnAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String todoText = etNew.getText().toString();
                if (todoText.isEmpty()) {
                    Toast.makeText(getActivity(), "string ", Toast.LENGTH_LONG);
                    return;
                }
                TodoEntry newTodo = new TodoEntry(null, todoText, null, false, new Date(), null);
                todoList.add(newTodo);
                Collections.sort(todoList);
                todoAdapter.notifyDataSetChanged();
                addHandler.onTodoAdd(newTodo);
                etNew.setText("");
            }
        });

    }

    @Override
    public void setTodoList(List<TodoEntry> todoList) {
        this.todoList = todoList;
    }

    @Override
    public void setOnAddHandler(OnTodoAddHandler handler) {
        this.addHandler = handler;
    }

    @Override
    public void setOnSelectHandler(OnTodoSelectHandler handler) {
        this.selectHanlder = handler;
    }

    @Override
    public void setOnDeleteHandler(OnTodoDeleteHandler handler) {
        this.deleteHandler = handler;
    }

}

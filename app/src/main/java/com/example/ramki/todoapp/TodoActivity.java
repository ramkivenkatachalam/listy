package com.example.ramki.todoapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class TodoActivity extends Activity {
    private List<TodoItem> todoItems = new ArrayList<>();
    private TodoAdapter todoAdapter;
    private ListView lvItems;
    private EditText etNew;
    private TodoListFileManager todoListManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        lvItems = (ListView) findViewById(R.id.lvItems);
        etNew = (EditText) findViewById(R.id.etNew);
        todoListManager = new TodoListFileManager(new File(getFilesDir(), "todos_v2.txt"));
        todoItems = todoListManager.readItems();
        todoAdapter = new TodoAdapter(this, R.layout.todo_item, todoItems);
        lvItems.setAdapter(todoAdapter);
        setupListViewListener();
    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                todoItems.remove(position);
                todoAdapter.notifyDataSetChanged();
                try {
                    todoListManager.writeItems(todoItems);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds todoItems to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_todo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onAddedItem(View view) throws IOException {
        String todoText = etNew.getText().toString();
        if (todoText.isEmpty()) {

            return;
        }
        TodoItem newTodo = new TodoItem(todoText, new Date());
        todoAdapter.add(newTodo);
        etNew.setText("");
        todoListManager.writeItems(todoItems);
    }
}

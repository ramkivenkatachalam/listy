package com.example.ramki.todoapp;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;


/**
 * Created by ramki on 6/14/15.
 */
public class TodoListFileManager {
    private static Logger logger = Logger.getLogger(TodoListFileManager.class.getName());
    private final File todoFile;

    public TodoListFileManager(File todoFile) {
        this.todoFile = todoFile;
    }

    public List<TodoItem> readItems()  {
        List<TodoItem> res =  new ArrayList<TodoItem>();
//        res.add(new TodoItem("pick up laundry", new Date()));
//        res.add(new TodoItem("complete android assignment", new Date()));
//        res.add(new TodoItem("review logservice pr", new Date()));
//        res.add(new TodoItem("end2end testing with garrett"));
//        res.add(new TodoItem("get supplies for dinner", new Date()));
          try {
            FileInputStream fin = new FileInputStream(this.todoFile);
            ObjectInputStream ois = new ObjectInputStream(fin);
            TodoItem todo;
            while (true) {
                todo = (TodoItem) ois.readObject();
                if (todo == null) break;
                logger.info("read todo from file: " + todo);
                res.add(todo);
            }
        } catch (Exception e) {
            // skip
        }
        return res;
    }

    public void writeItems(List<TodoItem> todos) throws IOException {
        todoFile.delete();
        FileOutputStream fos = new FileOutputStream(todoFile);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        for (TodoItem t: todos) {
            logger.info("Writing todo to file: "+ t);
           oos.writeObject(t);
        }
        oos.close();
        fos.close();
    }
}

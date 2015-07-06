package com.example.ramki.listy;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.example.ramki.listy.model.TodoEntry;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of ITodoDetailFragment
 */
public class TodoDetailsFragment extends ITodoDetailFragment {

    private boolean isValid = false;
    private EditText etTodoTitle;
    private EditText etTodoNote;
    private ImageButton btnDelete;

    private TodoEntry todo;
    private OnTodoUpdateHandler updateListener;
    private OnTodoDeleteHandler deleteHandler;
    private SeekBar sbDue;
    Map<Integer, TextView> sbarLabelMap = new HashMap<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        isValid = true;
        Log.i("TodoDetailsFragment", "onCreateView : " + todo.toString());

        // Inflate the layout for this fragment
        View todoDetailsView =  inflater.inflate(R.layout.fragment_todo_details, container, false);
        etTodoTitle = (EditText) todoDetailsView.findViewById(R.id.etTodoTitle);
        etTodoTitle.setText(todo.getTitle());
        etTodoNote = (EditText) todoDetailsView.findViewById(R.id.etNote);
        etTodoNote.setText(todo.getNotes());
        btnDelete = (ImageButton) todoDetailsView.findViewById(R.id.btnDelete);
        sbDue = (SeekBar) todoDetailsView.findViewById(R.id.sbDue);
        sbDue.setMax(4);
        sbDue.setProgress(todo.getDueEnum());
        sbarLabelMap.put(1, (TextView) todoDetailsView.findViewById(R.id.tvNow));
        sbarLabelMap.put(2, (TextView) todoDetailsView.findViewById(R.id.tvWeek));
        sbarLabelMap.put(3, (TextView) todoDetailsView.findViewById(R.id.tvMonth));
        TextView c = sbarLabelMap.get(sbDue.getProgress());
        if (c != null) {
            c.setTextSize(10);
            c.setTextColor(0xFF008080);
        }
        setupListeners();
        return todoDetailsView;
    }


    @Override
    public void checkForUpdates() {
        if (!isValid)
            return;
        Log.i("TodoDetailsFragment", "inside checkForUpdates: " + todo);
        TodoEntry updatedTodo = todo.copy();
        final String notes = etTodoNote.getText().toString();
        Date due = todo.getDue();
        int d = sbDue.getProgress();
        Log.i("TodoDetailsFragment",
            "inside checkForUpdates: " + todo.getTitle() + ", " + notes + ", " + d);

        // see if the todo is updated and if so, call the update handler
        if (!todo.compare(notes, d)) {
            todo.setNotes(notes);
            todo.setDue(d);
            Log.i("TodoDetailsFragment(" + this + ")", "todo changed");
            updateListener.onTodoUpdate(todo);
        }
    }

    private void setupListeners() {
        btnDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                isValid = false;
                deleteHandler.onTodoDelete(todo);
            }
        });
        sbDue.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            int previous = sbDue.getProgress();
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i("SB", "change from " + previous +", " + progress);
                TextView p = sbarLabelMap.get(previous);
                TextView c = sbarLabelMap.get(progress);
                if (p != null) {
                    p.setTextSize(8);
                    p.setTextColor(0xc16e6e6e);
                }
                Animation a = AnimationUtils.loadAnimation(getActivity(), R.anim.bounce);
                a.reset();
                if (c != null) {
                    c.clearAnimation();
                    c.setTextSize(10);
                    c.setTextColor(0xFF008080);
                    c.startAnimation(a);
                }
                previous = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void setTodoEntry(TodoEntry todoEntry) {
        this.todo = todoEntry;
    }

    @Override
    public void setOnUpdateHandler(OnTodoUpdateHandler handler) {
        this.updateListener = handler;
    }

    @Override
    public void setOnDeleteHandler(OnTodoDeleteHandler handler) {
        this.deleteHandler = handler;
    }

}

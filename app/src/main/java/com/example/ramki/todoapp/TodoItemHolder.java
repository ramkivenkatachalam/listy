package com.example.ramki.todoapp;

import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by ramki on 6/16/15.
 */
public class TodoItemHolder {
    private TextView desc;
    private Button btnDueLabel;

    public void setDescTV(TextView desc) {
        this.desc = desc;
    }

    public void setDueDateView(Button btnDueLabel) {
        this.btnDueLabel = btnDueLabel;
    }

    public void updateDesc(String descText) {
        this.desc.setText(descText);
    }

    public void updateDueDate(Date due) {
        String dueLable = null;
        if (due != null) {
            Date currentDate = new Date();
            long delta = currentDate.getTime() - due.getTime();
            if (delta < 86400) {
                dueLable = "now";
            } else if (delta < (86400 * 7)) {
                dueLable = "soon";
            }
        }
        if (dueLable == null) {
            btnDueLabel.setVisibility(View.GONE);
        } else {
            btnDueLabel.setVisibility(View.VISIBLE);
            btnDueLabel.setText(dueLable);
        }
    }
}

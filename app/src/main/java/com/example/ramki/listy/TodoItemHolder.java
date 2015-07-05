package com.example.ramki.listy;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.util.Date;

/**
 * Helper class to set UI elements based on the value of the underlying object
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
        Date today = new Date(new Date().getTime() / 86400000L * 86400000);
        Date week = new Date(today.getTime() + 7 * 86400000L);

        String dueLable = null;
        if (due != null) {
            if (today.compareTo(due) >= 0) {
                dueLable = "now";
            } else if (week.compareTo(due) >= 0) {
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

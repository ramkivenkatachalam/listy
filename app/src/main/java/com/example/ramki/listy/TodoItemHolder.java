package com.example.ramki.listy;

import android.view.View;
import android.widget.Button;
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
        Date today = new Date(new Date().getTime() / 86400000L * 86400000);
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + 7 - c.get(
            Calendar.DAY_OF_WEEK));
        Date eow = c.getTime();

        String dueLable = null;
        if (due != null) {
            if (today.compareTo(due) >= 0) {
                dueLable = "now";
            } else if (eow.compareTo(due) >= 0) {
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

package com.example.siri.simpletodo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class EditItemActivity extends AppCompatActivity {
    private String taskName;
    private EditText etTaskName;
    private int position;

    private DatePicker datePicker;
    private Calendar calendar;
    private Button dateViewButton;
    private int year, month, day;

    private CheckBox highPriorityChecker;
    private CheckBox completionChecker;

    private int isHighPriority;
    private boolean isCompleted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        taskName = getIntent().getStringExtra("taskName");
        position = getIntent().getIntExtra("position", 0);

        isHighPriority = getIntent().getIntExtra("isHighPriority", 0);
        isCompleted = getIntent().getBooleanExtra("isCompleted", false);

        day = getIntent().getIntExtra("day", 0);
        month = getIntent().getIntExtra("month", 0);
        year = getIntent().getIntExtra("year", 0);

        etTaskName = (EditText)findViewById(R.id.etTaskName);
        // Set edit field's default text to the task name that needs to be edited
        etTaskName.setText(taskName);
        // Place the edit field's cursor to the end
        etTaskName.setSelection(taskName.length());

        // Set Current Date
        dateViewButton = (Button)findViewById(R.id.setDate);

        if (day == 0 || month == 0 || year == 0) {
            calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
        }

        dateViewButton.setText(new StringBuilder().append(day).append("/")
                .append(month+1).append("/").append(year));

        highPriorityChecker = (CheckBox)findViewById(R.id.high_priority);
        highPriorityChecker.setChecked(isHighPriority==1);
        highPriorityChecker.setOnClickListener(onHighPriorityChangedListener);

        completionChecker = (CheckBox)findViewById(R.id.task_complete);
        completionChecker.setChecked(isCompleted);
        completionChecker.setOnClickListener(onTaskCompletionChangedListener);

    }

    private View.OnClickListener onHighPriorityChangedListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            CheckBox taskStatus = (CheckBox) v;
            isHighPriority = taskStatus.isChecked()? 1: 0;
        }
    };

    private View.OnClickListener onTaskCompletionChangedListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            CheckBox taskStatus = (CheckBox) v;
            isCompleted = taskStatus.isChecked();
        }
    };

    @SuppressWarnings("deprecation")
    public void onSetDate(View view) {
        showDialog(999);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    year = arg1;
                    month = arg2;
                    day = arg3;
                    dateViewButton.setText(new StringBuilder().append(day).append("/")
                            .append(month+1).append("/").append(year));
                }
            };

    public void onSave(View view) {
        EditText etName = (EditText) findViewById(R.id.etTaskName);
        // Prepare data intent
        Intent data = new Intent();
        // Pass relevant data back as a result
        data.putExtra("newTaskName", etName.getText().toString());
        data.putExtra("position", position);
        data.putExtra("isCompleted", isCompleted);
        data.putExtra("isHighPriority", isHighPriority);

        //Date
        data.putExtra("day", day);
        data.putExtra("month", month);
        data.putExtra("year", year);

        // Activity finished ok, return the data
        setResult(RESULT_OK, data); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent
    }
}

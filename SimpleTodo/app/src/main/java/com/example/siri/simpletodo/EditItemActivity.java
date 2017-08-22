package com.example.siri.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {
    private String taskName;
    private EditText etTaskName;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        taskName = getIntent().getStringExtra("taskName");
        position = getIntent().getIntExtra("position", 0);
        etTaskName = (EditText)findViewById(R.id.etTaskName);
        // Set edit field's default text to the task name that needs to be edited
        etTaskName.setText(taskName);
        // Place the edit field's cursor to the end
        etTaskName.setSelection(taskName.length());

    }

    public void onSave(View view) {
        EditText etName = (EditText) findViewById(R.id.etTaskName);
        // Prepare data intent
        Intent data = new Intent();
        // Pass relevant data back as a result
        data.putExtra("newTaskName", etName.getText().toString());
        data.putExtra("position", position); // ints work too
        // Activity finished ok, return the data
        setResult(RESULT_OK, data); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent
    }
}

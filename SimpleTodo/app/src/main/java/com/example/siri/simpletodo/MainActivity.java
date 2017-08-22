package com.example.siri.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static com.example.siri.simpletodo.R.id.lvItems;

public class MainActivity extends AppCompatActivity {
    private ArrayList tasks;
    private ArrayAdapter tasksAdapter;
    private ListView lvTasks;
    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvTasks = (ListView)findViewById(lvItems);
        readItems();
        tasksAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tasks);
        lvTasks.setAdapter(tasksAdapter);
        setUpListViewListener();
    }

    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            tasks = new ArrayList<String>(FileUtils.readLines(todoFile));
        }
        catch(IOException e) {
            tasks = new ArrayList<String>();
        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");

        try {
            FileUtils.writeLines(todoFile, tasks);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void setUpListViewListener() {
        lvTasks.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter,
                                                   View view, int pos, long id) {
                        tasks.remove(pos);
                        tasksAdapter.notifyDataSetChanged();
                        Toast.makeText(getApplicationContext(), "Task removed", Toast.LENGTH_SHORT).show();
                        writeItems();
                        return true;
                    }
                }
        );
        lvTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                i.putExtra("taskName", lvTasks.getItemAtPosition(position).toString());
                i.putExtra("position", position);
                startActivityForResult(i, REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            String newTaskName = data.getExtras().getString("newTaskName");
            int position = data.getExtras().getInt("position", 0);

            if (newTaskName.isEmpty()) {

                // Remove task if task name is empty
                tasks.remove(position);
            } else {
                tasks.set(position, newTaskName);
            }

            tasksAdapter.notifyDataSetChanged();
            writeItems();
        }
    }

    public void onAddTask(View v) {
        EditText etNewTask = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewTask.getText().toString();
        if (itemText.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Invalid task name", Toast.LENGTH_SHORT).show();
        } else {
            tasksAdapter.add(itemText);
            etNewTask.setText("");
            writeItems();
        }
    }
}

package com.example.siri.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.example.siri.simpletodo.R.id.lvItems;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Task> tasks;
    private TasksAdapter tasksAdapter;
    private ListView lvTasks;

    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvTasks = (ListView)findViewById(lvItems);
        tasks = new ArrayList<>();
        tasksAdapter = new TasksAdapter(this, tasks);
        readItems();
        lvTasks.setAdapter(tasksAdapter);
        setUpListViewListener();
    }

    private void readItems() {
        List<Task> taskList = Task.getAll();
        tasksAdapter.addAll(taskList);
    }

    public void setUpListViewListener() {
        lvTasks.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter,
                                                   View view, int pos, long id) {
                        Task tsk = tasks.remove(pos);
                        if (tsk != null) {
                            tsk.delete();
                        }
                        tasksAdapter.notifyDataSetChanged();
                        Toast.makeText(getApplicationContext(), "Task removed", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                }
        );
        lvTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                i.putExtra("taskName", ((Task)lvTasks.getItemAtPosition(position)).getTaskName());
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
            Task tsk = tasks.get(position);
            tsk.setTaskName(newTaskName);
            tsk.save();
            tasksAdapter.notifyDataSetChanged();
        }
    }

    public void onAddTask(View v) {
        EditText etNewTask = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewTask.getText().toString().trim();
        if (itemText.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Invalid task name", Toast.LENGTH_SHORT).show();
        } else {
            Task tsk = new Task();
            tsk.setId(tasks.size());
            tsk.setTaskName(itemText);
            tsk.setPriority(0);
            tsk.setComplete(false);
            tsk.save();
            tasks.add(tsk);
            tasksAdapter.notifyDataSetChanged();
        }
        etNewTask.setText("");
    }
}

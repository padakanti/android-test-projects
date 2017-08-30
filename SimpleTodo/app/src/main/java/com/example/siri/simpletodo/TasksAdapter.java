package com.example.siri.simpletodo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by siri on 8/23/17.
 */

public class TasksAdapter extends ArrayAdapter<Task> {
    public TasksAdapter(Context context, ArrayList<Task> tasks) {
        super(context, 0, tasks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Task task = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_task, parent, false);
        }

        if (task != null) {
            // Lookup view for data population
            TextView tskName = (TextView) convertView.findViewById(R.id.tskName);
            CheckBox tskStatus = (CheckBox) convertView.findViewById(R.id.tskStatus);
            // Populate the data into the template view using the data object
            if (tskName != null) {
                tskName.setText(task.getTaskName());
            }
            if (tskStatus != null) {
                tskStatus.setChecked(task.getComplete());
            }
            tskStatus.setOnClickListener(onClickListener);
            tskStatus.setTag(position);
        }

        // Return the completed view to render on screen
        return convertView;
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            CheckBox taskStatus = (CheckBox) v;
            int position = (int) taskStatus.getTag();
            Task task = getItem(position);
            task.setComplete(taskStatus.isChecked());
            task.save();
        }
    };
}
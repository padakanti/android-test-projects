package com.example.siri.simpletodo;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

/**
 * Created by sunil on 8/22/17.
 */

@Table(database = MyDatabase.class)
public class Task extends BaseModel {
    @Column
    @PrimaryKey
    int id;

    @Column
    String taskName;

    @Column
    int priority;

    @Column
    boolean isComplete;

    public String getTaskName() {
        return taskName;
    }

    public int getId() {
        return id;
    }

    public int getPriority() {
        return priority;
    }

    public boolean getComplete() {
        return isComplete;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setComplete(boolean complete) {
        this.isComplete = complete;
    }

    public static List<Task> getAll() {
        return SQLite.select().
                from(Task.class).queryList();
    }
}

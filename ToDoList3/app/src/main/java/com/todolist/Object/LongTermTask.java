package com.todolist.Object;

import java.util.List;

public class LongTermTask extends Type {

    //待办
    private String deadline;
    private List<SubTask> subtasks;
    public LongTermTask(String deadline)
    {
        super("LongTermTask");
        this.deadline=deadline;
        this.subtasks=null;
    }

    public List<SubTask> getSubtasks() {
        return subtasks;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public void setSubtasks(List<SubTask> subtasks) {
        this.subtasks=subtasks;
    }
}

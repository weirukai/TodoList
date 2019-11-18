package com.todolist.Object;

public class TemporalTask extends Type {
    private String deadLine;
    public TemporalTask(String deadLine)
    {
        super("TemporalTask");
        this.deadLine=deadLine;
    }

    public void setDeadLine(String deadLine) {
        this.deadLine = deadLine;
    }

    public String getDeadLine() {
        return deadLine;
    }
}

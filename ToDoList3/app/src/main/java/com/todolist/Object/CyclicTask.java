package com.todolist.Object;

public class CyclicTask extends Type {
    private String date;
    private int times;
    private String cycle;
    public CyclicTask(String date,int times,String cycle)
    {
        super("CyclicTask");
        this.cycle=cycle;
        this.date=date;
        this.times=times;
    }

    public int getTimes() {
        return times;
    }

    public String getCycle() {
        return cycle;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public void setTimes(int times) {
        this.times = times;
    }
}

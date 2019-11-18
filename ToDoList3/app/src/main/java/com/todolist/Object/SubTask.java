package com.todolist.Object;

import com.todolist.IO.SaveManager;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SubTask implements Serializable,Comparable<SubTask> {
    //长期任务中的子任务
    private String deadline_sub;
    private String sub_name;
    private boolean hasFinished;
    private String description;
    public SubTask(String description,String deadline_sub,String sub_name)
    {
        this.deadline_sub=deadline_sub;
        this.description=description;
        this.hasFinished=false;
        this.sub_name=sub_name;
    }

    public String getDeadline_sub() {
        return deadline_sub;
    }

    public String getSub_name() {
        return sub_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDeadline_sub(String deadline_sub) {
        this.deadline_sub = deadline_sub;
    }

    public void setSub_name(String sub_name) {
        this.sub_name = sub_name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setHasfinished(boolean hasfinished) {
        this.hasFinished = hasfinished;
    }
    public int compareTo(SubTask task)
    {

        String deadline1=task.getDeadline_sub();
        String deadline2=this.getDeadline_sub();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-mm-dd");
        try {
            Date date2=sdf.parse(deadline2);
            Date date1=sdf.parse(deadline1);

            if (date1.before(date2))
            {
                return 1;
            }else if (date1.equals(date2))
            {
                return 0;
            }else
            {
                return -1;
            }
        }catch (ParseException o)
        {
            return 1;
        }
    }


}

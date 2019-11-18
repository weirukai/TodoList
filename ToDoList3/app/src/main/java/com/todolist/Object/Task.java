package com.todolist.Object;

import android.os.Parcelable;

import com.todolist.IO.SaveManager;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class Task implements Serializable,Comparable<Task> {
    private String taskName;
    private boolean hasFinished;
    private String description;
    private Type TaskType;
    public Task(String taskName,String description,Type taskType)
    {
        this.description=description;
        this.hasFinished=false;
        this.taskName=taskName;
        this.TaskType=taskType;
    }

    public void setHasFinished(boolean hasFinished) {
        this.hasFinished = hasFinished;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setTaskType(Type taskType) {
        TaskType = taskType;
    }

    public String getDescription() {
        return description;
    }

    public String getTaskName() {
        return taskName;
    }

    public Type getTaskType() {
        return TaskType;
    }

    public boolean isHasFinished() {
        return hasFinished;
    }


    public  int compareTo(Task task1)
    {
        //重写CompareTo函数用于排序时候的比较
       //根据执行日期来决定
        //要对输入的日期进行相应的处理，转化成数字
       String typeName=task1.getTaskType().getTypeName();
       if (typeName.equals("TemporalTask"))
       {
           //临时任务比较的是截至的日期；
           String deadline1=((TemporalTask)task1.getTaskType()).getDeadLine();
           String deadline2=((TemporalTask)this.getTaskType()).getDeadLine();
           SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
           try {
               Date task1Date=sdf.parse(deadline1);
               Date task2Data=sdf.parse(deadline2);
               if (task1Date.before(task2Data))
               {
                   return 1;
               }else if (task1Date.equals(task2Data)) {
                   return 0;
               }
               else {
                return -1;
               }
           }catch (ParseException o)
           {
               return 1;
           }

       }
       else if (typeName.equals("CyclicTask"))
       {
           String implementDate1=((CyclicTask)task1.getTaskType()).getDate();
           String implementDate2=((CyclicTask)this.getTaskType()).getDate();
           SimpleDateFormat sdf=new SimpleDateFormat("yyyy-mm-dd");
           try {
               Date date1=sdf.parse(implementDate1);
               Date date2=sdf.parse(implementDate2);
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
       }else if (typeName.equals("LongTermTask"))
       {
           String deadline1=((LongTermTask)task1.getTaskType()).getDeadline();
           String deadline2=((LongTermTask)this.getTaskType()).getDeadline();
           SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
           try {
               Date date1=sdf.parse(deadline1);
               Date date2=sdf.parse(deadline2);
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
        return 1;
    }
    public void saveTask(String filePath)
    {
        SaveManager.save(filePath,this);
    }
    public void deleteTask(List<Task> tasks,String filePath)
    {
        SaveManager.DeleteTask(this,tasks,filePath);
    }
    public void changeType(Type newType)
    {
        this.TaskType=newType;
        //用作类型之间的转换
    }
}

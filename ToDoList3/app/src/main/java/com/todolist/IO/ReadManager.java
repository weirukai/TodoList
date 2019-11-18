package com.todolist.IO;

import android.widget.TableLayout;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.todolist.Object.CyclicTask;
import com.todolist.Object.LongTermTask;
import com.todolist.Object.SubTask;
import com.todolist.Object.Task;
import com.todolist.Object.TemporalTask;
import com.todolist.Object.Type;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ReadManager {
    //这里提供读取和解析的服务
    //应该自定反义序列化类型适配器
    public static List<Task> taskParse(String filePath)
    {
        String full_text=read(filePath);
        if (full_text==null)
        {
            return null;
        }
        String toBeParse='['+full_text+']';
        java.lang.reflect.Type type=new TypeToken<ArrayList<Task>>(){}.getType();
        Gson json=new GsonBuilder().registerTypeAdapter(Type.class,new typeAdapter()).create();//这里为什么会出为题呢
        List<Task> tasks=json.fromJson(toBeParse,type);
        return tasks;
    }


    /*抽取tasks中间所有的temporalTask*/
    public static List<Task> getTemporalTask(List<Task> list)
    {
        List<Task> temporalTasks=new LinkedList<>();
        for (Task index : list) {
            if (index.getTaskType().getTypeName().equals("TemporalTask"))
            temporalTasks.add(index);
        }
        return temporalTasks;
    }

    /*抽取期中所有的CyclicTask*/
    public static List<Task> getCyclicTask(List<Task> list)
    {
        List<Task> cyclicTask=new LinkedList<>();
        for (Task index : list) {
            if (index.getTaskType().getTypeName().equals("CyclicTask"))
                cyclicTask.add(index);
        }
        return cyclicTask;
    }

    /*抽取期中的所有的LongTermTask*/
    public static List<Task> getLongTermTask(List<Task> list)
    {
        List<Task> LongTermTask=new LinkedList<>();
        for (Task index :list) {
            if (index.getTaskType().getTypeName().equals("LongTermTask"))
                LongTermTask.add(index);
        }
        return LongTermTask;
    }



    /*读取文件*/
    protected static String read(String filePath)
    {
        if (new File(filePath).exists()&&new File(filePath).length()==0)
        {
            return null;
        }
        StringBuilder full_text=new StringBuilder();
        File file=new File(filePath);
        if (file.exists()==false)
            try {
                file.createNewFile();
            }catch (IOException o)
            {
                o.printStackTrace();
            }
        FileReader newReader=null;
        char[] in_read=new char[30];//缓冲数组
        char[] copy=in_read;
        try{
            newReader=new FileReader(file);
        }catch (FileNotFoundException e) { e.printStackTrace();}
        try {
            while (newReader.read(in_read)!=-1)
            {
                full_text.append(in_read);
                copy=in_read.clone();
                for (int i = 0; i < in_read.length; i++) {
                    in_read[i]='*';
                }
            }
            int index=0;
            for ( ; index < copy.length; index++) {
                if (copy[index]=='}'&&copy[index+1]==','&&copy[index+2]=='*')
                    break;
            }
            //full_text.delete(full_text.length()-9+index,full_text.length());
            String string=full_text.toString().substring(0,full_text.length()-(in_read.length-1)+index);
            full_text=new StringBuilder();
            full_text.append(string);
        }catch (IOException e){e.printStackTrace();}
        try{
            newReader.close();
        }catch (IOException o)
        {
            o.printStackTrace();
        }
        return full_text.toString();
    }
}

//做一个抽象列的适配器，让Gson能够适配抽象类；
class typeAdapter implements JsonDeserializer<Type>{


    public Type deserialize(JsonElement json, java.lang.reflect.Type typeOfT, JsonDeserializationContext context)
    {
             Type type=null;
             JsonObject newType=json.getAsJsonObject();
             if (((String)newType.get("typeName").getAsString()).equals("TemporalTask"))
             {
                 type=new TemporalTask(newType.get("deadLine").getAsString());

             }else if ((newType.get("typeName").getAsString().equals("CyclicTask")))
             {
                type=new CyclicTask(newType.get("date").getAsString(),newType.get("times").getAsInt(),newType.get("cycle").getAsString());

             }else if (newType.get("typeName").getAsString().equals("LongTermTask"))
             {
                 LongTermTask longTermTask=new LongTermTask(newType.get("deadline").getAsString());
                 List<SubTask> list=new LinkedList<>();//List是一个抽象接口
                 for (JsonElement index :
                         newType.get("subtasks").getAsJsonArray()) {
                     JsonObject jsonObject=index.getAsJsonObject();
                     SubTask subTask=new SubTask(jsonObject.get("description").getAsString(),jsonObject.get("deadline_sub").getAsString(),jsonObject.get("sub_name").getAsString());
                     list.add(subTask);
                 }
                 longTermTask.setSubtasks(list);
                 type=longTermTask;
             }
             return type;
    }

}

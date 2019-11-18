package com.todolist.IO;

import android.content.Context;
import android.util.JsonReader;

import com.google.gson.Gson;
import com.todolist.MainActivity;
import com.todolist.Object.Task;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class SaveManager {

//用于存储和读取文件的类


    public static void save(String filePath, Task task)
    {
        //前提是文件已经存了，不需要在创建，创建工作交给其他的函数来完成
        //11.2.19：00运行正常，示例是temporalTask
        File saveFile=new File(filePath);
        FileWriter newWriter=null;
        Gson json=new Gson();
        String saveString=json.toJson(task)+',';
        try
        {
            newWriter= new FileWriter(saveFile,true);
            newWriter.write(saveString);
        }catch (IOException o) {
            o.printStackTrace();}
        finally {
            try {
                newWriter.close();
            }catch (IOException e){e.printStackTrace();}
        }
    }


    /*创建一个文件，并且返回文件的路径*/
    public static String fileCreate(Context context,String name)
    {
        //创建文件并且返回文件的绝对路径
        String file_foldr= context.getExternalFilesDir("save").getAbsolutePath();
        //File file=new File()
        String filePath=file_foldr+"/"+name;
        File file =new File(filePath);
        if (!file.exists())
        {
            try{
                file.createNewFile();
            }catch (IOException e){
                e.printStackTrace();}
        }
        return filePath;
    }

    public static void DeleteTask(Task task, List<Task> all_task,String filePath)
    {
        File file=new File(filePath);
        all_task.remove(task);
        FileWriter fileWriter=null;
        //文件清空操作
        try {
            fileWriter=new FileWriter(file,false);
            fileWriter.write("");
            fileWriter.flush();
        }catch (IOException o)
        {
            o.printStackTrace();
        }
        try {
            fileWriter.close();
        }catch (IOException o)
        {
            o.printStackTrace();
        }

        for (Task index :
                all_task) {
            SaveManager.save(filePath,index);
        }

    }

}

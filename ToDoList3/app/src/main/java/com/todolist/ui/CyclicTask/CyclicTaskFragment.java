package com.todolist.ui.CyclicTask;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.todolist.IO.ReadManager;
import com.todolist.Object.CyclicTask;
import com.todolist.Object.Task;
import com.todolist.R;

import java.util.Collections;
import java.util.List;

public class CyclicTaskFragment extends Fragment {
    final private String filePath="/storage/emulated/0/Android/data/com.todolist/files/save/save.json";
    private CyclicTaskViewModel dashboardViewModel;
    private View root;
    private List<Task> tasks;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(CyclicTaskViewModel.class);
         root = inflater.inflate(R.layout.fragment_cyclictask, container, false);

         tasks= ReadManager.taskParse(filePath);
         LinearLayout linearLayout=root.findViewById(R.id.cycle_liner);
         if (tasks!=null)
             addViewCyclicTask(linearLayout,tasks);
        dashboardViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                 // textView.setText(s);
            }
        });
        return root;
    }


    //后续工作，更改xml文件，在onCrate View中调用相应的函数

    /*动态的添加cyclicTask*/
    public void addViewCyclicTask(LinearLayout linearLayout, List<Task> tasks)
    {
        List<Task> cyclicTask=ReadManager.getCyclicTask(tasks);

        if (cyclicTask==null)
        {
            System.out.println("文件读取的时候错误");
            return;
        }
        //此处进行排序的操作
        Collections.sort(cyclicTask);
        LinearLayout.LayoutParams params=null;
        for (int i = 0; i < cyclicTask.size(); i++) {
            LinearLayout task_LinerLayout=new LinearLayout(getActivity());
            params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            task_LinerLayout.setLayoutParams(params);
            linearLayout.addView(task_LinerLayout);
            task_LinerLayout.setOrientation(LinearLayout.VERTICAL);
            task_LinerLayout.setBackgroundColor(getResources().getColor(R.color.textView));


            //添加名称
            TextView taskName=new TextView(getActivity());
            taskName.setText("Task"+(i+1)+":"+cyclicTask.get(i).getTaskName());
            taskName.setHeight(50);
            taskName.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            taskName.setGravity(Gravity.CENTER);
            task_LinerLayout.addView(taskName);

            //添加具体描述
            TextView description=new TextView(getActivity());
            description.setText(cyclicTask.get(i).getDescription());
            description.setHeight(50);
            description.setGravity(Gravity.CENTER);
            description.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            task_LinerLayout.addView(description);

            //添加执行日期
            TextView data=new TextView(getActivity());
            data.setText(((CyclicTask)cyclicTask.get(i).getTaskType()).getDate());
            data.setHeight(50);
            data.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            data.setGravity(Gravity.CENTER);
            task_LinerLayout.addView(data);


            //添加重复次数
            TextView times=new TextView(getActivity());
            times.setText(((CyclicTask)cyclicTask.get(i).getTaskType()).getTimes()+"");
            times.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            times.setHeight(50);
            times.setGravity(Gravity.CENTER);
            task_LinerLayout.addView(times);
            //添加重复周期
            TextView cycle=new TextView(getActivity());
            cycle.setText(((CyclicTask)cyclicTask.get(i).getTaskType()).getCycle());
            cycle.setHeight(50);
            cycle.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            cycle.setGravity(Gravity.CENTER);
            task_LinerLayout.addView(cycle);

            //添加空白
            TextView gap=new TextView(getActivity());
            gap.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            gap.setHeight(6);
            gap.setBackgroundColor(getResources().getColor(R.color.backgroundHui));
            task_LinerLayout.addView(gap);

        }

    }
}
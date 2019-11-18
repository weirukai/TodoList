package com.todolist.ui.TemporalTask;

import android.graphics.Color;
import android.os.Bundle;
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
import com.todolist.Object.Task;
import com.todolist.Object.TemporalTask;
import com.todolist.R;

import java.util.Collections;
import java.util.List;

public class TemporalTaskFragment extends Fragment {
     private String filePath;
    private TemporalTaskViewModel homeViewModel;
    private List<Task> tasks;
    private View root;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(TemporalTaskViewModel.class);


        this.filePath="/storage/emulated/0/Android/data/com.todolist/files/save/save.json";


         root = inflater.inflate(R.layout.fragment_temporaltask, container, false);

        final  LinearLayout liner_temp=root.findViewById(R.id.temp_linerLayout);
        tasks=ReadManager.taskParse(filePath);
        if (tasks!=null)
            addViewTemporalTask(liner_temp,tasks);//这里没得必要去执行渲染，因为刚进来的时候就一定会却onResume*/
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
               // textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onResume() {
       super.onResume();
         tasks=ReadManager.taskParse(filePath);
    }



    /*动态的添加元素到相应的fragment*/
    public void addViewTemporalTask(LinearLayout linearLayout, List<Task> all_tasks)
    {
        //linerLayout是父容器组件

        List<Task> temporalTask= ReadManager.getTemporalTask(all_tasks);
        if (temporalTask==null)
        {
            System.out.println("空文件错误");
            return;
        }
        LinearLayout.LayoutParams params_super=null;


        //sort
        Collections.sort(temporalTask);//排序操作，按照截止日期的先后排序
        for (int i = 0; i < temporalTask.size(); i++) {
            //动态的添加组件
            //组件的数量应该有数组的size来确定
            LinearLayout task_linerLayout = new LinearLayout(getActivity());
            params_super = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);//获得当前空间的布局对象
            task_linerLayout.setLayoutParams(params_super);
            linearLayout.addView(task_linerLayout);
            task_linerLayout.setOrientation(LinearLayout.VERTICAL);
            task_linerLayout.setBackgroundColor(getResources().getColor(R.color.textView));//


            //任务名称
            TextView taskname = new TextView(getActivity());
            taskname.setText("Task"+(i+1)+":"+temporalTask.get(i).getTaskName());
            taskname.setHeight(50);
            taskname.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            taskname.setGravity(Gravity.CENTER);
            task_linerLayout.addView(taskname);


            //任务的详细描述
            TextView description = new TextView(getActivity());
            description.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            description.setHeight(50);
            description.setText(temporalTask.get(i).getDescription());
            description.setGravity(Gravity.CENTER);
            task_linerLayout.addView(description);
            //截至日期
            TextView deadline = new TextView(getActivity());
            deadline.setHeight(50);
            deadline.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            deadline.setGravity(Gravity.CENTER);
            deadline.setText(((TemporalTask) temporalTask.get(i).getTaskType()).getDeadLine());
            task_linerLayout.addView(deadline);

            //间隔
            TextView gap=new TextView(getActivity());
            gap.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            gap.setHeight(6);
            gap.setBackgroundColor(getResources().getColor(R.color.backgroundHui));
            task_linerLayout.addView(gap);
        }
    }




}
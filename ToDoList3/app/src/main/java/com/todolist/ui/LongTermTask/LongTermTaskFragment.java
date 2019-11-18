package com.todolist.ui.LongTermTask;

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
import com.todolist.Object.LongTermTask;
import com.todolist.Object.SubTask;
import com.todolist.Object.Task;
import com.todolist.R;

import java.util.Collections;
import java.util.List;

public class LongTermTaskFragment extends Fragment {

    private LongTermTaskViewModel notificationsViewModel;
    final private String filePath="/storage/emulated/0/Android/data/com.todolist/files/save/save.json";
    private List<Task> tasks;
    private View root;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(LongTermTaskViewModel.class);
         this.root = inflater.inflate(R.layout.fragment_longtermtask, container, false);
         LinearLayout linearLayout=root.findViewById(R.id.long_linerLayout);
       tasks= ReadManager.taskParse(filePath);
       if (tasks!=null)
           //此处动态的添加任务的布局和组件
           addLongTermTask(linearLayout,tasks);
        //观察者暂时不太需要
        notificationsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
        return root;
    }
    public void addLongTermTask(LinearLayout linearLayout,List<Task> tasks)
    {
        List<Task> LongTermTasks=ReadManager.getLongTermTask(tasks);
        if (LongTermTasks==null)
        {
            System.out.println("提取的时候发生了什么错误！");
        }
        else
        {
            //sort
            Collections.sort(LongTermTasks);
            LinearLayout.LayoutParams params=null;
            for (int i = 0; i < LongTermTasks.size(); i++) {
                LinearLayout task_linerLayout = new LinearLayout(getActivity());
                params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);//获得当前空间的布局对象
                task_linerLayout.setLayoutParams(params);
                linearLayout.addView(task_linerLayout);
                task_linerLayout.setOrientation(LinearLayout.VERTICAL);
                task_linerLayout.setBackgroundColor(getResources().getColor(R.color.textView));
               /* task_linerLayout.setId(100+i);
                task_linerLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });*///本来准备用来处理删除的事件
                //添加任务的名称
                TextView taskName=new TextView(getActivity());
                taskName.setText("Task"+i+1+':'+LongTermTasks.get(i).getTaskName());
                taskName.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                taskName.setHeight(50);
                taskName.setGravity(Gravity.CENTER);
                task_linerLayout.addView(taskName);


                //添加任务的具体描述
                TextView description=new TextView(getActivity());
                description.setText(LongTermTasks.get(i).getDescription());
                description.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                description.setHeight(50);
                description.setGravity(Gravity.CENTER);
                task_linerLayout.addView(description);


                //添加截止日期
                TextView deadline=new TextView(getActivity());
                deadline.setText(((LongTermTask)LongTermTasks.get(i).getTaskType()).getDeadline());
                deadline.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                deadline.setHeight(50);
                deadline.setGravity(Gravity.CENTER);
                task_linerLayout.addView(deadline);

//子任务的处理部分
                List<SubTask> subTasks=((LongTermTask) LongTermTasks.get(i).getTaskType()).getSubtasks();
                //sort
                Collections.sort(subTasks);
                //添加子任务
                for (int j = 0; j < (((LongTermTask) LongTermTasks.get(i).getTaskType()).getSubtasks().size()); j++) {

                    LinearLayout.LayoutParams subparams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
                    LinearLayout subLinerLayout=new LinearLayout(getActivity());
                    subLinerLayout.setLayoutParams(subparams);
                    subLinerLayout.setOrientation(LinearLayout.VERTICAL);
                    subLinerLayout.setBackgroundColor(getResources().getColor(R.color.subtask));//这里产生了错误
                    task_linerLayout.addView(subLinerLayout);


                    //子任务的名称
                    TextView subName=new TextView(getActivity());
                    subName.setText("子任务"+j+1+":"+subTasks.get(j).getSub_name());
                    subName.setHeight(50);
                    subName.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                    subName.setGravity(Gravity.CENTER);
                    subLinerLayout.addView(subName);


                    //子任务的具体描述
                    TextView subDescription=new TextView(getActivity());
                    subDescription.setText(subTasks.get(j).getDescription());
                    subDescription.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);;
                    subDescription.setHeight(50);
                    subDescription.setGravity(Gravity.CENTER);
                    subLinerLayout.addView(subDescription);
                    //不设置居中

                    //子任务的截止时间
                    TextView subDeadline=new TextView(getActivity());
                    subDeadline.setText(subTasks.get(j).getDeadline_sub());
                    subDeadline.setHeight(50);
                    subDeadline.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                    subDeadline.setGravity(Gravity.CENTER);
                    subLinerLayout.addView(subDeadline);
                    //添加空白
                    TextView gap=new TextView(getActivity());
                    gap.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                    gap.setHeight(2);
                    gap.setBackgroundColor(getResources().getColor(R.color.textView));
                    subLinerLayout.addView(gap);

                }
                TextView gap=new TextView(getActivity());
                gap.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                gap.setHeight(6);
                gap.setBackgroundColor(getResources().getColor(R.color.backgroundHui));
                task_linerLayout.addView(gap);

            }

        }
    }
}
package com.todolist;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.todolist.Object.CyclicTask;
import com.todolist.Object.LongTermTask;
import com.todolist.IO.SaveManager;
import com.todolist.Object.SubTask;
import com.todolist.Object.Task;
import com.todolist.Object.TemporalTask;

import java.util.ArrayList;
import java.util.List;

public class add_task extends Activity {

    protected List<SubTask> sub_task=new ArrayList<>();
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task);
       final RadioGroup radioGroup= findViewById(R.id.taskType);
       //全局的一个LongTerm

       //提交按键的监听与保存填写的内容
        Button submit=findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //事实上只有在复选框选择了的情况之下才能进行保存的工作
                EditText TaskName=findViewById(R.id.TaskName);//任务名称
                EditText Description=findViewById(R.id.Description);//人物的详细描述
                int checkedType=radioGroup.getCheckedRadioButtonId();//Type复选框的状态
                if (checkedType==findViewById(R.id.Type1).getId())
                {
                    //temporalTask的保存工作
                    EditText deadline=(EditText)findViewById(R.id.Deadline_temp);
                    TemporalTask temporalTask=new TemporalTask(deadline.getText().toString());
                    Task task=new Task(TaskName.getText().toString(),Description.getText().toString(),temporalTask);
                    SaveManager.save((String)add_task.this.getIntent().getStringExtra("path"),task);
                    Toast.makeText(add_task.this,"保存成功！",Toast.LENGTH_LONG).show();
                    finish();
                }
                else if (checkedType==findViewById(R.id.Type2).getId())
                {
                    //CyclicTask的保存工作
                    EditText data=findViewById(R.id.data_cyclic);
                    EditText times=findViewById(R.id.times_cyclic);
                    EditText cycle=findViewById(R.id.cycle);
                    CyclicTask cyclicTask=new CyclicTask(data.getText().toString(),Integer.parseInt(times.getText().toString()),cycle.getText().toString());
                    Task task=new Task(TaskName.getText().toString(),Description.getText().toString(),cyclicTask);
                    SaveManager.save((String)add_task.this.getIntent().getStringExtra("path"),task);
                    Toast.makeText(add_task.this,"保存成功！",Toast.LENGTH_LONG).show();
                    finish();
                }
                else if (checkedType==findViewById(R.id.Type3).getId())
                {

                    //LongTermTask的submit响应事件
                    EditText deadline=findViewById(R.id.Deadline_long);
                    LongTermTask longTermTask=new LongTermTask(deadline.getText().toString());
                    longTermTask.setSubtasks(sub_task);
                    Task task=new Task(TaskName.getText().toString(),Description.getText().toString(),longTermTask);
                    SaveManager.save((String)add_task.this.getIntent().getStringExtra("path"),task);
                    finish();
                }
            }
        });

        //Radio部分的监听程序
        radioGroup.setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton TemporalTask=findViewById(R.id.Type1);
                        RadioButton CyclicTask=findViewById(R.id.Type2);
                        RadioButton LongTerm=findViewById(R.id.Type3);
                        ConstraintLayout tempView=findViewById(R.id.temp);
                        ConstraintLayout CycView=findViewById(R.id.cyclic);
                        ConstraintLayout LongView=findViewById(R.id.LongTerm);
                        TextView type_feature=findViewById(R.id.type_feature);

                        if (checkedId==TemporalTask.getId())
                        {
                            tempView.setVisibility(View.VISIBLE);
                            CycView.setVisibility(View.GONE);
                            LongView.setVisibility(View.GONE);
                            type_feature.setText("TemporalTask");
                        }
                        else if (checkedId==CyclicTask.getId())
                        {
                            tempView.setVisibility(View.GONE);
                            CycView.setVisibility(View.VISIBLE);
                            LongView.setVisibility(View.GONE);
                            type_feature.setText("CyclicTask");
                        }
                        else {
                            tempView.setVisibility(View.GONE);
                            CycView.setVisibility(View.GONE);
                            LongView.setVisibility(View.VISIBLE);
                            type_feature.setText("LongTermTask");
                        }
                    }
                }
        );
        ImageView addView=findViewById(R.id.imageView);
        SubTask subTask=null;
        addView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add_sub=new Intent(add_task.this,sub_task.class);
                startActivityForResult(add_sub,1001);

            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        //这里暂时没有任何的响应
        if (requestCode==1001&&resultCode==RESULT_OK)
        {
           sub_task.add((SubTask) data.getBundleExtra("subTask").getSerializable("subTask"));
        }
    }



}
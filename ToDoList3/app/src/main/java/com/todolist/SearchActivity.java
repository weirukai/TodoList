package com.todolist;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.todolist.IO.ReadManager;
import com.todolist.Object.CyclicTask;
import com.todolist.Object.LongTermTask;
import com.todolist.Object.Task;
import com.todolist.Object.TemporalTask;

import java.util.List;

public class SearchActivity extends Activity {
    List<Task> tasks=null;
    String  targetName=null;
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        tasks= ReadManager.taskParse(SearchActivity.this.getIntent().getStringExtra("path"));
        final EditText searchName=findViewById(R.id.searchName);
        Button search_button=findViewById(R.id.search_button);
        final TextView date=findViewById(R.id.searchDate);
        final TextView description=findViewById(R.id.searchDescription);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                targetName=searchName.getText().toString();
                if (targetName!=null)
                {
                    int i=0;//提权操作
                    for ( i = 0; i < tasks.size(); i++) {

                        if (tasks.get(i).getTaskName().contains(targetName))
                        {
                            description.setVisibility(View.VISIBLE);
                            date.setVisibility(View.VISIBLE);
                            description.setText(tasks.get(i).getDescription());

                            if (tasks.get(i).getTaskType().getTypeName().equals("TemporalTask"))
                            {
                                date.setText(((TemporalTask)tasks.get(i).getTaskType()).getDeadLine());

                            }else if (tasks.get(i).getTaskType().getTypeName().equals("CyclicTask"))
                            {
                                date.setText(((CyclicTask)tasks.get(i).getTaskType()).getDate());

                            }else if (tasks.get(i).getTaskType().getTypeName().equals("LongTermTask"))
                            {
                                date.setText(((LongTermTask)tasks.get(i).getTaskType()).getDeadline());
                            }
                            break;
                        }

                    }
                    if (i==tasks.size())
                    {
                        date.setVisibility(View.VISIBLE);
                        description.setVisibility(View.INVISIBLE);
                        date.setText("NOT FOUND!");
                    }
                }
            }
        });




    }
}

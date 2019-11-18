package com.todolist;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.todolist.IO.ReadManager;
import com.todolist.IO.SaveManager;
import com.todolist.Object.Task;

import java.util.List;

public class DeleteTaskActivity extends Activity {
    String targetName=null;
    List<Task>tasks=null;
    Task targetTask=null;
    public void onCreate(Bundle savedInstancedState)
    {
        super.onCreate(savedInstancedState);
        setContentView(R.layout.delete_task);
        final EditText deleteName=findViewById(R.id.deleteName);
        //deleteName.requestFocus();
        //deleteName.addOnAttachStateChangeListener();
        final String filePath=DeleteTaskActivity.this.getIntent().getStringExtra("path");
        tasks= ReadManager.taskParse(filePath);
       /* deleteName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                targetName=deleteName.getText().toString();
                if (targetName!=null)
                {
                    TextView deleteDescription=findViewById(R.id.deleteDescription);
                    for (int i = 0; i < tasks.size(); i++) {
                        if (tasks.get(i).getTaskName().contains(targetName))
                        {
                            deleteDescription.setText(tasks.get(i).getDescription());
                            targetTask=tasks.get(i);
                        }
                    }
                    deleteDescription.setText("NOT FOUND!");

                }
            }
        });
*/

       Button delete_search=findViewById(R.id.delete_search);
       delete_search.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               EditText deleteName=findViewById(R.id.deleteName);
               if (!deleteName.getText().toString().isEmpty())
               {
                   targetName=deleteName.getText().toString();
                   TextView deleteDescription=findViewById(R.id.deleteDescription);
                   int i=0;
                   for ( i = 0; i < tasks.size(); i++) {
                       if (tasks.get(i).getTaskName().contains(targetName))
                       {
                           deleteDescription.setVisibility(View.VISIBLE);
                           deleteDescription.setText(tasks.get(i).getDescription());
                           targetTask=tasks.get(i);
                           break;
                       }
                   }
                   if (i==tasks.size()) {
                       deleteDescription.setVisibility(View.VISIBLE);
                       deleteDescription.setText("NOT FOUND!");
                   }
               }
           }
       });


        //确认删除操作
        Button delete_button=findViewById(R.id.delete_button);
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (targetTask!=null)
                {
                    SaveManager.DeleteTask(targetTask,tasks,filePath);
                }
            }
        });






    }
}

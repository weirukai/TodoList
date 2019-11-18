package com.todolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.todolist.Object.SubTask;

public class sub_task extends Activity {
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_task);
        TextView textView=findViewById(R.id.subBar);
        textView.setText("addSubTask");
        Button submmit_sub=findViewById(R.id.submmit_sub);
        submmit_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubTask sub=null;
                EditText subName=findViewById(R.id.subName);
                EditText subDeadline=findViewById(R.id.subDeadline);
                EditText subDescription=findViewById(R.id.subDescription);

                if (!TextUtils.isEmpty(subDeadline.getText()))
                {
                    //创建一个子任务，并且把它交给主页面上面的LongTermTask
                    sub=new SubTask(subDescription.getText().toString(),subDeadline.getText().toString(),subName.getText().toString());
                    Intent intent=new Intent();
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("subTask",sub);
                    intent.putExtra("subTask",bundle);
                    setResult(RESULT_OK,intent);
                    Toast.makeText(sub_task.this,"子任务添加成功！",Toast.LENGTH_LONG).show();
                    finish();
                }


            }
        });
    }
}

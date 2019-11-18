package com.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.todolist.IO.SaveManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.FloatingWindow;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {
       private static String filePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        filePath=SaveManager.fileCreate(MainActivity.this,"save.json");
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);


        //添加新的任务的button部分
        Button addButton=findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add_task=new Intent(MainActivity.this,add_task.class);
                add_task.putExtra("path",filePath);
                startActivity(add_task);
            }
        });


        //删除任务的按键监听
        Button deleteButton=findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent delete=new Intent(MainActivity.this,DeleteTaskActivity.class);
                delete.putExtra("path",filePath);
                startActivity(delete);
                //
            }
        });


        //FloatButton的监听是处理事件，可以在所有的页面提供搜索的服务
        FloatingActionButton floatButton=findViewById(R.id.floatSearsh);
        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent search=new Intent(MainActivity.this,SearchActivity.class);
                search.putExtra("path",filePath);
                startActivity(search);
            }
        });
    }

}

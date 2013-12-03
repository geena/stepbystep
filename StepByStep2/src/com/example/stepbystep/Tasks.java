package com.example.stepbystep;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.stepbystep.R;
import com.example.stepbystep.dropboxdata.APIResponse;
import com.example.stepbystep.dropboxdata.Task;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class Tasks extends Activity{

	ListView tasks;
	APIResponse allTasks = new APIResponse();
	Button addTask;
	List<Task> _tasks;
	SimpleAdapter tasksAdpt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.tasks);

		allTasks = (APIResponse) Register.register.get("allTasks");
		
		addTask = (Button) findViewById(R.id.addTask);

		tasks = (ListView) findViewById(R.id.tasks);

		tasksAdpt = new SimpleAdapter(this, createMap(allTasks.getTasks()), android.R.layout.simple_list_item_1, new String[] {"result"}, new int[] {android.R.id.text1});

		_tasks = allTasks.getTasks();
		
		tasks.setAdapter(tasksAdpt);
		
		tasks.setOnItemClickListener(new TaskClickListener());
		
		addTask.setOnClickListener(new AddTaskClickListener());

	}

	private List<Map<String,String>> createMap(List<Task> list) {
		
		List<Map<String,String>> mapList = new ArrayList<Map<String,String>>();
		for (Task name: list){
			mapList.add(createResult("result", name.getTitle()));
		}
		return mapList;
	}

	private HashMap<String, String> createResult(String key, String name) {

		HashMap<String, String> result = new HashMap<String, String>();
		result.put(key, name);
		return result;
	}

	public class TaskClickListener implements AdapterView.OnItemClickListener{

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
		         
		         Register.register.put("currentTask", _tasks.get(arg2));
		         Register.register.put("taskIndex", arg2);
		         
		         Intent intent = new Intent(Tasks.this, SingleTask.class);
				 startActivity(intent);

			}
		}
	private class AddTaskClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			
	         Intent intent = new Intent(Tasks.this, SingleTask.class);
			 startActivity(intent);
			
		}
		
	}

}
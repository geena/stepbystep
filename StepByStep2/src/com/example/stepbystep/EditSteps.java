package com.example.stepbystep;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.stepbystep.R;
import com.example.stepbystep.dropboxdata.Step;
import com.example.stepbystep.dropboxdata.Task;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class EditSteps extends Activity{

	ListView _steps;
	Task _currentTask;
	SimpleAdapter stepsAdpt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.editsteps);

		_currentTask = (Task) Register.register.get("currentTask");

		_steps = (ListView) findViewById(R.id.steps);

		stepsAdpt = new SimpleAdapter(this, createMap(_currentTask.getSteps()), android.R.layout.simple_list_item_1, new String[] {"result"}, new int[] {android.R.id.text1});
		
		_steps.setAdapter(stepsAdpt);
		
		_steps.setOnItemClickListener(new stepClickListener());

	}

	private List<Map<String,String>> createMap(List<Step> list) {
		
		List<Map<String,String>> mapList = new ArrayList<Map<String,String>>();
		for (Step step: list){
			mapList.add(createResult("result", "Step: " + (list.indexOf(step) + 1)));
		}
		return mapList;
	}

	private HashMap<String, String> createResult(String key, String name) {

		HashMap<String, String> result = new HashMap<String, String>();
		result.put(key, name);
		return result;
	}

	public class stepClickListener implements AdapterView.OnItemClickListener{

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
		         
		         Register.register.put("editStep", _currentTask.getSteps().get(arg2));
		         Register.register.put("stepIndex", arg2);
		         
		         Intent intent = new Intent(EditSteps.this, AddorEditStep.class);
				 startActivity(intent);

			}
		}

}
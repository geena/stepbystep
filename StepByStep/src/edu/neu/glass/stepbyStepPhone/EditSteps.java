package edu.neu.glass.stepbyStepPhone;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.neu.glass.stepByStep.R;
import edu.neu.glass.stepbyStepPhone.dropboxdata.Step;
import edu.neu.glass.stepbyStepPhone.dropboxdata.Task;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class EditSteps extends Activity{

	Button addStep;
	ListView _steps;
	Task _currentTask;
	SimpleAdapter stepsAdpt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.editsteps);

		_currentTask = (Task) Register.register.get("currentTask");

		_steps = (ListView) findViewById(R.id.steps);
		
		addStep = (Button) findViewById(R.id.addStep);

		stepsAdpt = new SimpleAdapter(this, createMap(_currentTask.getSteps()), android.R.layout.simple_list_item_1, new String[] {"result"}, new int[] {android.R.id.text1});
		
		_steps.setAdapter(stepsAdpt);
		
		_steps.setOnItemClickListener(new stepClickListener());
		
		addStep.setOnClickListener(new AddStepListener());

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
	
	private class AddStepListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			
	         Intent intent = new Intent(EditSteps.this, AddorEditStep.class);
			 startActivity(intent);
			
		}
		
	}

}
package edu.neu.glass.stepbyStepPhone;

import java.util.ArrayList;
import java.util.List;

import edu.neu.glass.stepByStep.R;
import edu.neu.glass.stepbyStepPhone.dropboxdata.APIResponse;
import edu.neu.glass.stepbyStepPhone.dropboxdata.Task;
import edu.neu.glass.stepbyStepPhone.dropboxdata.Step;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SingleTask extends Activity {

	EditText title;
	Button phase;
	Button editSteps;
	Button save;
	Context context;
	Task current;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.singletask);

		context = this;
		
		DropBoxService.setAcctMgr(getApplicationContext());

		phase = (Button) findViewById(R.id.phase);
		editSteps = (Button) findViewById(R.id.editsteps);
		save = (Button) findViewById(R.id.savechanges);
		title = (EditText) findViewById(R.id.addTitle);
		title.setText("");

		if (Register.register.containsKey("currentTask")){
			current = (Task) Register.register.get("currentTask");
			title.setText(current.getTitle());
		}

		phase.setOnClickListener(new PhaseListener());
		editSteps.setOnClickListener(new EditStepsListener());
		save.setOnClickListener(new SaveListener());
	}
	
	@Override
	public void onBackPressed(){
		resetRegister();
		
		super.onBackPressed();
	}
	
	private void resetRegister() {
		
        Register.register.remove(Register.CURRENT_TASK);
        Register.register.remove(Register.TASK_INDEX);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.fullscreen, menu);
		return true;
	}

	private class PhaseListener implements OnClickListener{
		@Override
		public void onClick(View v){

		}

	}

	private class EditStepsListener implements OnClickListener{
		@Override
		public void onClick(View v){

			if (current == null){
				saveTask();
			}
			Intent intent = new Intent(SingleTask.this, EditSteps.class);
			startActivity(intent);

		}

	}

	private class SaveListener implements OnClickListener{
		@Override
		public void onClick(View v){

			if (current == null){
				saveTask();
			}
		}

	}
	private void saveTask(){
		
		APIResponse data = (APIResponse) Register.register.get("allTasks");
		List<Task> allTasks = data.getTasks();
		String titleEntered = title.getText().toString();
		Task task = new Task(titleEntered, new ArrayList<Step>());
		Register.register.put("currentTask", task);
		allTasks.add(task);
		APIResponse data2 = new APIResponse();
		data2.setTasks(allTasks);

		DropBoxService.writeToDropBox(data2);

		Toast.makeText(context, "Task Saved", Toast.LENGTH_SHORT).show();
	}
}
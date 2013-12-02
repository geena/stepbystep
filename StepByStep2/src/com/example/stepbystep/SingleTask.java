package com.example.stepbystep;

import com.example.stepbystep.dropboxdata.Task;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SingleTask extends Activity {

	EditText title;
	Button phase;
	Button editSteps;
	Button save;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.singletask);
		
		phase = (Button) findViewById(R.id.phase);
		editSteps = (Button) findViewById(R.id.editsteps);
		save = (Button) findViewById(R.id.savechanges);
		title = (EditText) findViewById(R.id.addTitle);
		Task current = (Task) Register.register.get("currentTask");
		title.setText(current.getTitle());
	
		phase.setOnClickListener(new PhaseListener());
		editSteps.setOnClickListener(new EditStepsListener());
		save.setOnClickListener(new SaveListener());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
			
	         Intent intent = new Intent(SingleTask.this, EditSteps.class);
			 startActivity(intent);
			 
		}

	}
	
	private class SaveListener implements OnClickListener{
		@Override
		public void onClick(View v){
			
		}

	}

}
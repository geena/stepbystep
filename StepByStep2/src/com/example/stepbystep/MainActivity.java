package com.example.stepbystep;

import com.example.stepbystep.tasks.Tasks;
import com.vendi.activities.MainActivity;
import com.vendi.activities.Vendors;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	Button activities;
	Button settings;
	Button help;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		activities = (Button) findViewById(R.id.activities);
		settings = (Button) findViewById(R.id.settings);
		help = (Button) findViewById(R.id.help);
		
		activities.setOnClickListener(new ActivityListener());
		settings.setOnClickListener(new SettingsListener());
		help.setOnClickListener(new HelpListener());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private class ActivityListener implements OnClickListener{
		@Override
		public void onClick(View v){
			
			Intent intent = new Intent(MainActivity.this, Tasks.class);
			startActivity(intent);
		}

	}
	private class SettingsListener implements OnClickListener{
		@Override
		public void onClick(View v){
			
		}

	}
	
	private class HelpListener implements OnClickListener{
		@Override
		public void onClick(View v){
			
		}

	}

}

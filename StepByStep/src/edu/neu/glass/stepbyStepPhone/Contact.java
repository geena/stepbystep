package edu.neu.glass.stepbyStepPhone;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import edu.neu.glass.stepByStep.R;

public class Contact extends Activity {
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.addcontact);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.fullscreen, menu);
		return true;
	}

}
package edu.neu.glass.stepByStepMurph2;

import edu.neu.glass.stepByStepMurph2.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;


public class PhoneHome extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phonehome);
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.fullscreen, menu);
		return true;
	}

	
}

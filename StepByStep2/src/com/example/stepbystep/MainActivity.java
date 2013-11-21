package com.example.stepbystep;

import com.dropbox.sync.android.DbxAccountManager;
import com.dropbox.sync.android.DbxException.Unauthorized;
import com.dropbox.sync.android.DbxFileSystem;
import com.example.stepbystep.tasks.Tasks;

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
	private DbxAccountManager _acctMgr;
	private Activity context;
	private DbxFileSystem dbxFs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		context = this;
		
		_acctMgr = DbxAccountManager.getInstance(getApplicationContext(), "j1bohgxwhlirlq6", "kamif5qmk6m7und");
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
			
			_acctMgr.startLink(context, 0);
			
			Intent intent = new Intent(MainActivity.this, Tasks.class);
			startActivity(intent);
		}

	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == 0) {
	        if (resultCode == Activity.RESULT_OK) {
	            getFiles();
	        } else {
	            // ... Link failed or was cancelled by the user.
	        }
	    } else {
	        super.onActivityResult(requestCode, resultCode, data);
	    }
	}
	
	private void getFiles() {
		try {
			dbxFs = DbxFileSystem.forAccount(_acctMgr.getLinkedAccount());
		} catch (Unauthorized e) {
			e.printStackTrace();
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

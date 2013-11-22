package com.example.stepbystep;

import java.io.IOException;

import com.dropbox.sync.android.DbxAccountManager;
import com.dropbox.sync.android.DbxException;
import com.dropbox.sync.android.DbxException.Unauthorized;
import com.dropbox.sync.android.DbxFile;
import com.dropbox.sync.android.DbxFileSystem;
import com.dropbox.sync.android.DbxPath;
import com.dropbox.sync.android.DbxPath.InvalidPathException;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	Button activities;
	Button settings;
	Button help;
	Context context;
	DbxAccountManager _acctMgr;
	DbxFileSystem dbxFs;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		
        context = this;
		
		_acctMgr = DbxAccountManager.getInstance(getApplicationContext(), "j1bohgxwhlirlq6", "kamif5qmk6m7und");
		
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
			
			if (!_acctMgr.hasLinkedAccount()){
			_acctMgr.startLink((Activity) context, 0);
			} else {
				getFiles();
			}
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
	
	private class SettingsListener implements OnClickListener{
		@Override
		public void onClick(View v){
			
		}

	}
	
	private void getFiles(){
    	DbxFile testFile = null;
    	try {
			DbxFileSystem dbxFs = DbxFileSystem.forAccount(_acctMgr.getLinkedAccount());
			 testFile = dbxFs.open(new DbxPath("test.txt"));
			    String contents = testFile.readString();
			    Log.d("Dropbox Test", "File contents: " + contents);
			} catch (Unauthorized e) {
				e.printStackTrace();
			} catch (InvalidPathException e) {
				e.printStackTrace();
			} catch (DbxException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
			    testFile.close();
			}
	}
	
	private class HelpListener implements OnClickListener{
		@Override
		public void onClick(View v){
			
		}

	}

}

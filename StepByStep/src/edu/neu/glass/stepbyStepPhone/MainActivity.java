package edu.neu.glass.stepbyStepPhone;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.ObjectMapper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.dropbox.sync.android.DbxAccount;
import com.dropbox.sync.android.DbxAccountManager;
import com.dropbox.sync.android.DbxException;
import com.dropbox.sync.android.DbxException.Unauthorized;
import com.dropbox.sync.android.DbxFile;
import com.dropbox.sync.android.DbxFileSystem;
import com.dropbox.sync.android.DbxPath;
import com.dropbox.sync.android.DbxPath.InvalidPathException;

import edu.neu.glass.stepByStep.R;
import edu.neu.glass.stepbyStepPhone.dropboxdata.APIResponse;

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

		_acctMgr = DbxAccountManager.getInstance(getApplicationContext(), "v8j9ryp4ob0xct6", "5icwv655dpleqsi");

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
		getMenuInflater().inflate(R.menu.fullscreen, menu);
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

			Intent intent = new Intent(MainActivity.this, PairGlass.class);
			startActivity(intent);

		}

	}

	private void getFiles(){
		DbxFile testFile = null;
		try {
			DbxAccount acct = _acctMgr.getLinkedAccount();

			DbxFileSystem dbxFs = DbxFileSystem.forAccount(acct);
			testFile = dbxFs.open(new DbxPath("glass1.txt"));
			String contents = testFile.readString();
			Log.d("Dropbox Test", "File contents: " + contents);
			APIResponse response = parse(contents);
			Register.register.put(Register.ALL_DATA, response);
			Intent intent = new Intent(MainActivity.this, Tasks.class);
			startActivity(intent);
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

	private APIResponse parse(String json)
	{
		APIResponse _response = new APIResponse();
		try
		{
			_response = new ObjectMapper().readValue(json, APIResponse.class);
			return _response;
		}
		catch (JsonParseException e1)
		{
			e1.printStackTrace();
		}
		catch (IOException e1)
		{
			e1.printStackTrace();
		}
		return null;
	}

	private class HelpListener implements OnClickListener{
		@Override
		public void onClick(View v){

			Intent intent = new Intent(MainActivity.this, Contact.class);
			startActivity(intent);

		}

	}

}

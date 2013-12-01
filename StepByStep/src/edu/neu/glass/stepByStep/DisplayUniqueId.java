package edu.neu.glass.stepByStep;

import java.util.UUID;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import edu.neu.mhealth.api.KeyValueAPI;

public class DisplayUniqueId extends AsyncTask<Object,Void, String>{

	public static String UNIQUE_NAME = null;
	public static String uniqueDeviceId = null;
	public static final String PREF_UNIQUE_ID = "PREF_UNIQUE_ID";
	TextView txtView;
	Context context;
	
	public DisplayUniqueId(TextView txtView){
		this.txtView = txtView;
	}
	@Override
	protected String doInBackground(Object... params) {
		this.context = (Context)params[0];
		UNIQUE_NAME = KeyValueAPI.get("phiteamone", "teamone1", get_id(context));
		Log.d("unique id", "UNIQUE_NAME " + UNIQUE_NAME);
		if(UNIQUE_NAME.equalsIgnoreCase("Error: No Such Key")){
			Log.d("Enter Loop", "Hi");
			KeyValueAPI.put("phiteamone", "teamone1", "LAST","0");
			String LAST_Nmbr = KeyValueAPI.get("phiteamone", "teamone1", "LAST");
			UNIQUE_NAME = "glass" + Integer.toString(Integer.parseInt(LAST_Nmbr) + 1);
			KeyValueAPI.put("phiteamone", "teamone1",uniqueDeviceId,UNIQUE_NAME);
			KeyValueAPI.put("phiteamone", "teamone1","LAST",Integer.toString(Integer.parseInt(LAST_Nmbr) + 1));
		}
		return UNIQUE_NAME;
	}
	protected void onPostExecute(String result){
		txtView.setText(result);
	}
	public synchronized static String get_id(Context context){
		if(uniqueDeviceId == null){
			SharedPreferences sharedPrefs = context.getSharedPreferences(PREF_UNIQUE_ID, Context.MODE_PRIVATE);
			uniqueDeviceId = sharedPrefs.getString(PREF_UNIQUE_ID, null);
			if(uniqueDeviceId == null){
				uniqueDeviceId = UUID.randomUUID().toString();
				Editor editor = sharedPrefs.edit();
				editor.putString(PREF_UNIQUE_ID, uniqueDeviceId);
				editor.commit();
			}
		}
		return uniqueDeviceId;
		
	}
}
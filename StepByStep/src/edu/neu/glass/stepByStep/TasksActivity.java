package edu.neu.glass.stepByStep;


import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.message.BufferedHeader;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.Menu;
import android.view.MotionEvent;
import android.widget.TextView;

public class TasksActivity extends Activity implements GestureDetector.OnGestureListener,OnDoubleTapListener{

	private GestureDetector gestureDetector;
	public static String[] tasks;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tasks);
		gestureDetector = new GestureDetector(this,this);
		
		Typeface tfRobotoBlack = Typeface.createFromAsset(getAssets(), "fonts/roboto_black.ttf");
		Typeface tfRobotoLight = Typeface.createFromAsset(getAssets(), "fonts/roboto_light.ttf");
		Typeface tfRobotoRegular = Typeface.createFromAsset(getAssets(), "fonts/roboto_regular.ttf");
		TextView nxtChoicetxt = (TextView) findViewById(R.id.nxtChoicetxt);
		nxtChoicetxt.setTypeface(tfRobotoLight);
		TextView backtxt = (TextView) findViewById(R.id.backtxt);
		backtxt.setTypeface(tfRobotoLight);
		TextView taptxt = (TextView) findViewById(R.id.taptxt);
		taptxt.setTypeface(tfRobotoRegular);
		TextView wantThistxt = (TextView) findViewById(R.id.wantThistxt);
		wantThistxt.setTypeface(tfRobotoLight);
		TextView taptaptxt = (TextView) findViewById(R.id.taptaptxt);
		taptaptxt.setTypeface(tfRobotoRegular);
		TextView needHelptxt = (TextView) findViewById(R.id.needHelptxt);
		needHelptxt.setTypeface(tfRobotoLight);
		
		Typeface tfRobotoThin = Typeface.createFromAsset(getAssets(), "fonts/roboto_thin.ttf");
		TextView pickAnActivity = (TextView)findViewById(R.id.pickAnActivity);
		pickAnActivity.setTypeface(tfRobotoBlack);
		TextView ScriptHeader = (TextView)findViewById(R.id.ScriptHeader);
		ScriptHeader.setTypeface(tfRobotoThin);
		
		new loadTasks().execute();
		
	}
	
	public class loadTasks extends AsyncTask<String, Integer, String>{

		ProgressDialog dialog;
		
		protected void onPreExecute(){
			dialog = new ProgressDialog(TasksActivity.this);
			dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			dialog.setMax(100);
			dialog.setTitle("LOADING TASKS...");
			dialog.show();
		}
		
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			for(int i = 0 ; i < 50 ; i++){
				publishProgress(2);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}	
			
			dialog.dismiss();
			
			return null; 
		}
		
		protected void onProgressUpdate(Integer... progress){
			dialog.incrementProgressBy(progress[0]);
		}
		
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.choose_task, menu);
		return true;
	}
	
	@Override
	public boolean onGenericMotionEvent(MotionEvent event){
		gestureDetector.onTouchEvent(event);
		return true;
	}

	@Override
	public boolean onDoubleTap(MotionEvent e) {
		Log.d("App Home","Starting Emergency Activity");
		Intent i = new Intent(this,EmergencyActivity.class);
		startActivity(i);	
	return true;
	}

	@Override
	public boolean onDoubleTapEvent(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onSingleTapConfirmed(MotionEvent e) {
		Log.d("App Home","Starting new session");
		TextView ScriptHeader = (TextView)findViewById(R.id.ScriptHeader);
		String taskName = ScriptHeader.getText().toString();
		Intent i = new Intent(this,CallOptionsActivity.class);
		i.putExtra("TASK_NAME", taskName);
		startActivity(i);
		return true;
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		TextView ScriptHeader = (TextView)findViewById(R.id.ScriptHeader);
		String taskName = ScriptHeader.getText().toString();
		if (velocityX < -2500) {
			if (taskName.equalsIgnoreCase("Library")){
        		ScriptHeader.setText("Grocery Shopping");
        	}
        } else if (velocityX > 2500) {
        	if (taskName.equalsIgnoreCase("Grocery Shopping")){
        		ScriptHeader.setText("Library");
        	}
        }
        return true;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

}

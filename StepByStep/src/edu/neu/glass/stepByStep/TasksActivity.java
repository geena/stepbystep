package edu.neu.glass.stepByStep;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

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
	public static List<String> tasks = new LinkedList<String>() ;
	public static String TASK_URL;
	public static String glassName = "";
	public static String HOST_URL = "http://glassphiteam1.weebly.com/uploads/2/4/6/8/24684595/";
	
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
		
		taptaptxt.setKeepScreenOn(true);
		
		Typeface tfRobotoThin = Typeface.createFromAsset(getAssets(), "fonts/roboto_thin.ttf");
		TextView pickAnActivity = (TextView)findViewById(R.id.pickAnActivity);
		pickAnActivity.setTypeface(tfRobotoBlack);
		TextView ScriptHeader = (TextView)findViewById(R.id.ScriptHeader);
		ScriptHeader.setTypeface(tfRobotoThin);
		
		Intent intent = getIntent();	
		glassName = intent.getExtras().getString("LOGIN");
		Log.d("login", glassName);
		
		TASK_URL = HOST_URL + glassName + "_" + "tasks.txt";
		new loadTasks((TextView)findViewById(R.id.ScriptHeader)).execute(TASK_URL);
		
	}
	
	public class loadTasks extends AsyncTask<String, Integer, String>{
		
		TextView txtView;
		ProgressDialog dialog;
		
		public loadTasks(TextView txtView){
			this.txtView = txtView;
		}
		
		protected void onPreExecute(){
			dialog = new ProgressDialog(TasksActivity.this);
			dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			dialog.setMax(100);
			dialog.setTitle("LOADING TASKS...");
			dialog.show();
		}
		
		@Override
		protected String doInBackground(String... urls) {
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
			
			String urldisplay = urls[0];
			BufferedReader in = null;
			String line ="";
			try {
				URL urltxt = new URL(urldisplay);
				in = new BufferedReader(new InputStreamReader(urltxt.openStream()));
				while((line = in.readLine()) != null){
					tasks.add(line);
				}
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace(); 
			}finally{
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			dialog.dismiss();
			return tasks.get(0); 
		}
		
		protected void onProgressUpdate(Integer... progress){
			dialog.incrementProgressBy(progress[0]);
		}
		
		protected void onPostExecute(String result){
			txtView.setText(result);
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
		i.putExtra("glassName",glassName);
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
		i.putExtra("TASK_FULLNAME", taskName);
		taskName = taskName.toLowerCase();
		i.putExtra("TASK_NAME",taskName.replace(" ", ""));
		Log.d("glass - ", glassName);
		i.putExtra("glassName",glassName);
		startActivity(i);
		finish();
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
		int NbrOfTasks = tasks.size();
		int currentPosition = 0;
		for(int i = 0; i < NbrOfTasks ; i++){
			if (tasks.get(i).equalsIgnoreCase(taskName)){
				currentPosition = i;
			}
		}
		
		if (velocityX < -2500 && (currentPosition > 0)) {
			ScriptHeader.setText(tasks.get(currentPosition - 1));
        } else if (velocityX > 2500 && (currentPosition < NbrOfTasks - 1)) {
        	ScriptHeader.setText(tasks.get(currentPosition + 1));
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

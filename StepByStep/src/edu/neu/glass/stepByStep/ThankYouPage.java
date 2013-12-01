package edu.neu.glass.stepByStep;

import edu.neu.glass.stepByStep.R;
import edu.neu.mhealth.api.KeyValueAPI;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

public class ThankYouPage extends Activity implements GestureDetector.OnGestureListener,OnDoubleTapListener {

	private GestureDetector gestureDetector;
	public String Task_Name;
	public String glassName;
	public String Task_completed;
	public int noCompleted;
	public int count =1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_thank_you);
		gestureDetector = new GestureDetector(this,this);
		Typeface tfRobotoBlack = Typeface.createFromAsset(getAssets(), "fonts/roboto_black.ttf");
		Typeface tfRobotoLight = Typeface.createFromAsset(getAssets(), "fonts/roboto_light.ttf");
		Typeface tfRobotoRegular = Typeface.createFromAsset(getAssets(), "fonts/roboto_regular.ttf");
		
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
		
		TextView thankYoutxt = (TextView) findViewById(R.id.thankYoutxt);
		thankYoutxt.setTypeface(tfRobotoRegular);
		
		Intent intent = getIntent();
		Task_Name = intent.getExtras().getString("TASK_FULLNAME");	
		glassName = intent.getExtras().getString("glassName");
		
		
		new getFeedbackParams((TextView) findViewById(R.id.thankYoutxt)).execute();
		
		
	}
	
	public class getFeedbackParams extends AsyncTask<Void, Void, String>{
		TextView thankYoutxtView;
		String thankYoutxt;
		public getFeedbackParams(TextView thankYoutxtView){
			this.thankYoutxtView = thankYoutxtView;
		}
		
		@Override
		protected String doInBackground(Void... arg0) {
			Task_completed = KeyValueAPI.get("phiteamone", "teamone1",glassName+"_"+ Task_Name + "_done");
			if(Task_completed.equalsIgnoreCase("Error: No Such Key")){
				thankYoutxt = "Great Job !" + "\n" +"You have competed " + Task_Name + " 1 time" + "\n :)";
				KeyValueAPI.put("phiteamone", "teamone1",glassName +"_"+ Task_Name + "_done","1");
			}
			else{
				noCompleted = Integer.parseInt(Task_completed) + 1;
				thankYoutxt = "Great Job !" + "\n" + "You have competed " + Task_Name + " " + noCompleted + " times" + "\n :)";
				KeyValueAPI.put("phiteamone", "teamone1",glassName +"_"+ Task_Name + "_done","" + noCompleted);
			}
			return thankYoutxt;
		}
		
		protected void onPostExecute(String result){
			thankYoutxtView.setText(result);
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
		if(count == 2){	
			finish();
		}else{
			Toast.makeText(getApplicationContext(), "Tap Again to Exit", Toast.LENGTH_SHORT).show();
			count++;
		}
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
        return false;
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

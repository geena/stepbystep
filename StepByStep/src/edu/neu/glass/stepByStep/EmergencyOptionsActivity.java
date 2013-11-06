package edu.neu.glass.stepByStep;

import edu.neu.glass.stepByStep.R;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.MotionEvent;
import android.widget.TextView;

public class EmergencyOptionsActivity extends Activity implements GestureDetector.OnGestureListener,OnDoubleTapListener {

	private GestureDetector gestureDetector;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_emergency_options);
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
		TextView CallOptionTxtViewHelper = (TextView)findViewById(R.id.CallOptionTxtViewHelper);
		CallOptionTxtViewHelper.setTypeface(tfRobotoThin);
		TextView helperhdrhelper = (TextView)findViewById(R.id.helperhdrhelper);
		helperhdrhelper.setTypeface(tfRobotoBlack);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.emergency, menu);
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
		
		
		return false;
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		TextView callOptionsTxt = (TextView) findViewById(R.id.CallOptionTxtViewHelper);
		Log.d("callOptionsTxt", callOptionsTxt.getText().toString());
		if (velocityX < -2500) {
			if(callOptionsTxt.getText().toString().equalsIgnoreCase("Audio call with helper?")){
        		callOptionsTxt.setText("Video call with helper?");
        		callOptionsTxt.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.video_icon1);
        	}    
        } else if (velocityX > 2500) {
        	if(callOptionsTxt.getText().toString().equalsIgnoreCase("Video call with helper?")){
        		callOptionsTxt.setText("Audio call with helper?");
        		callOptionsTxt.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.audio_icon);
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

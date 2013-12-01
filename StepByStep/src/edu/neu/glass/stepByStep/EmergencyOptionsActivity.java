package edu.neu.glass.stepByStep;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.Menu;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

import com.openclove.ovx.OVXCallListener;
import com.openclove.ovx.OVXException;
import com.openclove.ovx.OVXView;

public class EmergencyOptionsActivity extends Activity implements GestureDetector.OnGestureListener,OnDoubleTapListener {

	private GestureDetector gestureDetector;
	public static String CALL_TYPE;
	public static OVXView ovxView;
	private ScriptPage1 currentActivity;
	public static String USER_ID = "GLASS_USER";
	public static String GROUP_ID = "";
	public String glassName ="";
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
		
		Intent intent = getIntent();
		glassName = intent.getExtras().getString("glassName");
		GROUP_ID = glassName;
		taptaptxt.setKeepScreenOn(true);
		ovxView = OVXView.getOVXContext(this);
		
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		//exit the call before destroying the activity
		if (!ovxView.isCallOn()) {
			ovxView.exitCall();
		}
		
		//and free the resources used by OVX context
		ovxView.unregister();		


		android.os.Process.killProcess(android.os.Process.myPid());
	}
	
	@Override
	public void onResume(){
		super.onResume();
		//exit the call before destroying the activity
		if (!ovxView.isCallOn()) {
			ovxView.exitCall();
		}
				
			
	}	
	@Override
	public void onPause(){
		super.onPause();
		//exit the call before destroying the activity
		if (!ovxView.isCallOn()) {
			ovxView.exitCall();
		}
				
			
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
		return true;
	}

	@Override
	public boolean onDoubleTapEvent(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onSingleTapConfirmed(MotionEvent e) {
		
		TextView callType = (TextView)findViewById(R.id.CallOptionTxtViewHelper);
		String callString = callType.toString();
		if (callString.equalsIgnoreCase("Video call with helper?")){
			CALL_TYPE = "VideoCall";
		}else{
			CALL_TYPE = "AudioCall";
		}
	
		
		try {
			ovxView.setApiKey("ph74wfeexb8q7s2bnkyeekg8");
			ovxView.setOvxUserId(USER_ID);
			ovxView.setOvxGroupId(GROUP_ID);
			ovxView.setShowOVXMenuOnTap(false);
			ovxView.setVisibility(false);
			ovxView.setRemoteViewWidth(640);
			ovxView.setRemoteViewHeight(360);
			ovxView.enableRemoteGesture(true);	
			//ovxView.setVideoCodec("vp9");
			if (!ovxView.isCallOn()) {
				//Checks if the call is on 
				try {
					ovxView.call(); // Initiates call and starts a session with the specified OVX group id and other parameters set earlier. 
					ovxView.addOtherUserToGroupChat("voice", "TEL","CARETAKER_USER");
				} catch (OVXException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
			}else{
				try {
					ovxView.exitCall();
					ovxView.call(); // Initiates call and starts a session with the specified OVX group id and other parameters set earlier. 
					ovxView.addOtherUserToGroupChat("voice", "TEL","CARETAKER_USER");
				} catch (OVXException exe) {
					// TODO Auto-generated catch block
					exe.printStackTrace();
				}
			}	
			callListener();
			
		}catch (OVXException exw) {
			// TODO Auto-generated catch block
			exw.printStackTrace();
		}
		
		
		return true;
	}
	
	
	
	private void callListener() {
		ovxView.setCallListener(new OVXCallListener() {

			@Override
			public void callEnded() {
				Toast.makeText(getApplicationContext(), "Call Ended", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void callFailed() {
				Toast.makeText(getApplicationContext(), "Call Failed", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void callStarted() {
				Toast.makeText(getApplicationContext(), "Call Started", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void callTerminated(String arg0) {
				Toast.makeText(getApplicationContext(), "Call Terminated", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void ovxReceivedData(String arg0) {
				
			}
			
			
		});
		
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

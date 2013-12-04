package edu.neu.glass.stepbyStepPhone;

import com.openclove.ovx.OVXCallListener;
import com.openclove.ovx.OVXException;
import com.openclove.ovx.OVXView;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import edu.neu.glass.stepByStep.R;
import edu.neu.glass.stepByStep.ScriptPage1;

public class PairGlass extends Activity {
	
	public static OVXView ovxView;
	private ScriptPage1 currentActivity;
	public static String USER_ID = "CARETAKER_USER";
	public static String GROUP_ID = "glass1";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.pairglass);
		ovxView = OVXView.getOVXContext(this);
		Button btnSync = (Button)findViewById(R.id.btnSync);
		Button btnSwitchUser = (Button)findViewById(R.id.btnSwitchUser);
		Button btnEndCall = (Button)findViewById(R.id.btnEndCall);
		
		btnSync.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					ovxView.setApiKey("ph74wfeexb8q7s2bnkyeekg8");
					ovxView.setOvxUserId(USER_ID);
					ovxView.setOvxGroupId(GROUP_ID);
					ovxView.setShowOVXMenuOnTap(false);
					ovxView.setRemoteViewWidth(480);
					ovxView.setRemoteViewHeight(360);
					ovxView.enableRemoteGesture(true);
					if (!ovxView.isCallOn()) {
						//Checks if the call is on 
						try {
							ovxView.call(); // Initiates call and starts a session with the specified OVX group id and other parameters set earlier. 
							//ovxView.addOtherUserToGroupChat("voice", "TEL","CARETAKER_USER");
						} catch (OVXException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}else{
						try {
							ovxView.exitCall();
							ovxView.call(); // Initiates call and starts a session with the specified OVX group id and other parameters set earlier. 
							//ovxView.addOtherUserToGroupChat("voice", "TEL","CARETAKER_USER");
						} catch (OVXException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
						
					callListener();
				}catch (OVXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		btnSwitchUser.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (ovxView.isCallOn()) {
					try {
						ovxView.switchLayer("2");
						
					} catch (OVXException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}	
			}
		});
		btnEndCall.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
					if (ovxView.isCallOn()) {
						ovxView.exitCall();
					}		
			}
		});
		
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
	public void onDestroy() {
		super.onDestroy();
		
		//exit the call before destroying the activity
		if (!ovxView.isCallOn()) {
			ovxView.exitCall();
		}
		
		//and free the resources used by OVX context
		ovxView.unregister();		

	}
		//android.os.Process.killProcess(android.os.Process.myPid());
	
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
		getMenuInflater().inflate(R.menu.fullscreen, menu);
		return true;
	}
}
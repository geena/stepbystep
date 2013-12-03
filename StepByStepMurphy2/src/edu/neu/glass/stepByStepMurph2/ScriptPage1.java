package edu.neu.glass.stepByStepMurph2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import com.openclove.ovx.OVXCallListener;
import com.openclove.ovx.OVXException;
import com.openclove.ovx.OVXView;

import edu.neu.glass.stepByStepMurph2.R;
import edu.neu.glass.stepByStepMurph2.R.drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.View;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ScriptPage1 extends Activity implements GestureDetector.OnGestureListener,OnDoubleTapListener{

	private GestureDetector gestureDetector;
	public String Task_Name;
	public String glassName;
	public String TASK_FULLNAME;
	public String CALL_TYPE;
	public String SCRIPT_IMAGE_URL = "";
	public String SCRIPT_TXT_URL ="";
	public String NEXT_IMAGE_URL ="";
	public static String HOST_URL = "http://glassphiteam1.weebly.com/uploads/2/4/6/8/24684595/";
	public static HashMap<String,String> stepNbrDescMap = new HashMap<String,String>();
	public static OVXView ovxView;
	private ScriptPage1 currentActivity;
	public static String USER_ID = "GLASS_USER";
	public static String GROUP_ID = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_script_page1);
		gestureDetector = new GestureDetector(this,this);
		
		Typeface tfRobotoBlack = Typeface.createFromAsset(getAssets(), "fonts/roboto_black.ttf");
		Typeface tfRobotoLight = Typeface.createFromAsset(getAssets(), "fonts/roboto_light.ttf");
		Typeface tfRobotoRegular = Typeface.createFromAsset(getAssets(), "fonts/roboto_regular.ttf");
		TextView nxtChoicetxt = (TextView) findViewById(R.id.nxtChoicetxt);
		nxtChoicetxt.setTypeface(tfRobotoLight);
		TextView backtxt = (TextView) findViewById(R.id.backtxt);
		backtxt.setTypeface(tfRobotoLight);
		TextView taptaptxt = (TextView) findViewById(R.id.taptaptxt);
		taptaptxt.setTypeface(tfRobotoRegular);
		TextView needHelptxt = (TextView) findViewById(R.id.needHelptxt);
		needHelptxt.setTypeface(tfRobotoLight);
		
		taptaptxt.setKeepScreenOn(true);
		
		Typeface tfRobotoThin = Typeface.createFromAsset(getAssets(), "fonts/roboto_thin.ttf");
		TextView stepDesctxt = (TextView)findViewById(R.id.stepDesctxt);
		stepDesctxt.setTypeface(tfRobotoThin);
		TextView stepNbrtxt = (TextView)findViewById(R.id.helperhdrhelper);
		stepNbrtxt.setTypeface(tfRobotoBlack);
		
		Intent intent = getIntent();
		Task_Name = intent.getExtras().getString("TASK_NAME");	
		glassName = intent.getExtras().getString("glassName");
		GROUP_ID = glassName;
		TASK_FULLNAME = intent.getExtras().getString("TASK_FULLNAME");
		CALL_TYPE = intent.getExtras().getString("CALL_TYPE");
		Log.d("glass - ", glassName);
		SCRIPT_IMAGE_URL =  HOST_URL + glassName + "_" + Task_Name + "_" + 1 + ".jpg";
		SCRIPT_TXT_URL =  HOST_URL + glassName + "_" + Task_Name + "_" + "scripts" +".txt";
		//ovxView = OVXView.getOVXContext(this);
		//new connectCall((ImageView)findViewById(R.id.imageViewStep)).execute(SCRIPT_IMAGE_URL,SCRIPT_TXT_URL);
		
		ImageView start = (ImageView)findViewById(R.id.imageViewStep);
		start.setImageResource(R.drawable.glass1_groceryshopping_1);
		
		
		stepNbrDescMap.put(Integer.toString(1), "Enter the store");
		stepNbrDescMap.put(Integer.toString(2), "Pick 3 cans of soup and put them in your basket");
		stepNbrDescMap.put(Integer.toString(3), "Pick 1 box of healthy cereal and put it in your basket");
		stepNbrDescMap.put(Integer.toString(4), "Put 1 gallon of 1% milk in your basket");
		stepNbrDescMap.put(Integer.toString(5), "Put 1 jar of peanut butter in your basket");
		stepNbrDescMap.put(Integer.toString(6), "Put your items on the counter for the cashier to ring up");
		stepNbrDescMap.put(Integer.toString(7), "Say thank you and take your groceries and receipt");
		
		stepNbrtxt.setText("1");
		stepDesctxt.setText(stepNbrDescMap.get("1"));
		
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		//if (!CALL_TYPE.equalsIgnoreCase("No Call")){
		//exit the call before destroying the activity
		//if (!ovxView.isCallOn()) {
		////	ovxView.exitCall();
		//}
		
		//and free the resources used by OVX context
		//ovxView.unregister();		

		//}
		//android.os.Process.killProcess(android.os.Process.myPid());
	}
	
	@Override
	public void onResume(){
		super.onResume();
		//exit the call before destroying the activity
		//if (!CALL_TYPE.equalsIgnoreCase("No Call")){
		//	if (!ovxView.isCallOn()) {
		//	ovxView.exitCall();
		//}}	
			
	}	
	@Override
	public void onPause(){
		super.onPause();
		//exit the call before destroying the activity
		//if (!CALL_TYPE.equalsIgnoreCase("No Call")){
		//	if (!ovxView.isCallOn()) {
		//	ovxView.exitCall();
		//	}
		//}
	}	
	
	public class connectCall extends AsyncTask<String, Integer, Bitmap>{
		ImageView bmImage;
		ProgressDialog dialog;
		
		public connectCall(ImageView bmImage){
			this.bmImage = bmImage;
		}
		
		
		protected void onPreExecute(){
			dialog = new ProgressDialog(ScriptPage1.this);
			dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			dialog.setMax(100);
			dialog.setTitle("CONNECTING...");
			dialog.show();
		}
		
		@Override
		protected Bitmap doInBackground(String... urls) {
			// TODO Auto-generated method stub
			
			for(int i = 0 ; i < 50 ; i++){
				publishProgress(5);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
				Log.d("tag call type","" +CALL_TYPE);
				if (!CALL_TYPE.equalsIgnoreCase("No Call")){
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
				}
			}catch (OVXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String urldisplay = urls[0];
			String file = urls[1];
			BufferedReader br = null;
			String line ="";
			int stepNbr = 1;
			try {
				URL urltxt = new URL(file);
				br = new BufferedReader(new InputStreamReader(urltxt.openStream()));
				while((line = br.readLine()) != null){
					stepNbrDescMap.put(Integer.toString(stepNbr), line);
					stepNbr++;
				}
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace(); 
			}finally{
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			Bitmap image = null;
			InputStream in = null;
			try{
				in = new java.net.URL(urldisplay).openStream();
				image = BitmapFactory.decodeStream(in);
			}catch(Exception e){
				
			}finally{
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			dialog.dismiss();
			
			return image;
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


		protected void onProgressUpdate(Integer... progress){
			dialog.incrementProgressBy(progress[0]);
		}
		
		protected void onPostExecute(Bitmap result){
			bmImage.setImageBitmap(result);
			TextView stepNbrtxt = (TextView)findViewById(R.id.helperhdrhelper);
			stepNbrtxt.setText("1");
			TextView stepDesctxt = (TextView)findViewById(R.id.stepDesctxt);
			stepDesctxt.setText(stepNbrDescMap.get("1"));
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
		
		TextView stepDesctxt = (TextView)findViewById(R.id.stepDesctxt);
		TextView stepNbrtxt = (TextView)findViewById(R.id.helperhdrhelper);
		TextView donetxt = (TextView)findViewById(R.id.donetxt);
		ImageView stepImg = (ImageView)findViewById(R.id.imageViewStep);
		int stepNbr = Integer.parseInt(stepNbrtxt.getText().toString());
		
		if (velocityX < -2500 && (stepNbr > 1)) {
			stepDesctxt.setVisibility(View.VISIBLE);
			stepNbrtxt.setVisibility(View.VISIBLE);
			stepImg.setVisibility(View.VISIBLE);
			donetxt.setVisibility(View.INVISIBLE);
			stepNbrtxt.setText("" + (stepNbr - 1));
			stepDesctxt.setText(stepNbrDescMap.get("" + (stepNbr - 1)));
			NEXT_IMAGE_URL = glassName + "_" + Task_Name + "_" + (stepNbr - 1) ;
			Log.d("nxt image", NEXT_IMAGE_URL);
			
			int res = getResId(NEXT_IMAGE_URL, getApplicationContext(), drawable.class);
					stepImg.setImageResource(res);
					Log.d("res", "" + res);
			//if (!CALL_TYPE.equalsIgnoreCase("No Call")){
			//	if (ovxView.isCallOn()) {
			//	ovxView.setVideoPause();
			//}}
			//new loadImage((ImageView)findViewById(R.id.imageViewStep)).execute(NEXT_IMAGE_URL);
			//if (!CALL_TYPE.equalsIgnoreCase("No Call")){
			//	if (ovxView.isCallOn()) {
			//	ovxView.setVideoResume();
			//}}
		} else if (velocityX > 2500 && (stepNbr < stepNbrDescMap.size())) {
			stepDesctxt.setVisibility(View.VISIBLE);
			stepNbrtxt.setVisibility(View.VISIBLE);
			stepImg.setVisibility(View.VISIBLE);
			donetxt.setVisibility(View.INVISIBLE);
			stepNbrtxt.setText("" + (stepNbr + 1));
			stepDesctxt.setText(stepNbrDescMap.get("" + (stepNbr + 1)));
			NEXT_IMAGE_URL = glassName + "_" + Task_Name + "_" + (stepNbr + 1) ;
			int res = getResId(NEXT_IMAGE_URL, getApplicationContext(), drawable.class);
			stepImg.setImageResource(res);
			Log.d("res", "" + res);
			//if (!CALL_TYPE.equalsIgnoreCase("No Call")){
			//	if (ovxView.isCallOn()) {
			//	ovxView.setVideoPause();
			//}}
			//new loadImage((ImageView)findViewById(R.id.imageViewStep)).execute(NEXT_IMAGE_URL);
			//if (!CALL_TYPE.equalsIgnoreCase("No Call")){
			//	if (ovxView.isCallOn()) {
			//	ovxView.setVideoResume();
			//}}
		} else if (velocityX > 2500 && (stepNbr == stepNbrDescMap.size())){
			stepDesctxt.setVisibility(View.INVISIBLE);
			stepNbrtxt.setVisibility(View.INVISIBLE);
			stepImg.setVisibility(View.INVISIBLE);
			donetxt.setVisibility(View.VISIBLE);
			stepNbrtxt.setText("" + (stepNbr + 1));
		} else if (velocityX > 2500 && (stepNbr == stepNbrDescMap.size() + 1)){
			Intent i = new Intent(this, ThankYouPage.class);
			i.putExtra("TASK_FULLNAME", TASK_FULLNAME);
			i.putExtra("glassName", glassName);
			startActivity(i);

			//exit the call before destroying the activity
			//if (!CALL_TYPE.equalsIgnoreCase("No Call")){
			//	if (ovxView.isCallOn()) {
			//	ovxView.exitCall();
			//}
					
			//and free the resources used by OVX context
			//ovxView.unregister();		
			//}
			finish();
		}
        return true;
	}
	
	public static int getResId(String fieldName, Context context, Class<?> c) {

	    try {
	        Field field= c.getDeclaredField(fieldName);
	        return field.getInt(field);
	    } catch (Exception e) {
	        return 0;
	    } 
	}
	public class loadImage extends AsyncTask<String, Integer, Bitmap>{
		ImageView bmImage;
		
		public loadImage(ImageView bmImage){
			this.bmImage = bmImage;
		}
		
		@Override
		protected Bitmap doInBackground(String... urls) {
			// TODO Auto-generated method stub

			String urldisplay = urls[0];
			Bitmap image = null;
			InputStream in = null;
			try{
				in = new java.net.URL(urldisplay).openStream();
				image = BitmapFactory.decodeStream(in);
			}catch(Exception e){
				
			}finally{
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			return image;
		}
		
		protected void onPostExecute(Bitmap result){
			bmImage.setImageBitmap(result);
		}
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

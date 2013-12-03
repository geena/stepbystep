package edu.neu.glass.stepByStepMurph2;

import edu.neu.glass.stepByStepMurph2.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class AppHome extends Activity implements GestureDetector.OnGestureListener,OnDoubleTapListener {

	private GestureDetector gestureDetector;
	public static int DEVICE_WIDTH = 0;
	public static int DEVICE_HEIGHT = 0;
	public static String LOGIN ;
	public static boolean ON_GLASS = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_apphome);
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
		
		TextView stepBySteptxt = (TextView) findViewById(R.id.stepDesctxt);
		stepBySteptxt.setTypeface(tfRobotoBlack);
		
		TextView txtUniqueLogin = (TextView) findViewById(R.id.txtUniqueLogin);
		txtUniqueLogin.setTypeface(tfRobotoLight);
		TextView txtWelcome = (TextView) findViewById(R.id.txtWelcome);
		txtWelcome.setTypeface(tfRobotoLight);
		
		WindowManager w = getWindowManager();
		Display d = w.getDefaultDisplay();
		DisplayMetrics metrics = new DisplayMetrics();
		d.getMetrics(metrics);
		DEVICE_WIDTH = metrics.widthPixels;
		DEVICE_HEIGHT = metrics.heightPixels;
		
		if (DEVICE_WIDTH == 640 && DEVICE_HEIGHT == 360){
			ON_GLASS = true ;			
			//new DisplayUniqueId(txtUniqueLogin).execute(getApplicationContext());
			txtUniqueLogin.setText("glass1");
		}else{
			ON_GLASS = false ;
			txtUniqueLogin.setText("to Caretaker App!");
			LinearLayout footer = (LinearLayout)findViewById(R.id.footer);
			footer.setVisibility(View.INVISIBLE);
			RelativeLayout loginInputLayout = (RelativeLayout)findViewById(R.id.loginInputLayout);
			loginInputLayout.setVisibility(View.VISIBLE);
			Button homeLayout = (Button)findViewById(R.id.loginButton);
			homeLayout.setVisibility(View.VISIBLE);
			homeLayout.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent i = new Intent(AppHome.this,PhoneHome.class);
					startActivity(i);
				}
			});
			
		}
		
		Toast.makeText(getApplicationContext(),"Murphy's law - Anything that can go wrong, will go wrong!",Toast.LENGTH_LONG).show();
			
	}	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.fullscreen, menu);
		return true;
	}

	@Override
	public boolean onGenericMotionEvent(MotionEvent event){
		gestureDetector.onTouchEvent(event);
		return true;
	}

	@Override
	public boolean onDoubleTap(MotionEvent arg0) {
		Log.d("App Home","Starting Emergency Activity");
		Intent i = new Intent(this,EmergencyActivity.class);
		TextView txtUniqueLogin = (TextView) findViewById(R.id.txtUniqueLogin);
		i.putExtra("glassName",txtUniqueLogin.getText().toString());
		startActivity(i);	
		return true;
	}

	@Override
	public boolean onDoubleTapEvent(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onSingleTapConfirmed(MotionEvent arg0) {
		Log.d("App Home","Starting new session for glass");
		if (ON_GLASS =true){
			TextView txtUniqueLogin = (TextView) findViewById(R.id.txtUniqueLogin);
			LOGIN = txtUniqueLogin.getText().toString();
			Log.d("TEXT = ",LOGIN);
			Intent i = new Intent(this,TasksActivity.class);
			i.putExtra("LOGIN",LOGIN);
			startActivity(i);
		}
		return true;
	}

	@Override
	public boolean onDown(MotionEvent arg0) {
		return false;
	}
	
	@Override
	public void onBackPressed(){
		finish();
	}

	@Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
       
        return true;
    }

	@Override
	public void onLongPress(MotionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent arg0) {
		return false;
	}

}
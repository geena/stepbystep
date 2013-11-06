package edu.neu.glass.stepByStep;

import edu.neu.glass.stepByStep.R;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;

public class ScriptPage1 extends Activity implements GestureDetector.OnGestureListener,OnDoubleTapListener{

	private GestureDetector gestureDetector;
	public String Task_Name;
	public String pichdr;
	public String gsdesc1 = "Enter The Store";
	public String gsdesc2 = "Pick Up 2 Boxes Of Pasta";
	public String gsdesc3 = "Go To The Cashier";	
	public String lbdesc1 = "Enter The Library";
	public String lbdesc2 = "Pick Up Book";
	public String lbdesc3 = "Checkout The Book";	
	
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
		TextView taptxt = (TextView) findViewById(R.id.taptxt);
		taptxt.setTypeface(tfRobotoRegular);
		TextView wantThistxt = (TextView) findViewById(R.id.wantThistxt);
		wantThistxt.setTypeface(tfRobotoLight);
		TextView taptaptxt = (TextView) findViewById(R.id.taptaptxt);
		taptaptxt.setTypeface(tfRobotoRegular);
		TextView needHelptxt = (TextView) findViewById(R.id.needHelptxt);
		needHelptxt.setTypeface(tfRobotoLight);
		
		Typeface tfRobotoThin = Typeface.createFromAsset(getAssets(), "fonts/roboto_thin.ttf");
		TextView stepDesctxt = (TextView)findViewById(R.id.stepDesctxt);
		stepDesctxt.setTypeface(tfRobotoThin);
		TextView stepNbrtxt = (TextView)findViewById(R.id.helperhdrhelper);
		stepNbrtxt.setTypeface(tfRobotoBlack);
		
		new connectCall().execute();
		Intent intent = getIntent();
		Task_Name = intent.getExtras().getString("TASK_NAME");
		
		ImageView imageViewStep = (ImageView)findViewById(R.id.imageViewStep);
		
		if(Task_Name.equalsIgnoreCase("Grocery Shopping")){
			pichdr = "gs";
			stepNbrtxt.setText("1");
			stepDesctxt.setText(gsdesc1);
			imageViewStep.setImageResource(R.drawable.glass1_gstore_1);
		}else{
			pichdr = "lb";
			stepNbrtxt.setText("1");
			stepDesctxt.setText(lbdesc1);
			imageViewStep.setImageResource(R.drawable.glass1_lib_1);
		}
	}
	
	public class connectCall extends AsyncTask<String, Integer, String>{

		ProgressDialog dialog;
		
		
		protected void onPreExecute(){
			dialog = new ProgressDialog(ScriptPage1.this);
			dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			dialog.setMax(100);
			dialog.setTitle("CONNECTING...");
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
		ImageView imageViewStep = (ImageView)findViewById(R.id.imageViewStep);
		int stepNbr = Integer.parseInt(stepNbrtxt.getText().toString());
		
		if (velocityX < -2500 && (stepNbr > 1)) {
			
			if(Task_Name.equalsIgnoreCase("Grocery Shopping")){
				stepNbrtxt.setText("" + (stepNbr - 1));
				if((stepNbr - 1) == 2){
					stepDesctxt.setText(gsdesc2);
					imageViewStep.setImageResource(R.drawable.glass1_gstore_7);
				}
				else if((stepNbr - 1) == 1){
					stepDesctxt.setText(gsdesc1);
					imageViewStep.setImageResource(R.drawable.glass1_gstore_1);
				}
			}else{
				pichdr = "lb";
				stepNbrtxt.setText("" + (stepNbr - 1));
				if((stepNbr - 1) == 2){
					stepDesctxt.setText(lbdesc2);
					imageViewStep.setImageResource(R.drawable.glass1_lib_15);}
				else if((stepNbr - 1) == 1){
					stepDesctxt.setText(lbdesc1);
					imageViewStep.setImageResource(R.drawable.glass1_lib_1);
				}
			}
			
        } else if (velocityX > 2500 && (stepNbr < 3)) {
        	
        	if(Task_Name.equalsIgnoreCase("Grocery Shopping")){
    			pichdr = "gs";
    			stepNbrtxt.setText("" + (stepNbr + 1));
    			if((stepNbr + 1) == 2){
					stepDesctxt.setText(gsdesc2);
					imageViewStep.setImageResource(R.drawable.glass1_gstore_7);
					}
				else if((stepNbr + 1) == 3){
					stepDesctxt.setText(gsdesc3);
					imageViewStep.setImageResource(R.drawable.glass1_gstore_20);
				}
    		}else{
    			pichdr = "lb";
    			stepNbrtxt.setText("" + (stepNbr + 1));
    			if((stepNbr + 1) == 2){
					stepDesctxt.setText(lbdesc2);
					imageViewStep.setImageResource(R.drawable.glass1_lib_15);
					}
				else if((stepNbr + 1) == 3){
					stepDesctxt.setText(lbdesc3);
					imageViewStep.setImageResource(R.drawable.glass1_lib_20);
				}
    		}
        	//right
        }else if (velocityX > 2500 && (stepNbr == 3)){
        	Intent i = new Intent(this,DonePage.class);
        	startActivity(i);
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

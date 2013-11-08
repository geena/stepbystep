package edu.neu.glass.stepByStep;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import edu.neu.glass.stepByStep.R;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
	public String glassName = "glass1";
	public String SCRIPT_IMAGE_URL = "";
	public String SCRIPT_TXT_URL ="";
	public String NEXT_IMAGE_URL ="";
	public static String HOST_URL = "http://glassphiteam1.weebly.com/uploads/2/4/6/8/24684595/";
	public static HashMap<String,String> stepNbrDescMap = new HashMap<String,String>();
	
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
		
		Intent intent = getIntent();
		Task_Name = intent.getExtras().getString("TASK_NAME");		
		
		SCRIPT_IMAGE_URL =  HOST_URL + glassName + "_" + Task_Name + "_" + 1 + ".jpg";
		SCRIPT_TXT_URL =  HOST_URL + glassName + "_" + Task_Name + "_" + "scripts" +".txt";
		
		new connectCall((ImageView)findViewById(R.id.imageViewStep)).execute(SCRIPT_IMAGE_URL,SCRIPT_TXT_URL);
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
				publishProgress(2);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
		int stepNbr = Integer.parseInt(stepNbrtxt.getText().toString());
		
		if (velocityX < -2500 && (stepNbr > 1)) {
			stepNbrtxt.setText("" + (stepNbr - 1));
			stepDesctxt.setText(stepNbrDescMap.get("" + (stepNbr - 1)));
			NEXT_IMAGE_URL = HOST_URL + glassName + "_" + Task_Name + "_" + (stepNbr - 1) + ".jpg";
			new loadImage((ImageView)findViewById(R.id.imageViewStep)).execute(NEXT_IMAGE_URL);
		} else if (velocityX > 2500 && (stepNbr < stepNbrDescMap.size())) {
			stepNbrtxt.setText("" + (stepNbr + 1));
			stepDesctxt.setText(stepNbrDescMap.get("" + (stepNbr + 1)));
			NEXT_IMAGE_URL = HOST_URL + glassName + "_" + Task_Name + "_" + (stepNbr + 1) + ".jpg";
			new loadImage((ImageView)findViewById(R.id.imageViewStep)).execute(NEXT_IMAGE_URL);
		} else if (velocityX > 2500 && (stepNbr == stepNbrDescMap.size())){
			Intent i = new Intent(this,DonePage.class);
        	startActivity(i);
		}
		
		
        return true;
	}

	public class loadImage extends AsyncTask<String, Integer, Bitmap>{
		ImageView bmImage;
		ProgressDialog dialog;
		
		public loadImage(ImageView bmImage){
			this.bmImage = bmImage;
		}
		
		
		protected void onPreExecute(){
			dialog = new ProgressDialog(ScriptPage1.this);
			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			dialog.setMax(100);
			dialog.setTitle("LOADING IMAGE...");
			dialog.show();
		}
		
		@Override
		protected Bitmap doInBackground(String... urls) {
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
		
		protected void onProgressUpdate(Integer... progress){
			dialog.incrementProgressBy(progress[0]);
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

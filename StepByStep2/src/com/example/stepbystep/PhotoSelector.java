package com.example.stepbystep;

import java.io.File;
import java.io.InputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Window;


public class PhotoSelector extends Activity{
    
    private final int REQUEST_SELECT_PHOTO = 1001;
    private final int REQUEST_TAKE_PHOTO = 1002;

    private File image;
    private String imageName = "tmp.png";
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.generic);
		
		image = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), imageName);
		
		showDialog();
	}
	
	
	@Override
	protected void onResume(){
		super.onResume();
		System.gc();
	}
	
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		System.gc();
	}
	
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data){ 
		super.onActivityResult(requestCode, resultCode, data); 
		switch(requestCode){ 
			case REQUEST_SELECT_PHOTO:
		        if(resultCode == RESULT_OK){
	                Uri imageUri = data.getData();
	                grabPhoto(imageUri);
	                Bitmap bm = preparePhoto(Uri.fromFile(image));
		        	returnBundle(bm);
		        }
		        else{
		        	finish();
		        }
				break;
			case REQUEST_TAKE_PHOTO:
				if(resultCode == RESULT_OK){
					Bitmap bm = preparePhoto(Uri.fromFile(image));
					returnBundle(bm);	
				}else{
					finish();
				}
				break;
		}
	}
	
	
	private void showDialog(){
		AlertDialog dialog = new AlertDialog.Builder(PhotoSelector.this)
		.setTitle(getResources().getString(R.string.changeimage))
		.setIcon(getResources().getDrawable(R.drawable.camera_icon))
		.setPositiveButton(R.string.addphotoexisting, new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface d, int i){ 
				d.dismiss(); 
				startImagePicker();
			}
		})
		.setNegativeButton(R.string.addphotonew, new DialogInterface.OnClickListener(){ 
			public void onClick(DialogInterface d, int i){ 
				d.dismiss();
				startCamera();
			}
		})
		.setOnCancelListener(new DialogInterface.OnCancelListener(){
			@Override
			public void onCancel(DialogInterface dialog){
				setResult(Activity.RESULT_CANCELED);
				finish();
			}
		})
		.create();
		
		dialog.show();
		
		//if you want some blur ;)
//		WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();  
//		lp.dimAmount=0.0f;
//		dialog.getWindow().setAttributes(lp);
//		dialog.getWindow().addFlags(WindowManager.LayoutParams.flag);
	}
	
	
    public void startImagePicker(){ 
    	Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_SELECT_PHOTO);
    }
    
	
    private void startCamera(){
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(image));
		startActivityForResult(intent, REQUEST_TAKE_PHOTO);
    }
    
    
    private void grabPhoto(Uri imageUri){
        if(imageUri != null){ 
    	        String[] projection = { MediaStore.Images.Media.DATA };
    	        Cursor cursor = managedQuery(imageUri, projection, null, null, null);
    	        if(cursor != null){
    	        	try{
	    	            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	    	            cursor.moveToFirst();
			            String filePath = cursor.getString(columnIndex);
			            cursor.close();
			            image = new File(filePath);
    	        	}catch(Exception e){
            			finish();
    	        	}
    	        }     
        }else{
    		imageUri = Uri.fromFile(image);
            image = new File(imageUri.getPath());
        }
    }
    	
    
	private Bitmap preparePhoto(Uri uri){
		ContentResolver mContentResolver = getContentResolver();
		
		InputStream in = null;
		try{
			final int IMAGE_MAX_SIZE = 100*1024; 	// 100K //
			in = mContentResolver.openInputStream(uri);

			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(in, null, o);
			in.close();

			int scale = 1;
			while((o.outWidth * o.outHeight) * (1 / Math.pow(scale, 2)) > IMAGE_MAX_SIZE){
				scale++;
			}

			Bitmap b = null;
			in = mContentResolver.openInputStream(uri);
			if(scale > 1){
				scale--; 
				// scale to max possible inSampleSize that still yields an image larger than target
				o = new BitmapFactory.Options();
				o.inSampleSize = scale;
				b = BitmapFactory.decodeStream(in, null, o);

				// resize to desired dimensions
				int height = b.getHeight();
				int width = b.getWidth();

				double y = Math.sqrt(IMAGE_MAX_SIZE / (((double)width) / height));
				double x = (y / height) * width;

				Bitmap scaledBitmap = Bitmap.createScaledBitmap(b, (int)x, (int)y, true);
				b.recycle();
				b = scaledBitmap;

				System.gc();
			}else{
				b = BitmapFactory.decodeStream(in);
			}
			in.close();

			
			return b;
		}catch(Exception e){
			return null;
		}
	}
	
	private void returnBundle(Bitmap bm){
		Intent intent = getIntent();
		Bundle bundle = new Bundle();
		bundle.putParcelable(Register.PHOTO, bm);
		intent.putExtra(Register.PHOTO_BUNDLE, bundle);
		setResult(Activity.RESULT_OK, intent);
		finish();
	}
	
}
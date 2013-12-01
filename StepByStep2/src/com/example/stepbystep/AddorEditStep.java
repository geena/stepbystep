package com.example.stepbystep;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import com.dropbox.sync.android.DbxAccountManager;
import com.dropbox.sync.android.DbxException;
import com.dropbox.sync.android.DbxException.Unauthorized;
import com.dropbox.sync.android.DbxFile;
import com.dropbox.sync.android.DbxFileSystem;
import com.dropbox.sync.android.DbxPath;
import com.dropbox.sync.android.DbxPath.InvalidPathException;
import com.example.stepbystep.dropboxdata.APIResponse;
import com.example.stepbystep.dropboxdata.Step;
import com.example.stepbystep.dropboxdata.Task;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class AddorEditStep extends Activity {

	ImageView _image;
	EditText script;
	Button uploadImage;
	Button save;
	Bitmap imageBitmap;
	String imageURL;
	DbxAccountManager _acctMgr;
	DbxFileSystem dbxFs;
	Context context;
	Step _step;
	Drawable _drawImage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.addoreditstep);

		_step = (Step) Register.register.get("editStep");

		context = this;

		_acctMgr = DbxAccountManager.getInstance(getApplicationContext(), "j1bohgxwhlirlq6", "kamif5qmk6m7und");

		_image = (ImageView) findViewById(R.id.stepImage);
		script = (EditText) findViewById(R.id.script);
		uploadImage = (Button) findViewById(R.id.uploadImage);
		save = (Button) findViewById(R.id.saveStep);

		if (!_acctMgr.hasLinkedAccount()){
			_acctMgr.startLink((Activity) context, 2);
		} else {
			downloadImage(_step.getImage());
		}

		script.setText(_step.getScript());

		uploadImage.setOnClickListener(new UploadListener());

		save.setOnClickListener(new SaveListener());
	}

	private void downloadImage(String url) {
		
		DbxFile image = null;
		try {
			DbxFileSystem dbxFs = DbxFileSystem.forAccount(_acctMgr.getLinkedAccount());

			image = dbxFs.open(new DbxPath(url));

			FileInputStream in = image.getReadStream();
			BufferedInputStream buf = new BufferedInputStream(in);
			byte[] bitMapA= new byte[buf.available()];
			buf.read(bitMapA);
			Bitmap bM = BitmapFactory.decodeByteArray(bitMapA, 0, bitMapA.length);
			_image.setImageBitmap(bM);
			if (in != null) {
				in.close();
			}
			if (buf != null) {
				buf.close();
			}

		} catch (Unauthorized e) {
			e.printStackTrace();
		} catch (InvalidPathException e) {
			e.printStackTrace();
		} catch (DbxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			image.close();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private class SaveListener implements OnClickListener{
		@Override
		public void onClick(View v){

			if (!_acctMgr.hasLinkedAccount()){
				_acctMgr.startLink((Activity) context, 0);
			} else {
				if (!(imageBitmap == null)){
					uploadPhoto();
				} else {
					rewriteJSON();
				}
			}
		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 0) {
			if (resultCode == Activity.RESULT_OK) {
				if (!(imageBitmap == null)){
					uploadPhoto();
				} else {
					rewriteJSON();
				}
			} else {
				// ... Link failed or was cancelled by the user.
			}
		} else if (requestCode == 1 && resultCode == Activity.RESULT_OK){
			setPhoto(data);
		} else if (requestCode == 2 && resultCode == Activity.RESULT_OK){
			downloadImage(_step.getImage());
		}
		else {
			super.onActivityResult(requestCode, resultCode, data);
		}
	}

	private void setPhoto(Intent data)
	{
		Bundle bundle = data.getBundleExtra(Register.PHOTO_BUNDLE);

		Bitmap photo = (Bitmap) bundle.getParcelable(Register.PHOTO);
		Drawable imageDrawable = new BitmapDrawable(photo);

		_image.setImageDrawable(imageDrawable);

		imageBitmap = photo;

	}

	private void uploadPhoto() {
		DbxFile image = null;
		try {
			DbxFileSystem dbxFs = DbxFileSystem.forAccount(_acctMgr.getLinkedAccount());

			int stepIndex = (Integer) Register.register.get("stepIndex") + 1;
			Task currentTask = (Task) Register.register.get("currentTask");
			String taskTitle = currentTask.getTitle();
			
			String imgURL = "Images/" + taskTitle + "/" + "Step" + stepIndex + ".jpg";
			if (dbxFs.exists(new DbxPath(imgURL))){
				image = dbxFs.open(new DbxPath(imgURL));
			}

			Bitmap bitmap = imageBitmap;
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
			byte[] bitmapdata = bos.toByteArray();

			FileOutputStream output = image.getWriteStream();

			output.write(bitmapdata);

			imageURL = imgURL;

			rewriteJSON();

		} catch (Unauthorized e) {
			e.printStackTrace();
		} catch (InvalidPathException e) {
			e.printStackTrace();
		} catch (DbxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			image.close();
		}
	}

	private class UploadListener implements OnClickListener{
		@Override
		public void onClick(View v){

			Intent intent = new Intent(AddorEditStep.this, PhotoSelector.class);
			startActivityForResult(intent, 1);

		}

	}

	private void rewriteJSON(){

		Step step = (Step) Register.register.get("editStep");
		int stepIndex = (Integer) Register.register.get("stepIndex");
		Task currentTask = (Task) Register.register.get("currentTask");
		int taskIndex = (Integer) Register.register.get("taskIndex");
		List<Step> steps = currentTask.getSteps();
		APIResponse allTasks = (APIResponse) Register.register.get("allTasks");
		List<Task> tasks = allTasks.getTasks();

		step.addScript(script.getText().toString());
		if (!(imageURL == null)){
			step.addImage(imageURL);
		}
		steps.set(stepIndex, step);
		currentTask.addSteps(steps);
		tasks.set(taskIndex, currentTask);

		APIResponse data = new APIResponse();
		data.setTasks(tasks);

		ObjectWriter writer = new ObjectMapper().writer();

		String json = "";
		try {
			json = writer.writeValueAsString(data);
		} catch (JsonGenerationException e1) {
			e1.printStackTrace();
		} catch (JsonMappingException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		updateDropBox(json);

	}

	private void updateDropBox(String json){
		DbxFile testFile = null;
		try {
			DbxFileSystem dbxFs = DbxFileSystem.forAccount(_acctMgr.getLinkedAccount());
			testFile = dbxFs.open(new DbxPath("test.txt"));
			if (!json.contentEquals("")){
				testFile.writeString(json);
			}
			Log.d("Dropbox Test", "File contents: " + json);
			Intent intent = new Intent(AddorEditStep.this, MainActivity.class);
			startActivity(intent);
		} catch (Unauthorized e) {
			e.printStackTrace();
		} catch (InvalidPathException e) {
			e.printStackTrace();
		} catch (DbxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			testFile.close();
		}
	}

}
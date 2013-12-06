package edu.neu.glass.stepbyStepPhone;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import edu.neu.glass.stepByStep.R;
import edu.neu.glass.stepbyStepPhone.dropboxdata.APIResponse;
import edu.neu.glass.stepbyStepPhone.dropboxdata.Step;
import edu.neu.glass.stepbyStepPhone.dropboxdata.Task;

public class AddorEditStep extends Activity {

	ImageView _image;
	EditText script;
	Button uploadImage;
	Button save;
	Bitmap imageBitmap;
	String imageURL;
	Context context;
	Step _step;
	Drawable _drawImage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.addoreditstep);

		_image = (ImageView) findViewById(R.id.stepImage);
		script = (EditText) findViewById(R.id.script);
		uploadImage = (Button) findViewById(R.id.uploadImage);
		save = (Button) findViewById(R.id.saveStep);

		uploadImage.setOnClickListener(new UploadListener());

		save.setOnClickListener(new SaveListener());

		context = this;


		if (Register.register.containsKey("editStep")){
			_step = (Step) Register.register.get("editStep");

			if (!DropBoxService.hasLinkedAccount()){
				DropBoxService.linkAccount(this, 2);
			} else {
				if (!(_step.getImage() == null)){
					_image.setImageBitmap(DropBoxService.downloadImage(_step.getImage()));
				}
			}
			script.setText(_step.getScript());
		}
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.fullscreen, menu);
		return true;
	}

	private class SaveListener implements OnClickListener{
		@Override
		public void onClick(View v){

			if (!DropBoxService.hasLinkedAccount()){
				DropBoxService.linkAccount((Activity) context, 0);
			} else {
				if (!Register.register.containsKey("editStep")){
					addStepAndUpdate();
				}
				if (!(imageBitmap == null)){
					imageURL = DropBoxService.uploadPhoto(imageBitmap);
				} 
				rewriteJSON();
			}
		}

		private void addStepAndUpdate() {

			Task current = (Task) Register.register.get("currentTask");
			List<Step> steps = current.getSteps();
			Step added = new Step();
			steps.add(added);
			current.addSteps(steps);
			Register.register.put("currentTask", current);
			Register.register.put("editStep", added);
			Register.register.put(Register.STEP_INDEX, steps.size() - 1);
		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 0) {
			if (resultCode == Activity.RESULT_OK) {
				if (!(imageBitmap == null)){
					imageURL = DropBoxService.uploadPhoto(imageBitmap);
				} 
				rewriteJSON();
			} else {
				// ... Link failed or was cancelled by the user.
			}
		} else if (requestCode == 1 && resultCode == Activity.RESULT_OK){
			setPhoto(data);
		} else if (requestCode == 2 && resultCode == Activity.RESULT_OK){
			if (!(_step.getImage() == "")){
				_image.setImageBitmap(DropBoxService.downloadImage(_step.getImage()));
			}
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
		if (!(imageURL == "")){
			step.addImage(imageURL);
		}
		steps.set(stepIndex, step);
		currentTask.addSteps(steps);
		tasks.set(taskIndex, currentTask);

		APIResponse data = new APIResponse();
		data.setTasks(tasks);

		DropBoxService.writeToDropBox(data);

		Intent intent = new Intent(AddorEditStep.this, MainActivity.class);
		startActivity(intent);

	}



}
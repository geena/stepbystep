package com.example.stepbystep.tasks;

import com.example.stepbystep.R;
import com.example.stepbystep.R.id;
import com.example.stepbystep.R.layout;
import com.example.stepbystep.R.menu;
import com.paypal.android.base.commons.exception.InvalidStateException;
import com.paypal.android.base.commons.patterns.mvc.model.DefaultModel;
import com.paypal.android.base.commons.patterns.mvc.model.IModel;
import com.paypal.android.base.commons.patterns.mvc.presenter.NavigationController;
import com.paypal.android.base.commons.patterns.mvc.view.IView;
import com.paypal.android.base.commons.ui.factories.ViewFactory;
import com.paypal.android.base.commons.validation.Assert;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;

public class Tasks extends Activity {

	Button activities;
	Button settings;
	Button help;
	IView _tasksView;
	IModel _model;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		try {
			_model = TasksModel.class.newInstance();
		    _tasksView = TasksView.class.newInstance();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
		setContentView(_tasksView.getView());
		
		activities = (Button) findViewById(R.id.activities);
		settings = (Button) findViewById(R.id.settings);
		help = (Button) findViewById(R.id.help);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
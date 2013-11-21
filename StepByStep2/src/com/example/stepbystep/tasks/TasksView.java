package com.example.stepbystep.tasks;

import java.util.ArrayList;

import android.app.ActionBar;
import android.content.res.Resources;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.example.stepbystep.R;
import com.example.stepbystep.TaskListFragment;
import com.example.stepbystep.TasksAdapter;
import com.example.stepbystep.dataservice.Task;
import com.paypal.android.base.commons.patterns.mvc.model.ModelChangeEvent;
import com.paypal.android.base.commons.patterns.mvc.view.DefaultView;
import com.paypal.android.base.commons.patterns.mvc.view.IView;

public class TasksView extends DefaultView<TasksModel, LinearLayout> implements IView
{

	private ActionBar _actionBar;
	private TasksAdapter _tasksAdapter;

	public TasksView()
	{
		super(R.layout.tasks);
	}

	protected void initLayout()
	{
		Resources resources = getContext().getResources();
		_tasksAdapter = new TasksAdapter(
				getContext(),
				new ArrayList<Task>());

		TaskListFragment tasksFragment = new TaskListFragment(_tasksAdapter, new TasksClickListener(_tasksAdapter));
		
		tasksFragment.setRetainInstance(true);

	}

	public void modelChanged(ModelChangeEvent event)
	{
		/*
		PropertyKeys propertyKey = event.propertyKey;
		
		if (propertyKey.equals(ISalesHistoryModel.Properties.INVOICE_SUMMARY_LIST))
		{
			if (invoiceTypeDisplayed.equals(InvoiceTypeDisplayed.ALL))
			{
				_allAdapter.setItems(_model.getInvoiceSummaryList());
			}
		}
		*/
	}

	public class TasksClickListener implements AdapterView.OnItemClickListener
	{
		TasksAdapter _adapter;

		TasksClickListener(TasksAdapter adapter)
		{
			_adapter = adapter;
		}

		public void onItemClick(AdapterView<?> parent, View view, int position, long id)
		{
			Task task = _adapter.getItem(position);
			_model.setSelectedTask(task);
			notifyViewListener(TasksView.this, TasksModel.TasksActions.TASK_SELECTED);
		}
	}

	public void updateView()
	{
		_tasksAdapter.notifyDataSetChanged();
	}
}

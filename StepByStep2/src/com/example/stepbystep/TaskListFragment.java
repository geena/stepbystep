package com.example.stepbystep;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class TaskListFragment extends ListFragment {

	private TasksAdapter _adapter;
	private OnItemClickListener _clickListener;
	private ListView _listView;

	public TaskListFragment(){};
	
	public TaskListFragment(TasksAdapter adapter, OnItemClickListener clickListener)
	{
		_adapter = adapter;
		_clickListener = clickListener;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setListAdapter(_adapter);
		_listView = getListView();
		
		_listView.setOnItemClickListener(_clickListener);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
	}
}
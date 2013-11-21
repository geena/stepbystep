package com.example.stepbystep;

import java.util.ArrayList;
import java.util.List;

import com.example.stepbystep.dataservice.Task;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;

public class TasksAdapter extends BaseAdapter implements SectionIndexer
{

	private List<Task> _items = new ArrayList<Task>();
	private final Context _context;


	public TasksAdapter(Context context, List<Task> tasks)
	{
		 _items = tasks;
		_context = context;
	}

	public void setItems(List<Task> tasks)
	{
		_items = tasks;
	}

	public Task getItem(int position)
	{
		return _items.get(position);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getPositionForSection(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getSectionForPosition(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object[] getSections() {
		// TODO Auto-generated method stub
		return null;
	}

}
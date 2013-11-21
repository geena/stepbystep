package com.example.stepbystep.dataservice;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;



@JsonIgnoreProperties(ignoreUnknown = true)
public class APIResponse
{
	private List<Task> _tasks = new ArrayList<Task>();
	private Exception _exception;
	
	@JsonProperty("tasks")
	public void addTask(Task task)
	{
		_tasks.add(task);
	}
	
	public List<Task> getTasks()
	{
		return _tasks;
	}
	
	public void clearTasks()
	{
		_tasks = new ArrayList<Task>();
	}
	
	public void setException(Exception e)
	{
		_exception = e;
	}
	
	public Exception getException()
	{
		return _exception;
	}
}
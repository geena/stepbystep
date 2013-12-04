package edu.neu.glass.stepbyStepPhone.dropboxdata;

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
	public void setTasks(List<Task> tasks)
	{
		_tasks = tasks;
	}
	
	public List<Task> getTasks()
	{
		return _tasks;
	}
	
	public void setException(Exception e)
	{
		_exception = e;
	}
	
	public Exception getException()
	{
		return _exception;
	}

	public APIResponse() {
	}
}
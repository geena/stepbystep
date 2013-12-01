package com.example.stepbystep.dropboxdata;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Task
{
	private String _title;
	private List<Step> _steps;
	private Exception _exception;
	
	@JsonProperty("title")
	public void addTitle(String title)
	{
		_title = title;
	}
	
	public String getTitle()
	{
		return _title;
	}
	
	@JsonProperty("steps")
	public void addSteps(List<Step> steps)
	{
		_steps = steps;
	}
	
	public List<Step> getSteps()
	{
		return _steps;
	}
	
	public void setException(Exception e)
	{
		_exception = e;
	}
	
	public Exception getException()
	{
		return _exception;
	}
	
	public Task(){
		
	}
}
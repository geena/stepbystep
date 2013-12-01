package com.example.stepbystep.dropboxdata;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Step
{
	private String _imageURL;
	private String _script;
	private Exception _exception;
	
	@JsonProperty("image")
	public void addImage(String image)
	{
		_imageURL = image;
	}
	
	public String getImage()
	{
		return _imageURL;
	}
	
	@JsonProperty("script")
	public void addScript(String script)
	{
		_script = script;
	}
	
	public String getScript()
	{
		return _script;
	}
	
	public void setException(Exception e)
	{
		_exception = e;
	}
	
	public Exception getException()
	{
		return _exception;
	}
	
	public Step(){
		
	}
}
package com.example.stepbystep.dataservice;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Task
{
	private String _imageURL;
	private String _textURL;
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
	
	@JsonProperty("text")
	public void addText(String text)
	{
		_textURL = text;
	}
	
	public String getText()
	{
		return _textURL;
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
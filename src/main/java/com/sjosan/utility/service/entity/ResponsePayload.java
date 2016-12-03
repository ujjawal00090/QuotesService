package com.sjosan.utility.service.entity;

import java.io.Serializable;
import java.util.List;

public class ResponsePayload implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private boolean responseStatus=true;
	private String responseMessage;
	
	private Entity data;
	
	List<Category> categories;
	List<Content> contents;
	
	
	public boolean isResponseStatus() {
		return responseStatus;
	}
	public void setResponseStatus(boolean responseStatus) {
		this.responseStatus = responseStatus;
	}
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	
	public Entity getData() {
		return data;
	}
	public void setData(Entity data) {
		this.data = data;
	}
	public List<Category> getCategories() {
		return categories;
	}
	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
	
	public List<Content> getContents() {
		return contents;
	}
	public void setContents(List<Content> contents) {
		this.contents = contents;
	}
	
	
	
	
	

}

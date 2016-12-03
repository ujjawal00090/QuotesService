package com.sjosan.utility.service.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class Entity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	long id;
	String createdTS;
	String app_id;
	String token;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCreatedTS() {
		return createdTS;
	}
	public void setCreatedTS(String createdTS) {
		this.createdTS = createdTS;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getApp_id() {
		return app_id;
	}
	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}
	
	
	
	

}
